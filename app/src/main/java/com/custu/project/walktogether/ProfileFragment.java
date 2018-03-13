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

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {
    private View view;

    private FragmentActivity context;
    private Caretaker caretaker;

    public ProfileFragment() {
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
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        CircleImageView imageView = view.findViewById(R.id.image_profile);
        TextView name = view.findViewById(R.id.name);
        TextView sex = view.findViewById(R.id.sex);
        TextView age = view.findViewById(R.id.age);
        TextView tell = view.findViewById(R.id.tell);
        TextView occupation = view.findViewById(R.id.occupation);
        Button logout = view.findViewById(R.id.logout);
        TextView email = view.findViewById(R.id.email);

        PicassoUtil.getInstance().setImageProfile(context, caretaker.getImage(), imageView);
        name.setText(caretaker.getTitleName()
                + ""
                + caretaker.getFirstName()
                + " "
                + caretaker.getLastName());
        sex.setText(caretaker.getSex().getName());
        age.setText(caretaker.getAge());
        tell.setText(caretaker.getTell());
        email.setText(caretaker.getEmail());
        occupation.setText(caretaker.getOccupation());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager.getInstance(context).removeCaretaker();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }

    public void getData() {
        caretaker = UserManager.getInstance(context).getCaretaker();
        ConnectServer.getInstance().get(new OnDataSuccessListener() {
            @Override
            public void onResponse(JsonObject object, Retrofit retrofit) {
                if (object!=null) {
                    caretaker = CaretakerModel.getInstance().getCaretaker(object);
                    UserManager.getInstance(context).storeCaretaker(caretaker);
                    setUI();
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
        }, ConfigService.CARETAKER+caretaker.getId());
    }
}
