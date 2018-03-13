package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class SelectMissionFragment extends Fragment implements BasicActivity, View.OnClickListener {
    private ProgressDialog progressDialog;
    private FragmentActivity context;
    private View view;


    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    public SelectMissionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_choosemap, container, false);
        view.findViewById(R.id.map1).setOnClickListener(this);
        view.findViewById(R.id.map2).setOnClickListener(this);
        view.findViewById(R.id.map3).setOnClickListener(this);
        view.findViewById(R.id.map4).setOnClickListener(this);
        initProgressDialog();
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        progressDialog.show();
        Intent intent;
        switch (id) {
            case R.id.map1:
                intent = new Intent(context, MapsActivity.class);
                intent.putExtra("map", "interPark");
                startActivity(intent);
                break;
            case R.id.map2:
                intent = new Intent(context, MapsActivity.class);
                intent.putExtra("map", "lc2");
                startActivity(intent);
                break;
            case R.id.map3:
                intent = new Intent(context, MapsActivity.class);
                intent.putExtra("map", "ekkami");
                startActivity(intent);
                break;
            case R.id.map4:
                intent = new Intent(context, MapsActivity.class);
                intent.putExtra("map", "myHome");
                startActivity(intent);
                break;
        }

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }
}
