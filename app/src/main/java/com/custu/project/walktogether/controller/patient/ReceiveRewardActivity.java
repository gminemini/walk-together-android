package com.custu.project.walktogether.controller.patient;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.PicassoUtil;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ReceiveRewardActivity extends AppCompatActivity implements BasicActivity {
    private LottieAnimationView giftLottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_reward);
        getSupportActionBar().hide();
        setUI();

    }

    @Override
    public void initValue() {
        int splashInterval = 1000;
        new Handler().postDelayed(() -> {
                giftLottieAnimationView.setVisibility(View.GONE);
                showDialog();
        }, splashInterval);
    }

    @Override
    public void setUI() {
        giftLottieAnimationView = findViewById(R.id.gift);
        initValue();
    }

    @Override
    public void getData() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, ConfigService.RANDOM_REWARD);

    }

    @Override
    public void initProgressDialog() {

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(ReceiveRewardActivity.this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog_reward);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imageView = dialog.findViewById(R.id.image);
        PicassoUtil.getInstance().setImage(ReceiveRewardActivity.this, "image/reward/5999/file.jpg", imageView);

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(view -> dialog.dismiss());
        dialog.show();

    }
}
