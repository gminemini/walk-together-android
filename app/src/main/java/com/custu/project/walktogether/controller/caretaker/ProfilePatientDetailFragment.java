package com.custu.project.walktogether.controller.caretaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.PicassoUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePatientDetailFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextView name;
    private TextView tell;
    private TextView occupation;
    private TextView number;
    private TextView email;
    private FragmentActivity context;
    private Patient patient;

    public ProfilePatientDetailFragment() {
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
        view = inflater.inflate(R.layout.profile_detail, container, false);
        setUI();
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        final Fragment fragment = this;
        imageView = view.findViewById(R.id.image_profile);
        name = view.findViewById(R.id.name);
        tell = view.findViewById(R.id.tell);
        occupation = view.findViewById(R.id.occupation);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        ImageView dismissImageView = view.findViewById(R.id.dismiss);
        dismissImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = context.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_fragment, R.anim.exit_fragment);
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
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
        tell.setText(patient.getTell());
        email.setText(patient.getEmail());
        number.setText(patient.getPatientNumber());
        occupation.setText(DataFormat.getInstance().validateData(patient.getOccupation()));
    }

    public void getData() {
        if (getArguments() != null) {
            patient = DataFormat.getInstance().getGsonParser().fromJson(getArguments().getString("patient"), Patient.class);
        }
        initValue();
    }
}
