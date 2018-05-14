package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

public class CollectionAdapter extends BaseAdapter {

    private Context mContext;
    private int size;
    private ArrayList<Album> albumArrayList;

    public CollectionAdapter(Context context, ArrayList<Album> albumArrayList, int size) {
        this.mContext = context;
        this.albumArrayList = albumArrayList;
        this.size = size;
    }

    @Override
    public int getCount() {
        return albumArrayList.size();
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
            view = inflater.inflate(R.layout.collection_new_item, parent, false);
        } else {
            view = convertView;
        }
//        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, size));


//        ImageView showRewardImageView = view.findViewById(R.id.image_reward);
//        ImageView showRewardImageView2 = view.findViewById(R.id.lock);
        TextView name = view.findViewById(R.id.name);

        ImageView showimg1 = view.findViewById(R.id.image_reward1);
        ImageView showimg2 = view.findViewById(R.id.image_reward2);
        ImageView showimg3 = view.findViewById(R.id.image_reward3);
        ImageView showimg4 = view.findViewById(R.id.image_reward4);
        ArrayList<ImageView> showimg = new ArrayList<ImageView>();
        showimg.add(showimg1);
        showimg.add(showimg2);
        showimg.add(showimg3);
        showimg.add(showimg4);


        LinearLayout lay1 = view.findViewById(R.id.lay1);
        LinearLayout lay2 = view.findViewById(R.id.lay2);
        LinearLayout lay3 = view.findViewById(R.id.lay3);
        LinearLayout lay4 = view.findViewById(R.id.lay4);
        ArrayList<LinearLayout> lay = new ArrayList<LinearLayout>();
        lay.add(lay1);
        lay.add(lay2);
        lay.add(lay3);
        lay.add(lay4);

//        showRewardImageView.requestLayout();
//        showRewardImageView2.requestLayout();

//        showRewardImageView.getLayoutParams().height = (width/2 );

//        showRewardImageView.getLayoutParams().height = showRewardImageView.getLayoutParams().width;

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        if (albumArrayList.get(position).getLock()) {
            RelativeLayout relativeLayout = view.findViewById(R.id.head);
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            LinearLayout linearLayout = view.findViewById(R.id.fourbox);
            linearLayout.setVisibility(View.VISIBLE);


            for (int i = 0; i < albumArrayList.get(position).getPreviewImage().size(); i++) {
                if (albumArrayList.get(position).getPreviewImage().get(i).getIsReceive()) {
                    PicassoUtil.getInstance().setImageNoCatch(mContext, albumArrayList.get(position).getPreviewImage().get(i).getReward().getImage(), showimg.get(i));

                } else if (!albumArrayList.get(position).getPreviewImage().get(i).getIsReceive() && !albumArrayList.get(position).getPreviewImage().get(i).getIsLock()) {
                    if (Build.VERSION.SDK_INT > 22)
                        PicassoUtil.getInstance().setImageNoCatch(mContext, albumArrayList.get(position).getPreviewImage().get(i).getReward().getImage(), showimg.get(i));
                    else
                        PicassoUtil.getInstance().setImage(mContext, albumArrayList.get(position).getPreviewImage().get(i).getReward().getImage(), showimg.get(i));

                    showimg.get(i).setColorFilter(filter);
                }else {
                    lay.get(i).setPadding(14,14,14,14);
                }
            }

        }
        name.setText(albumArrayList.get(position).getAlbumName());


//        showRewardImageView.getLayoutParams().height = showRewardImageView.getMeasuredWidth();
//        showRewardImageView2.getLayoutParams().height = showRewardImageView.getMeasuredWidth();
        return view;
    }

}
