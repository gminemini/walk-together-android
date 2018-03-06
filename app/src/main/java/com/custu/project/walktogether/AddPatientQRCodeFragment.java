package com.custu.project.walktogether;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import static android.app.Activity.RESULT_OK;

public class AddPatientQRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ZXingScannerView mScannerView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_CAMERARESULT = 201;

    private String mParam1;
    private String mParam2;

    private boolean isQrCode = false;
    private LinearLayout linearLayout;
    private OnFragmentInteractionListener mListener;

    /*view variable*/
    private View view;

    private FragmentActivity context;

    public AddPatientQRCodeFragment() {

    }

    public static AddPatientQRCodeFragment newInstance(String param1, String param2) {
        AddPatientQRCodeFragment fragment = new AddPatientQRCodeFragment();
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
        mScannerView = new ZXingScannerView(context);
        this.view = inflater.inflate(R.layout.fragment_add_patient_qrcode, container, false);
        linearLayout = view.findViewById(R.id.qr_code);
       // linearLayout.addView(mScannerView, 0);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        addUser(result.getText());
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private void addUser(String patientNumber) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idCaretaker", UserManager.getInstance(context).getCaretaker().getId());
        jsonObject.addProperty("patientNumber", patientNumber);
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                int status = object.get("status").getAsInt();
                if (status == 200) {
                    Intent intent = new Intent(context, ReHomeCaretakerActivity.class);
                    intent.putExtra("page", "list");
                    startActivity(intent);
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
        }, ConfigService.MATCHING + ConfigService.MATCHING_ADD_PATIENT, jsonObject);
    }
}