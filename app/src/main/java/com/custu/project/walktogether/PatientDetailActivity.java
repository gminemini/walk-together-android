package com.custu.project.walktogether;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.HomePatientPagerAdapter;
import com.custu.project.walktogether.adapter.PatientDetailPagerAdapter;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

public class PatientDetailActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private String name;
    private Long idPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_patient_detail);
        getData();
        setUI();
        setListener();
    }

    @Override
    public void initValue() {

    }


    public void setUI() {
        TextView titleTextView = findViewById(R.id.title);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));
        titleTextView.setText(name);

        PatientDetailPagerAdapter adapter = new PatientDetailPagerAdapter(getSupportFragmentManager(), idPatient);
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(PatientDetailActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(PatientDetailActivity.this, R.color.colorBackground);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(PatientDetailActivity.this, R.color.colorMiddleGray);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setCurrentItem(0);
    }


    private void setListener() {
    }

    @Override
    public void getData() {
        name = getIntent().getStringExtra("name");
        idPatient = getIntent().getLongExtra("idPatient", 0);
    }

    @Override
    public void initProgressDialog() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
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
