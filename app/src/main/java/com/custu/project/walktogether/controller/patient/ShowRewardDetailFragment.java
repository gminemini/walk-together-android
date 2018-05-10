package com.custu.project.walktogether.controller.patient;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.data.collection.Reward;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.PicassoUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowRewardDetailFragment extends Fragment {
    private View view;
    private TextView rewardname;
    private TextView detailname;
    private TextView lv;
    private ImageView dismissImageView ,head;

    private FragmentActivity context;
    private Reward reward;

    public ShowRewardDetailFragment() {
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
        view = inflater.inflate(R.layout.reward_dialog_detail, container, false);
        setUI();
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        final Fragment fragment = this;
        head = view.findViewById(R.id.head);
        rewardname = view.findViewById(R.id.rewardname);
        detailname = view.findViewById(R.id.detailname);
        lv = view.findViewById(R.id.lv);
        dismissImageView = view.findViewById(R.id.dismiss);
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
        PicassoUtil.getInstance().setImageNoCatch(context, reward.getImage(),head);
        rewardname.setText(String.valueOf(reward.getRewardName()));
        detailname.setText(reward.getDetail());
        lv.setText(String.valueOf(reward.getLevel()));
    }

    public void getData() {
        if (getArguments() != null) {
            reward = DataFormat.getInstance().getGsonParser().fromJson(getArguments().getString("reward"), Reward.class);
        }
        initValue();
    }
}
