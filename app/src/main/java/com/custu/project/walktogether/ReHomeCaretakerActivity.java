package com.custu.project.walktogether;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;

public class ReHomeCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout content;
    private ListNameFragment listNameFragment = new ListNameFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_homecaretaker);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        content = (FrameLayout) findViewById(R.id.content);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
    }

    public void setListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    public void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_recent:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                openFragment(profileFragment);
                return true;
            case R.id.item_favorite:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                openFragment(listNameFragment);
                return true;
        }
        return false;
    }
}
