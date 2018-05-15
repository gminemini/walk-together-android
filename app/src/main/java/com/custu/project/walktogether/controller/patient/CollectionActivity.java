package com.custu.project.walktogether.controller.patient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.RewardAdapter;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.model.CollectionModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.PicassoUtil;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity implements BasicActivity, AdapterView.OnItemClickListener {
    private ArrayList<Collection> collectionArrayList;
    private GridView gridView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ImageView imageView;
    private TextView textView;
    private RelativeLayout viewImageRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().hide();
        setUI();
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        gridView = findViewById(R.id.gridviewReward);
        textView = findViewById(R.id.title);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        imageView = findViewById(R.id.image);
        ImageView dismiss = findViewById(R.id.dismiss);
        viewImageRelativeLayout = findViewById(R.id.view_image);
        dismiss.setOnClickListener((v) -> viewImageRelativeLayout.setVisibility(View.GONE));
    }

    @Override
    public void getData() {
        textView.setText(getIntent().getStringExtra("name"));
        collectionArrayList = CollectionModel.getInstance().getCollections(getIntent().getStringExtra("collection"));
        RewardAdapter rewardAdapter = new RewardAdapter(CollectionActivity.this, collectionArrayList);
        rewardAdapter.notifyDataSetChanged();
        gridView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (collectionArrayList.get(i).getIsReceive()) {
                viewImageRelativeLayout.setVisibility(View.VISIBLE);
                PicassoUtil.getInstance().setImageNoCatch(CollectionActivity.this, collectionArrayList.get(i).getReward().getImage(), imageView);
            }
        });
    }

    @Override
    public void initProgressDialog() {

    }
}
