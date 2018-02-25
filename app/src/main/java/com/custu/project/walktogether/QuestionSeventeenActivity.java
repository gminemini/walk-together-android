package com.custu.project.walktogether;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.custu.project.project.walktogether.R;

import com.custu.project.walktogether.util.BasicActivity;

import java.io.File;
import java.io.FileOutputStream;

public class QuestionSeventeenActivity extends AppCompatActivity implements BasicActivity,View.OnClickListener{
    private DrawImage drawImage;
    private Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_seventeen);
        setUI();

//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(QuestionSeventeenActivity.this, QuestionEighteenActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void initValue() {
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


    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    private void takeScreenshot() {
        try {
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, "capture_user_draw.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public Boolean compareImage() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        int count = 0;
        float percentage;

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file2 = new File(directory, "capture_user_draw.jpg");

        Bitmap mainImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.capture);
        Bitmap userDraw = BitmapFactory.decodeFile(file2.getPath());

        userDraw = Bitmap.createScaledBitmap(userDraw, mainImage.getWidth(), mainImage.getHeight(), true);


        for (int y = 0; y < mainImage.getHeight(); ++y){
            for (int x = 0; x < mainImage.getWidth(); ++x){
                if (mainImage.getPixel(x, y) == userDraw.getPixel(x, y)) {
                    count++;
                }
            }
        }
        percentage = (count * 100) / (mainImage.getHeight() * mainImage.getWidth());
        Log.d("onClick: ", "onClick: "+percentage);
        return mainImage.sameAs(userDraw);
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.delete_draw:
                drawImage.clear();
                break;
            case R.id.next:
//                takeScreenshot();
                Intent intent = new Intent(QuestionSeventeenActivity.this, QuestionEighteenActivity.class);
                startActivity(intent);

                break;
        }
    }
}
