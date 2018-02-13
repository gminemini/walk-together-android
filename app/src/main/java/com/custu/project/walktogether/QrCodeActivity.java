package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.custu.project.project.walktogether.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        intent.putExtra("patientNumber", result.getText());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }
}
