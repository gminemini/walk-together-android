package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.ConfigService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/2/2018 AD.
 */

public class PatientAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Patient> patientArrayList;

    public PatientAdapter(Context context, ArrayList<Patient> patientArrayList) {
        this.mContext = context;
        this.patientArrayList = patientArrayList;
    }

    public int getCount() {
        return patientArrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.list_patient_under_caretaker, parent, false);

        ImageView imageView = view.findViewById(R.id.image);

        if (patientArrayList.get(position).getImage() != null) {
            Picasso.with(mContext).invalidate(ConfigService.BASE_URL_IMAGE + patientArrayList.get(position).getImage());
            Picasso.with(mContext)
                    .load(ConfigService.BASE_URL_IMAGE + patientArrayList.get(position).getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageView);
        }

        TextView name = view.findViewById(R.id.name);
        name.setText(patientArrayList.get(position).getFirstName() + " " + patientArrayList.get(position).getLastName());

        TextView age = view.findViewById(R.id.age);
        age.setText(patientArrayList.get(position).getAge());

        TextView sex = view.findViewById(R.id.sex);
        sex.setText(patientArrayList.get(position).getSex().getName());
        return view;
    }
}
