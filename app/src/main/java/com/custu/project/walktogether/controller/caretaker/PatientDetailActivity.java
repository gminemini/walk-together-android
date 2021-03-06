package com.custu.project.walktogether.controller.caretaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.PatientDetailPagerAdapter;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.DataFormat;

import me.leolin.shortcutbadger.ShortcutBadger;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PatientDetailActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private String name;
    private Patient patient;
    private Long idPatient;
    private RelativeLayout profileRelativeLayout;

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

    @SuppressLint("ClickableViewAccessibility")
    public void setUI() {
        ShortcutBadger.removeCount(this);
        TextView titleTextView = findViewById(R.id.title);
        profileRelativeLayout = findViewById(R.id.profile);

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
        profileRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void getData() {
        name = getIntent().getStringExtra("name");
        String s = getIntent().getStringExtra("patient");
        patient = DataFormat.getInstance().getGsonParser().fromJson(getIntent().getStringExtra("patient"), Patient.class);
        idPatient = patient.getId();
    }

    @Override
    public void initProgressDialog() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.profile:
                openProfileDetail();
                break;
        }
    }

    private void openProfileDetail() {
        Bundle bundle = new Bundle();
        bundle.putString("patient", getIntent().getStringExtra("patient"));
        ProfilePatientDetailFragment fragment = new ProfilePatientDetailFragment();
        fragment.setArguments(bundle);
        openFragment(fragment);
    }

    public void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_fragment, R.anim.exit_fragment);
        transaction.replace(R.id.profile_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
