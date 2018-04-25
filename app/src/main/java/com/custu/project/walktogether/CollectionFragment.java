package com.custu.project.walktogether;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.CollectionAdapter;
import com.custu.project.walktogether.adapter.MapMissionAdapter;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.MissionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.UserManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class CollectionFragment extends Fragment implements BasicActivity {
    private ProgressDialog progressDialog;
    private FragmentActivity context;
    private View view;
    private ShimmerFrameLayout shimmerFrameLayout;
    private GridView gridView;

    private ArrayList<Integer> integers;

    public CollectionFragment() {
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
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        initProgressDialog();
        getData();
        setUI();
        return view;
    }

    @Override
    public void initValue() {
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        gridView.setAdapter(new CollectionAdapter(context, integers));
    }

    @Override
    public void setUI() {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        gridView = view.findViewById(R.id.gridview);
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void getData() {
        integers = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            integers.add(i);
        }
        int splashInterval = new Random().nextInt(1500)+500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initValue();
            }
        }, splashInterval);
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
