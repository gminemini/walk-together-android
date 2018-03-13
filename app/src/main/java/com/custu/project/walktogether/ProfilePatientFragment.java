package com.custu.project.walktogether;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ProfilePatientFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextView name;
    private TextView sex;
    private TextView age;
    private TextView tell;
    private TextView occupation;
    private Button logout;
    private TextView email;
    private PullRefreshLayout pullRefreshLayout;

    private FragmentActivity context;
    private Patient patient;

    public ProfilePatientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_patient, container, false);
        setUI();
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        imageView = view.findViewById(R.id.image_profile);
        name = view.findViewById(R.id.name);
        sex = view.findViewById(R.id.sex);
        age = view.findViewById(R.id.age);
        tell = view.findViewById(R.id.tell);
        occupation = view.findViewById(R.id.occupation);
        logout = view.findViewById(R.id.logout);
        email = view.findViewById(R.id.email);
        pullRefreshLayout  = view.findViewById(R.id.pull_refresh);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager.getInstance(context).removePatient();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });

    }

    private void initValue() {
        PicassoUtil.getInstance().setImageProfile(context, patient.getImage(), imageView);
        name.setText(patient.getTitleName()
                + ""
                + patient.getFirstName()
                + " "
                + patient.getLastName());
        sex.setText(patient.getSex().getName());
        age.setText(patient.getAge());
        tell.setText(patient.getTell());
        email.setText(patient.getEmail());
        occupation.setText(patient.getOccupation());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager.getInstance(context).removePatient();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.setRefreshing(true);
                getData();
            }
        });

    }

    public void getData() {
        patient = UserManager.getInstance(context).getPatient();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                pullRefreshLayout.setRefreshing(false);
                if(object!=null) {
                    patient = PatientModel.getInstance().getPatient(object.getAsJsonObject("data"));
                    UserManager.getInstance(context).storePatient(patient);
                    patient = UserManager.getInstance(context).getPatient();
                    initValue();
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
                pullRefreshLayout.setRefreshing(false);
                patient = UserManager.getInstance(context).getPatient();
                NetworkUtil.isOnline(context, imageView);
                initValue();
            }
        }, ConfigService.PATIENT+patient.getId());
    }
}
