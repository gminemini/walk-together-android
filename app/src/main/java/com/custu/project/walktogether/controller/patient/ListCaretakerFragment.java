package com.custu.project.walktogether.controller.patient;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.ListViewCaretakerAdapter;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;


public class ListCaretakerFragment extends Fragment implements BasicActivity, View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private FragmentActivity context;
    private ListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;

    private ArrayList<Caretaker> caretakerArrayList;
    private Patient patient;
    private ListViewCaretakerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_name_patient, container, false);
        listView = view.findViewById(R.id.list_patient);
        initValue();
        getDataUser();
        initProgressDialog();
        getData();
        return view;
    }

    public ListCaretakerFragment() {
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
    public void showDialog(Context context2, final String caretakerNumber, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_delete_patient);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView titleTextView = dialog.findViewById(R.id.title);
        titleTextView.setText(titleTextView.getText().toString() + " " + caretakerArrayList.get(position).getFirstName() + " " + caretakerArrayList.get(position).getLastName());
        LinearLayout done = dialog.findViewById(R.id.submit);
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ConnectServer.getInstance().delete(new OnDataSuccessListener() {
                    @Override
                    public void onResponse(JsonObject object, Retrofit retrofit) {
                        if (object.get("status").getAsInt() == 200) {
                            caretakerArrayList.remove(position);
                            setUI();

                        } else {
                            DialogUtil.getInstance().showDialogStartIntent(context, object.get("message").getAsString());
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
                }, ConfigService.MATCHING + ConfigService.MATCHING_REMOVE_CARETAKER + patient.getId() + ConfigService.MATCHING_CARETAKER_NUMBER + caretakerNumber);
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });
        dialog.show();

    }


    private void getDataUser() {
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                patient = PatientModel.getInstance().getPatient(object);
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
        }, ConfigService.PATIENT + patient.getId());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void initValue() {
        patient = UserManager.getInstance(context).getPatient();
    }

    @Override
    public void setUI() {
        mAdapter = new ListViewCaretakerAdapter(context, caretakerArrayList, ListCaretakerFragment.this);
        listView = view.findViewById(R.id.list_patient);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ((ReHomePatientActivity)getActivity()).openProfileDetail(caretakerArrayList.get(position));
            }
        });
    }

    @Override
    public void getData() {
        pullRefreshLayout = view.findViewById(R.id.refresh_layout);
        pullRefreshLayout.setRefreshing(true);
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {

                if (object != null) {
                    if (object.get("status").getAsInt() == 200) {
                        pullRefreshLayout.setRefreshing(false);
                        caretakerArrayList = CaretakerModel.getInstance().getListCaretaker(object);
                        Collections.reverse(caretakerArrayList);
                        setUI();
                        setListener();

                    } else {
                        pullRefreshLayout.setRefreshing(false);
                        DialogUtil.getInstance().showDialogStartIntent(context, object.get("message").getAsString());
                    }
                } else {
                    pullRefreshLayout.setRefreshing(false);
                    DialogUtil.getInstance().showDialogStartIntent(context, object.get("message").getAsString());
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
        }, ConfigService.MATCHING + ConfigService.MATCHING_CARETAKER_UNDER_PATIENT + patient.getId());
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }
}
