package com.custu.project.walktogether.controller.patient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.airbnb.lottie.animation.content.Content;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.CollectionAdapter;
import com.custu.project.walktogether.adapter.RewardAdapter;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.model.CollectionModel;
import com.custu.project.walktogether.util.BasicActivity;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity implements BasicActivity, AdapterView.OnItemClickListener {
    private ArrayList<Collection> collectionArrayList;
    private RewardAdapter rewardAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
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

//        shimmerFrameLayout.startShimmerAnimation();

    }

    @Override
    public void getData() {
        collectionArrayList = CollectionModel.getInstance().getCollections(getIntent().getStringExtra("collection"));
        rewardAdapter = new RewardAdapter(CollectionActivity.this, collectionArrayList);
        rewardAdapter.notifyDataSetChanged();
        gridView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
    }

    @Override
    public void initProgressDialog() {

    }
}
