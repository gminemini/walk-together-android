package com.custu.project.walktogether;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
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
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNameFragment extends Fragment implements BasicActivity, View.OnClickListener{
    private View view;
    private FragmentActivity context;
    private SwipeMenuListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;
    private SwipeMenuCreator creator;

    private ArrayList<Patient> patientArrayList;
    private Caretaker caretaker;

    OnDataSuccessListener caretakerListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                caretaker = CaretakerModel.getInstance().getCaretaker(object);
                setDataUser();
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

        }
    };

    public ListNameFragment() {
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listname, container, false);
        initValue();
        getDataUser();
        initProgressDialog();
        SwipeMenuCreator();
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

    private void SwipeMenuCreator() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        context);
                deleteItem.setTitle("ลบ");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setBackground(R.color.colorDelete);
                deleteItem.setWidth((int) dp2px());
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private float dp2px() {
        float scale = getResources().getDisplayMetrics().density;
        return 80 * scale + 0.5f;
    }

    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePatient(patientArrayList.get(index).getPatientNumber(), caretaker.getId(), position);
                        break;
                }
                return false;
            }
        });

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
        listView = view.findViewById(R.id.list_patient);
        PatientAdapter patientAdapter = new PatientAdapter(context, patientArrayList);
        listView.setMenuCreator(creator);
        listView.setAdapter(patientAdapter);
    }

    public void setDataUser() {
        TextView nameTextView = view.findViewById(R.id.name);
        CircleImageView circleImageView = view.findViewById(R.id.image);
        nameTextView.setText("คุณ" + caretaker.getFirstName() + " " + caretaker.getLastName());
        PicassoUtil.getInstance().setImage(context, caretaker.getImage(), circleImageView);
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
}
