package com.custu.project.walktogether.controller.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.CollectionAdapter;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CollectionModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.DialogQrCode;
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
    private CollectionAdapter mAdapter;
    private GridView gridView;
    private ArrayList<Collection> collectionArrayList;
    private Patient patient;

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
        setUI();
        getData();
        return view;
    }

    @Override
    public void initValue() {
        if (collectionArrayList.size() > 0) {
//            mAdapter = new CollectionAdapter(context, collectionArrayList,1);
            gridView.setAdapter(mAdapter);

            gridView.setOnItemClickListener((parent, view, position, id) -> {
                if (collectionArrayList.get(position).getIsReceive()){
                    ((ReHomePatientActivity) context).openRewardDetail(collectionArrayList.get(position).getReward());
                }


            });

            gridView.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUI() {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        gridView = view.findViewById(R.id.gridview);
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void getData() {
        patient = UserManager.getInstance(context).getPatient();
            ConnectServer.getInstance().get(new OnDataSuccessListener() {
                @Override
                public void onResponse(JsonObject object, Retrofit retrofit) {
                    if (object != null) {
                        if (object.get("status").getAsInt() == 200) {
                            collectionArrayList = CollectionModel.getInstance().getCollectionArrayList(object);
                            int splashInterval = new Random().nextInt(1500) + 500;
                            new Handler().postDelayed(() -> initValue(), splashInterval);
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
                    NetworkUtil.isOnline(context, gridView);

                }
            }, ConfigService.COLLECTION_BY_PATIENT + patient.getId());
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
