package com.custu.project.walktogether.controller.caretaker;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.LoginActivity;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.DialogQrCode;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ProfileCaretakerFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextView name;
    private TextView tell;
    private TextView occupation;
    private TextView number;
    private Button logout;
    private LinearLayout qrCode;
    private TextView email;
    private PullRefreshLayout pullRefreshLayout;

    private FragmentActivity context;
    private Caretaker caretaker;

    public ProfileCaretakerFragment() {
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setUI();
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        imageView = view.findViewById(R.id.image_profile);
        name = view.findViewById(R.id.name);
        tell = view.findViewById(R.id.tell);
        occupation = view.findViewById(R.id.occupation);
        logout = view.findViewById(R.id.logout);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        qrCode = view.findViewById(R.id.qr_code);
        pullRefreshLayout = view.findViewById(R.id.pull_refresh);
    }

    private void initValue() {
        PicassoUtil.getInstance().setImageProfile(context, caretaker.getImage(), imageView);
        name.setText(caretaker.getTitleName()
                + ""
                + caretaker.getFirstName()
                + " "
                + caretaker.getLastName());
        tell.setText(caretaker.getTell());
        email.setText(caretaker.getEmail());
        number.setText(caretaker.getCaretakerNumber());
        occupation.setText(DataFormat.getInstance().validateData(caretaker.getOccupation()));
        logout.setOnClickListener(view -> {
            UserManager.getInstance(context).removeCaretaker();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.finish();
            startActivity(intent);
        });

        qrCode.setOnClickListener(view -> DialogQrCode.getInstance().showDialog(context, caretaker.getQrCode()));

        pullRefreshLayout.setOnRefreshListener(() -> {
            pullRefreshLayout.setRefreshing(true);
            getData();
        });

    }

    public void getData() {
        caretaker = UserManager.getInstance(context).getCaretaker();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                pullRefreshLayout.setRefreshing(false);
                if (object != null) {
                    caretaker = CaretakerModel.getInstance().getCaretaker(object);
                    UserManager.getInstance(context).storeCaretaker(caretaker);
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
                caretaker = UserManager.getInstance(context).getCaretaker();
                NetworkUtil.isOnline(context, imageView);
            }
        }, ConfigService.CARETAKER + caretaker.getId());
    }
}
