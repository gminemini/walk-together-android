package com.custu.project.walktogether;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.chaos.view.PinView;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class AddPatientFragment extends Fragment implements BasicActivity, View.OnClickListener, TextWatcher {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentActivity context;
    private View view;

    private Button addButton;
    private Button scanQrCode;
    private PullRefreshLayout pullRefreshLayout;
    private LinearLayout resultLayout;
    private ImageView imageView;
    private TextView name;
    private TextView age;
    private TextView sex;
    private TextView notFoundTextView;
    private PinView pinView;

    private Patient patient;
    private Caretaker caretaker;

    private OnFragmentInteractionListener mListener;

    OnDataSuccessListener patientListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                pullRefreshLayout.setRefreshing(false);
                if (!object.get("data").isJsonNull()) {
                    patient = PatientModel.getInstance().getPatient(object);
                    setDataToUi();
                } else {
                    pullRefreshLayout.setRefreshing(false);
                    notFoundTextView.setVisibility(View.VISIBLE);
                    notFoundTextView.setText(object.get("message").getAsString());
                }
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
            NetworkUtil.isOnline(context, addButton);
            pullRefreshLayout.setRefreshing(false);
        }
    };

    public AddPatientFragment() {
        // Required empty public constructor
    }

    public static AddPatientFragment newInstance(String param1, String param2) {
        AddPatientFragment fragment = new AddPatientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_add_patien, container, false);
        initValue();
        setUI();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        switch (editable.length()) {
            case 0:
                pinView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                break;
            case 1:
                pinView.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 7:
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                getData();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void initValue() {
        caretaker = UserManager.getInstance(context).getCaretaker();
    }

    @Override
    public void setUI() {
        imageView = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        sex = view.findViewById(R.id.sex);
        resultLayout = view.findViewById(R.id.result);
        pullRefreshLayout = view.findViewById(R.id.refresh);
        addButton = view.findViewById(R.id.add_user);
        notFoundTextView = view.findViewById(R.id.not_found);
        scanQrCode = view.findViewById(R.id.scan_qr_code);
        pinView = view.findViewById(R.id.pinView);
        setListener();
    }

    private void setDataToUi() {
        notFoundTextView.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        name.setText(patient.getFirstName() + " " + patient.getLastName());
        age.setText(patient.getAge());
        sex.setText(patient.getSex().getName());
        PicassoUtil.getInstance().setImageProfile(context, patient.getImage(), imageView);
    }

    @Override
    public void getData() {
        resultLayout.setVisibility(View.GONE);
        notFoundTextView.setVisibility(View.GONE);
        ConnectServer.getInstance().get(patientListener, ConfigService.PATIENT
                + ConfigService.PATIENT_BY_NUMBER
                + pinView.getText().toString());
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        addButton.setOnClickListener(this);
        scanQrCode.setOnClickListener(this);
        pinView.addTextChangedListener(this);
        pinView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        pinView.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void addUser(String patientNumber) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idCaretaker", caretaker.getId());
        jsonObject.addProperty("patientNumber", patientNumber);
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                int status = object.get("status").getAsInt();
                if (status == 200) {
                    Intent intent = new Intent(context, ReHomeCaretakerActivity.class);
                    intent.putExtra("page", "list");
                    startActivity(intent);
                } else {
                    notFoundTextView.setVisibility(View.VISIBLE);
                    notFoundTextView.setText(object.get("message").getAsString());
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
                NetworkUtil.isOnline(context, imageView);
            }
        }, ConfigService.MATCHING + ConfigService.MATCHING_ADD_PATIENT, jsonObject);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.add_user: {
                addUser(pinView.getText().toString().trim());
                break;
            }

        }
    }
}
