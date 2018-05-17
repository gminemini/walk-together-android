package com.custu.project.walktogether.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.lib.OpenCVUtil;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

import io.fabric.sdk.android.Fabric;
import org.opencv.android.Utils;

public class SplashScreenActivity extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {

                    compare();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        ProgressBar progressBar = findViewById(R.id.progress);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        if (OpenCVLoader.initDebug()) {
            Log.d("onManagerConnected: ", "onCreate: ");
        } else {
            Log.d("onManagerConnected: ", "onManagerConnected:");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, baseLoaderCallback);
        }

        /*int splashInterval = 2000;
        new Handler().postDelayed(() -> {
            Intent intent;
            if (UserManager.getInstance(SplashScreenActivity.this).getCaretaker() != null) {
                intent = new Intent(SplashScreenActivity.this, ReHomeCaretakerActivity.class);
                startActivity(intent);
            } else if (UserManager.getInstance(SplashScreenActivity.this).getPatient() != null) {
                Patient patient = UserManager.getInstance(SplashScreenActivity.this).getPatient();
                if (patient.getUserName() != null) {
                    intent = new Intent(SplashScreenActivity.this, ReHomePatientActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(SplashScreenActivity.this, RegisterPatientActivity.class);
                    intent.putExtra("idPatient", patient.getId());
                    intent.putExtra("isContinue", true);
                    startActivity(intent);
                }
            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }, splashInterval);*/
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    @Override
    public void onBackPressed() {
    }

    private void compare() {
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
        compare_image(b1, b2);
    }

    public void compare_image(Bitmap img_1, Bitmap img_2)
    {
        Mat mat_1 = new Mat();
        Mat mat_2 = new Mat();


        Bitmap bitmap1 = img_1.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bitmap1, mat_1);

        Bitmap bitmap2 = img_2.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bitmap2, mat_2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f,256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Collections.singletonList(mat_1), new MatOfInt(0),
                new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Collections.singletonList(mat_2), new MatOfInt(0),
                new Mat(), hist_2, histSize, ranges);

        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        Double d = res * 100;
        Log.d("compare_image: ", "compare_image: "+d.intValue());
        Log.d("compare_image: ", "compare_image: "+hist_1.dump());
        Log.d("compare_image: ", "compare_image: "+hist_2.dump());
    }

}

