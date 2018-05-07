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
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.PicassoUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/2/2018 AD.
 */

public class MapMissionAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map> mapArrayList;

    public MapMissionAdapter(Context context, ArrayList<Map> mapArrayList) {
        this.mContext = context;
        this.mapArrayList = mapArrayList;
    }

    public int getCount() {
        return mapArrayList.size();
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
            view = mInflater.inflate(R.layout.list_map, parent, false);

        ImageView imageView = view.findViewById(R.id.image_map);
        PicassoUtil.getInstance().setImageNoCatch(mContext, mapArrayList.get(position).getImage(), imageView);

        TextView name = view.findViewById(R.id.place);
        name.setText(mapArrayList.get(position).getNamePlace());
        return view;
    }
}
