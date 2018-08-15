package com.custu.project.walktogether.controller.patient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.HomePatientPagerAdapter;
import com.custu.project.walktogether.controller.LoginActivity;
import com.custu.project.walktogether.controller.tmse.ConditionActivity;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.data.collection.Reward;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.UserManager;

import me.leolin.shortcutbadger.ShortcutBadger;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReHomePatientActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {

    private TabLayout tabLayout;
    private RelativeLayout editProfileRelativeLayout;
    private RelativeLayout addProfileRelativeLayout;
    private RelativeLayout historyMissionRelativeLayout;
    private TextView titleTextView;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_re_homepatient);
        initValue();
        setUI();
        setListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
            case R.id.add:
                intent = new Intent(ReHomePatientActivity.this, AddTabPatientActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_profile:
                intent = new Intent(ReHomePatientActivity.this, EditPatientProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.history_mission:
                intent = new Intent(ReHomePatientActivity.this, HistoryMissionActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setListener() {
        titleTextView.setOnClickListener(this);
        addProfileRelativeLayout.setOnClickListener(this);
        editProfileRelativeLayout.setOnClickListener(this);
        historyMissionRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void initValue() {
        page = getIntent().getStringExtra("page") == null ? "" : getIntent().getStringExtra("page");
    }

    @Override
    public void setUI() {
        ShortcutBadger.removeCount(this);
        titleTextView = findViewById(R.id.title);
        tabLayout = findViewById(R.id.tabs);
        addProfileRelativeLayout = findViewById(R.id.add);
        editProfileRelativeLayout = findViewById(R.id.edit_profile);
        historyMissionRelativeLayout = findViewById(R.id.history_mission);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));
        HomePatientPagerAdapter adapter = new HomePatientPagerAdapter(getSupportFragmentManager(), UserManager.getInstance(ReHomePatientActivity.this).getPatient());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);


        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tab = tabLayout.getTabAt(1);
        tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(2);
        tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(3);
        tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(4);
        tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);


        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorBackground);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomePatientActivity.this, R.color.colorMiddleGray);
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    titleTextView.setText("โปรไฟล์");
                    editProfileRelativeLayout.setVisibility(View.VISIBLE);
                    addProfileRelativeLayout.setVisibility(View.GONE);
                    historyMissionRelativeLayout.setVisibility(View.GONE);
                } else if (position == 1) {
                    titleTextView.setText("รายชื่อผู้ดูแล");
                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                    editProfileRelativeLayout.setVisibility(View.GONE);
                    historyMissionRelativeLayout.setVisibility(View.GONE);
                } else if (position == 2) {
                    titleTextView.setText("ภารกิจ");
                    historyMissionRelativeLayout.setVisibility(View.VISIBLE);
                    editProfileRelativeLayout.setVisibility(View.GONE);
                    addProfileRelativeLayout.setVisibility(View.GONE);
                } else if (position == 3) {
                    titleTextView.setText("แบบทดสอบ");
                    editProfileRelativeLayout.setVisibility(View.GONE);
                    addProfileRelativeLayout.setVisibility(View.GONE);
                    historyMissionRelativeLayout.setVisibility(View.GONE);
                } else {
                    titleTextView.setText("ของรางวัลสะสม");
                    editProfileRelativeLayout.setVisibility(View.GONE);
                    addProfileRelativeLayout.setVisibility(View.GONE);
                    historyMissionRelativeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(2);

        if (page.equalsIgnoreCase("profile"))
            viewPager.setCurrentItem(0);
        if (page.equalsIgnoreCase("list"))
            viewPager.setCurrentItem(1);
        if (page.equalsIgnoreCase("historyEvaluation"))
            viewPager.setCurrentItem(3);
        if (page.equalsIgnoreCase("collection"))
            viewPager.setCurrentItem(4);

        boolean isTestEvaluation = getIntent().getBooleanExtra("isTestEvaluation", false);
        if (isTestEvaluation) {
            Intent intent = new Intent(ReHomePatientActivity.this, ConditionActivity.class);
            DialogUtil.getInstance().showDialogStartIntent(ReHomePatientActivity.this, getString(R.string.evaluation_dialog), intent);
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (imm != null) {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void openProfileDetail(Caretaker caretaker) {
        Bundle bundle = new Bundle();
        bundle.putString("caretaker", DataFormat.getInstance().getGsonParser().toJson(caretaker));
        ProfileCaretakerDetailFragment fragment = new ProfileCaretakerDetailFragment();
        fragment.setArguments(bundle);
        openFragment(fragment);
    }

    public void openRewardDetail(Reward reward) {
        Bundle bundle = new Bundle();
        bundle.putString("reward", DataFormat.getInstance().getGsonParser().toJson(reward));
        ShowRewardDetailFragment fragment = new ShowRewardDetailFragment();
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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
