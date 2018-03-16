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
import com.custu.project.walktogether.ListCaretakerFragment;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.util.PicassoUtil;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

public class ListViewCaretakerAdapter extends BaseSwipeAdapter {
    private final ListCaretakerFragment listCaretakerFragment;
    private ArrayList<Caretaker> caretakerArrayList;
    private Context mContext;

    public ListViewCaretakerAdapter(Context mContext, ArrayList<Caretaker> caretakerArrayList, ListCaretakerFragment listCaretakerFragment) {
        this.mContext = mContext;
        this.caretakerArrayList = caretakerArrayList;
        this.listCaretakerFragment = listCaretakerFragment;
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
        PicassoUtil.getInstance().setImageProfile(mContext, caretakerArrayList.get(position).getImage(), imageView);

        RelativeLayout openRelativeLayout = view.findViewById(R.id.swipe_layout);
        final SwipeLayout swipeLayout = view.findViewById(R.id.swipe);

        TextView name = view.findViewById(R.id.name);
        name.setText(caretakerArrayList.get(position).getFirstName() + " " + caretakerArrayList.get(position).getLastName());

        TextView age = view.findViewById(R.id.age);
        age.setText(caretakerArrayList.get(position).getAge());

        TextView sex = view.findViewById(R.id.sex);
        sex.setText(caretakerArrayList.get(position).getSex().getName());

        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCaretakerFragment.showDialog(mContext, caretakerArrayList.get(position).getCaretakerNumber(), position);
            }
        });


        openRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen[0]) {
                    isOpen[0] = !isOpen[0];
                    swipeLayout.open(true);
                } else {
                    isOpen[0] = !isOpen[0];
                    swipeLayout.close(true);
                }

            }
        });


        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
    }

    @Override
    public int getCount() {
        return caretakerArrayList.size();
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

