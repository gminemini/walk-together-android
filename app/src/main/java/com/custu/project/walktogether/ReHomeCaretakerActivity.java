package com.custu.project.walktogether;

import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

public class ReHomeCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout content;
    private ListNameFragment listNameFragment = new ListNameFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private BottomNavigationView bottomNavigationView;
    private RelativeLayout addRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_re_homecaretaker);
        setUI();
        setListener();
    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        content = (FrameLayout) findViewById(R.id.content);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        addRelativeLayout = findViewById(R.id.add);
        if (getIntent().getStringExtra("page")!=null) {
            String page = getIntent().getStringExtra("page");
            if (page.equalsIgnoreCase("profile"))
                openFragment(profileFragment);
            if (page.equalsIgnoreCase("list"))
                openFragment(listNameFragment);
        } else {
            openFragment(profileFragment);
        }
    }

    public void setListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addRelativeLayout.setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(ReHomeCaretakerActivity.this, AddTabActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                UserManager.getInstance(ReHomeCaretakerActivity.this).removeCaretaker();
                intent = new Intent(ReHomeCaretakerActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
