package com.custu.project.walktogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.UserManager;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class SplashScreenActivity extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        ProgressBar progressBar = findViewById(R.id.progress);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);


        int splashInterval = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent;
                if (UserManager.getInstance(SplashScreenActivity.this).getCaretaker() != null) {
                    intent = new Intent(SplashScreenActivity.this, ReHomeCaretakerActivity.class);
                    startActivity(intent);
                } else  if (UserManager.getInstance(SplashScreenActivity.this).getPatient() != null){
                    intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, splashInterval);
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
}

