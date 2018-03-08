package com.custu.project.walktogether.adapter;

/**
 * Created by pannawatnokket on 9/3/2018 AD.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.ListNameFragment;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.PicassoUtil;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

public class ListViewAdapter extends BaseSwipeAdapter {
    private final ListNameFragment listNameFragment;
    private ArrayList<Patient> patientArrayList;
    private Context mContext;

    public ListViewAdapter(Context mContext, ArrayList<Patient> patientArrayList, ListNameFragment listNameFragment) {
        this.mContext = mContext;
        this.patientArrayList = patientArrayList;
        this.listNameFragment = listNameFragment;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        PicassoUtil.getInstance().setImageProfile(mContext, patientArrayList.get(position).getImage(), imageView);

        TextView name = view.findViewById(R.id.name);
        name.setText(patientArrayList.get(position).getFirstName() + " " + patientArrayList.get(position).getLastName());

        TextView age = view.findViewById(R.id.age);
        age.setText(patientArrayList.get(position).getAge());

        TextView sex = view.findViewById(R.id.sex);
        sex.setText(patientArrayList.get(position).getSex().getName());

        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listNameFragment.showDialog(mContext, patientArrayList.get(position).getPatientNumber(), position);
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

