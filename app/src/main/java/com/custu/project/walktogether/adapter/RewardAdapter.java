package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.collection.Album;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.util.PicassoUtil;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/2/2018 AD.
 */

public class RewardAdapter extends BaseAdapter {

    private Context mContext;
    private int size;
    private ArrayList<Collection> collectionArrayList;

    public RewardAdapter(Context context, ArrayList<Collection> collectionArrayList) {
        this.mContext = context;
        this.collectionArrayList = collectionArrayList;
        this.size = size;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.collection_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView lock = view.findViewById(R.id.picture);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        if (collectionArrayList.get(position).getIsReceive()) {
            PicassoUtil.getInstance().setImage(mContext, collectionArrayList.get(position).getReward().getImage(), lock);

        } else if(!collectionArrayList.get(position).getIsReceive() && !collectionArrayList.get(position).getIsLock()){
            PicassoUtil.getInstance().setImage(mContext, collectionArrayList.get(position).getReward().getImage(), lock);
            lock.setColorFilter(filter);
        }else {
            lock.setPadding(14,14,14,14);
        }
//        name.setText(albumArrayList.get(position).getAlbumName());


        return view;
    }

}
