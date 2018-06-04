package com.custu.project.walktogether.adapter;

/**
 * Created by pannawatnokket on 9/3/2018 AD.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.controller.caretaker.ListPatientFragment;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.PicassoUtil;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

public class ListViewAdapter extends BaseSwipeAdapter {
    private final ListPatientFragment listPatientFragment;
    private ArrayList<Patient> patientArrayList;
    private Context mContext;

    public ListViewAdapter(Context mContext, ArrayList<Patient> patientArrayList, ListPatientFragment listPatientFragment) {
        this.mContext = mContext;
        this.patientArrayList = patientArrayList;
        this.listPatientFragment = listPatientFragment;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        final boolean[] isOpen = {false};
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        PicassoUtil.getInstance().setImageProfile(mContext, patientArrayList.get(position).getImage(), imageView);

        RelativeLayout openRelativeLayout = view.findViewById(R.id.swipe_layout);
        final SwipeLayout swipeLayout = view.findViewById(R.id.swipe);

        TextView name = view.findViewById(R.id.name);
        name.setText(patientArrayList.get(position).getFirstName() + " " + patientArrayList.get(position).getLastName());

        TextView age = view.findViewById(R.id.age);
        age.setText(patientArrayList.get(position).getAge());

        TextView sex = view.findViewById(R.id.sex);
        sex.setText(patientArrayList.get(position).getSex().getName());

        view.findViewById(R.id.delete).setOnClickListener(view12 -> listPatientFragment.showDialog(mContext, patientArrayList.get(position).getPatientNumber(), position));


        openRelativeLayout.setOnClickListener(view1 -> {
            if (!isOpen[0]) {
                isOpen[0] = !isOpen[0];
                swipeLayout.open(true);
            } else {
                isOpen[0] = !isOpen[0];
                swipeLayout.close(true);
            }

        });


        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
    }

    @Override
    public int getCount() {
        return patientArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

