package com.custu.project.walktogether;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class AddCaretakerQRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ZXingScannerView mScannerView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_CAMERARESULT = 201;

    private String mParam1;
    private String mParam2;

    /*view variable*/
    private View view;
    private FragmentActivity context;

    public AddCaretakerQRCodeFragment() {

    }

    public static AddCaretakerQRCodeFragment newInstance(String param1, String param2) {
        AddCaretakerQRCodeFragment fragment = new AddCaretakerQRCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                ///method to get Images
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERARESULT);
            }
        }
        this.view = inflater.inflate(R.layout.fragment_add_caretaker_qrcode, container, false);
        mScannerView = new ZXingScannerView(context);
        return mScannerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    public void handleResult(final Result result) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(AddCaretakerQRCodeFragment.this);
                addUser(result.getText());
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.startCamera();
        mScannerView.setResultHandler(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    private void addUser(String caretakerNumber) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idPatient", UserManager.getInstance(context).getPatient().getId());
        jsonObject.addProperty("caretakerNumber", caretakerNumber);
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                int status = object.get("status").getAsInt();
                if (status == 200) {
                    Intent intent = new Intent(context, ReHomePatientActivity.class);
                    intent.putExtra("page", "list");
                    startActivity(intent);
                } else {
                    NetworkUtil.showMessageResponse(context, mScannerView, object.get("message").getAsString());
                }

            }

            @Override
            public void onBodyError(ResponseBody responseBodyError) {

            }

            @Override
            public void onBodyErrorIsNull() {

            }

            @Override
            public void onFailure(Throwable t) {
                NetworkUtil.isOnline(context, view);
            }
        }, ConfigService.MATCHING + ConfigService.MATCHING_ADD_CARETAKER, jsonObject);
    }

}