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
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextView name;
    private TextView sex;
    private TextView age;
    private TextView tell;
    private TextView occupation;
    private Button logout;

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
        setUI();
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

        PicassoUtil.getInstance().setImageProfile(context, caretaker.getImage(), imageView);
        name.setText(caretaker.getTitleName()
                + ""
                + caretaker.getFirstName()
                + " "
                + caretaker.getLastName());
        sex.setText(caretaker.getSex().getName());
        age.setText(caretaker.getAge());
        tell.setText(caretaker.getTell());
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
    }
}
