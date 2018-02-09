package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class AddPatientAcctivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private EditText inputNumber;
    private Button addButton;
    private Button searchButton;
    private PullRefreshLayout pullRefreshLayout;
    private LinearLayout resultLayout;
    private ImageView imageView;
    private TextView name;
    private TextView age;
    private TextView sex;
    private TextView notFoundTextView;

    private Patient patient;
    private Caretaker caretaker;

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

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initValue();
        setUI();
        setListener();
    }

    @Override
    public void initValue() {
        caretaker = UserManager.getInstance(AddPatientAcctivity.this).getCaretaker();
    }

    @Override
    public void setUI() {
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        sex = findViewById(R.id.sex);
        resultLayout = findViewById(R.id.result);
        pullRefreshLayout = findViewById(R.id.refresh);
        addButton = findViewById(R.id.add_user);
        inputNumber = findViewById(R.id.number);
        searchButton = findViewById(R.id.search);
        notFoundTextView = findViewById(R.id.not_found);
    }

    private void setDataToUi() {
        notFoundTextView.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        name.setText(patient.getFirstName() + " " + patient.getLastName());
        age.setText(patient.getAge());
        sex.setText(patient.getSex().getName());

        if (patient.getImage() != null) {
            Picasso.with(AddPatientAcctivity.this).invalidate(ConfigService.BASE_URL_IMAGE + patient.getImage());
            Picasso.with(AddPatientAcctivity.this)
                    .load(ConfigService.BASE_URL_IMAGE + patient.getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        } else {
            Picasso.with(AddPatientAcctivity.this)
                    .load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }
    }

    @Override
    public void getData() {
        resultLayout.setVisibility(View.GONE);
        notFoundTextView.setVisibility(View.GONE);
        ConnectServer.getInstance().get(patientListener, ConfigService.PATIENT
                + ConfigService.PATIENT_BY_NUMBER
                + inputNumber.getText().toString());
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        addButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    private void addUser() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idCaretaker", caretaker.getId());
        jsonObject.addProperty("patientNumber", inputNumber.getText().toString().trim());
        ConnectServer.getInstance().post(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                int status = object.get("status").getAsInt();
                if (status == 200) {
                    Intent intent = new Intent(AddPatientAcctivity.this, HomeCaretakerActivity.class);
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

            }
        }, ConfigService.MATCHING + ConfigService.MATCHING_ADD_PATIENT, jsonObject);
    }

    private boolean validate() {

        if (inputNumber.getText().toString().length() == 0)
            inputNumber.setError("กรุณาใส่หมายเลขผู้ป่วย");

        return inputNumber.getText().toString().length() != 0;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search: {
                if (validate()) {
                    pullRefreshLayout.setRefreshing(true);
                    getData();
                }
                break;
            }
            case R.id.add_user: {
                addUser();
                break;
            }

        }
    }
}
