package com.custu.project.walktogether;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.AddPatientPagerAdapter;
import com.custu.project.walktogether.adapter.HomePatientPagerAdapter;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

public class ReHomeCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private TabLayout tabLayout;
    private TabItem pfTabItem;
    private TabItem plTabItem;
    private TextView logout;
    private RelativeLayout tabAdd;

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


    public void setUI() {
        tabAdd = findViewById(R.id.add);
        logout = findViewById(R.id.logout);
        tabLayout = findViewById(R.id.tabs);
        pfTabItem = findViewById(R.id.tab_profile);
        plTabItem = findViewById(R.id.tab_patientlist);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));

        HomePatientPagerAdapter adapter = new HomePatientPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(ReHomeCaretakerActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomeCaretakerActivity.this, R.color.colorBackground);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomeCaretakerActivity.this, R.color.colorMiddleGray);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setCurrentItem(0);
    }


    private void setListener() {
        tabAdd.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (imm != null) {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
