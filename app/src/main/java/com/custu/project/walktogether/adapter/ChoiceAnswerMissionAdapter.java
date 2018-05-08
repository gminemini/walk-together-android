package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.mission.AnswerMission;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/2/2018 AD.
 */

public class ChoiceAnswerMissionAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AnswerMission> answerMissions;

    public ChoiceAnswerMissionAdapter(Context context, ArrayList<AnswerMission> answerMissions) {
        this.mContext = context;
        this.answerMissions = answerMissions;
    }

    public int getCount() {
        return answerMissions.size();
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
            view = mInflater.inflate(R.layout.list_choice_mission, parent, false);

        TextView name = view.findViewById(R.id.choice);
        name.setText(answerMissions.get(position).getAnswer());
        return view;
    }
}
