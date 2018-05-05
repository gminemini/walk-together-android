package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.util.PicassoUtil;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/2/2018 AD.
 */

public class CollectionAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Collection> collectionArrayList;

    public CollectionAdapter(Context context, ArrayList<Collection> collectionArrayList) {
        this.mContext = context;
        this.collectionArrayList = collectionArrayList;
    }

    public int getCount() {
        return collectionArrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(     Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate(R.layout.collection_item, parent, false);
        } else {
            v = (View) convertView;
        }


        if (collectionArrayList.get(position).getIsReceive() && !collectionArrayList.get(position).getIsLock() ){
            ImageView imageView = convertView.findViewById(R.id.image_map);
            PicassoUtil.getInstance().setImage(mContext, collectionArrayList.get(position).getReward().getImage(), imageView);
        }


        return v;
    }

}
