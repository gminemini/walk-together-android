package com.custu.project.walktogether.controller.tmse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionSeventeenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private DrawImage drawImage;
    private Button nextBtn;
    private Question question;
    private ProgressDialog progressDialog;

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(QuestionSeventeenActivity.this) {
        @Override
        public void onManagerConnected(int status) {
            super.onManagerConnected(status);
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    compareImage();
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                }
            }

        }
    };

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
        final int[] time = {31};
        final ProgressBar progress;
        progress = findViewById(R.id.progress);
        progress.setMax(time[0]);
        progress.setProgress(time[0]);
        countDownTimer = new CountDownTimer(timeInterval, 1000) {
            public void onTick(long millisUntilFinished) {
                progress.setProgress(--time[0]);
            }

            public void onFinish() {
                progress.setProgress(0);
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no17", question.getId(), "");
                Intent intent = new Intent(QuestionSeventeenActivity.this, QuestionEighteenActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitEvaluation(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_seventeen);
        initProgressDialog();
        getData();
        setUI();
        countDownTime();
    }

    @Override
    public void initValue() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        countDownTimer.cancel();
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("17", QuestionSeventeenActivity.this)
                .getQuestion();
    }

    @Override
    public void setUI() {
        drawImage = findViewById(R.id.draw_line);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        drawImage.init(metrics);
        drawImage.normal();
        Button clearDrawLineButton = findViewById(R.id.delete_draw);
        clearDrawLineButton.setOnClickListener(this);
        nextBtn = (Button) findViewById(R.id.next);
        nextBtn.setOnClickListener(this);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText("(17) " + question.getTitle());
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(QuestionSeventeenActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.delete_draw:
                drawImage.clear();
                break;
            case R.id.next:
                progressDialog.show();
                startOpencv();
                countDownTimer.cancel();
                break;
        }
    }

    private Bitmap takeScreenshot() {
        View rootView = findViewById(R.id.draw_line);
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    private void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(this.getFilesDir() + "/" + new Date().getTime() + ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void compareImage() {
        Bitmap bitmap = takeScreenshot();
        Bitmap template = BitmapFactory.decodeResource(getResources(), R.drawable.template);
        saveBitmap(bitmap);

        double result = getPercentSimilarity(bitmap, template);

        int score = 0;
        if (result >= ConfigService.AVERAGE_OPENCV)
            score = 2;
        else if (result >= ConfigService.MIN_OPENCV && result < ConfigService.AVERAGE_OPENCV)
            score = 1;

        Log.d("compareImage: ", "compareImage: "+result);
        Log.d("compareImage: ", "compareImage: "+score);
        progressDialog.dismiss();
        StoreAnswerTmse.getInstance().storeAnswer("no17", question.getId(), String.valueOf(score));
        Intent intent = new Intent(QuestionSeventeenActivity.this, QuestionEighteenActivity.class);
        startActivity(intent);
    }

    public Double getPercentSimilarity(Bitmap inputBitmap, Bitmap templateBitmap) {
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();

        inputBitmap = resize(inputBitmap, inputBitmap.getWidth(), inputBitmap.getHeight());
        templateBitmap = Bitmap.createScaledBitmap(templateBitmap, inputBitmap.getWidth(), inputBitmap.getHeight(), true);
        saveBitmap(templateBitmap);

        Bitmap bitmap1 = inputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bitmap1, mat1);

        Bitmap bitmap2 = templateBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bitmap2, mat2);

        Mat hist1 = new Mat();
        Mat hist2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Collections.singletonList(mat1), new MatOfInt(0),
                new Mat(), hist1, histSize, ranges);
        Imgproc.calcHist(Collections.singletonList(mat2), new MatOfInt(0),
                new Mat(), hist2, histSize, ranges);

        double res = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);
        return res * 100;
    }

    private void startOpencv() {
        if (OpenCVLoader.initDebug()) {
            compareImage();
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, baseLoaderCallback);
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

}
