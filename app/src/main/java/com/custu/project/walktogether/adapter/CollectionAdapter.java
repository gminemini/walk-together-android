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

    @Override
    public int getCount() {
        return collectionArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.collection_item, parent, false);

        ImageView showRewardImageView = view.findViewById(R.id.image_reward);

        if (collectionArrayList.get(position).getIsReceive()){
            PicassoUtil.getInstance().setImage(mContext, collectionArrayList.get(position).getReward().getImage(), showRewardImageView);
        }

        return view;
    }

}
