package com.custu.project.walktogether;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.ListViewAdapter;
import com.custu.project.walktogether.adapter.PatientAdapter;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.ErrorDialog;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.daimajia.swipe.SwipeLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class ListNameFragment extends Fragment implements BasicActivity, View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private FragmentActivity context;
    private ListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;
    private SwipeMenuCreator creator;

    private ArrayList<Patient> patientArrayList;
    private Caretaker caretaker;

    private ListViewAdapter mAdapter;


    OnDataSuccessListener caretakerListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                caretaker = CaretakerModel.getInstance().getCaretaker(object);
            } else {
                ErrorDialog.getInstance().showDialog(context, object.get("message").getAsString());
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
            NetworkUtil.isOnline(context, listView);
            pullRefreshLayout.setRefreshing(false);
        }
    };

    OnDataSuccessListener patientListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                pullRefreshLayout.setRefreshing(false);
                patientArrayList = PatientModel.getInstance().getListPatients(object);
                Collections.reverse(patientArrayList);
                setUI();
                setListener();
            } else {
                pullRefreshLayout.setRefreshing(false);
                ErrorDialog.getInstance().showDialog(context, object.get("message").getAsString());
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
            NetworkUtil.isOnline(context, listView);
            pullRefreshLayout.setRefreshing(false);
        }
    };

    public ListNameFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listname, container, false);
        listView = view.findViewById(R.id.list_patient);
        listView.setOnItemClickListener(this);
        initValue();
        getDataUser();
        initProgressDialog();
        getData();
        return view;
    }


    @Override
    public void initValue() {
        caretaker = UserManager.getInstance(context).getCaretaker();
    }

    private void getDataUser() {
        ConnectServer.getInstance().get(caretakerListener, ConfigService.CARETAKER +
                +caretaker.getId());
    }

    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showDialog(Context context, final String patientNumber, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete_patient);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);


        titleTextView.setText(titleTextView.getText()
                + " "
                + patientArrayList.get(position).getFirstName()
                + " "
                + patientArrayList.get(position).getLastName());


        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePatient(patientNumber, caretaker.getId(), position);
                dialog.dismiss();
            }
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void deletePatient(String patientNumber, Long caretakerId, final int index) {
        ConnectServer
                .getInstance()
                .delete(new OnDataSuccessListener() {
                            @Override
                            public void onResponse(JsonObject object, Retrofit retrofit) {
                                int status = object.get("status").getAsInt();
                                if (status == 200) {
                                    patientArrayList.remove(index);
                                    setUI();
                                } else {
                                    ErrorDialog.getInstance().showDialog(context, object.get("message").getAsString());
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
                                NetworkUtil.isOnline(context, listView);
                                pullRefreshLayout.setRefreshing(false);
                            }
                        }, ConfigService.MATCHING
                                + ConfigService.MATCHING_REMOVE_PATIENT
                                + caretakerId
                                + ConfigService.MATCHING_PATIENT_NUMBER
                                + patientNumber
                );

    }

    @Override
    public void setUI() {
        mAdapter = new ListViewAdapter(context, patientArrayList, ListNameFragment.this);
        listView = view.findViewById(R.id.list_patient);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //((SwipeLayout)(listView.getChildAt(position - listView.getFirstVisiblePosition()))).open(true);
                Patient patient = patientArrayList.get(position);
                Intent intent = new Intent(context, PatientDetailActivity.class);
                intent.putExtra("name", patient.getTitleName() + patient.getFirstName() + " " + patient.getLastName());
                intent.putExtra("idPatient", patient.getId());
                startActivity(intent);

            }
        });
    }

    @Override
    public void getData() {
        pullRefreshLayout = view.findViewById(R.id.refresh_layout);
        pullRefreshLayout.setRefreshing(true);
        ConnectServer.getInstance().get(patientListener, ConfigService.MATCHING +
                ConfigService.MATCHING_PATIENT_UNDER_CARETAKER + caretaker.getId());
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
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
            case R.id.logout:
                UserManager.getInstance(context).removeCaretaker();
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(context, AddPatientAcctivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Patient patient = patientArrayList.get(i);
        Intent intent = new Intent(context, PatientDetailActivity.class);
        intent.putExtra("name", patient.getTitleName() + patient.getFirstName() + " " + patient.getLastName());
        intent.putExtra("idPatient", patient.getId());
        startActivity(intent);
    }
}
