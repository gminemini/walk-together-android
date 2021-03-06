package com.custu.project.walktogether.controller.caretaker;

import android.annotation.SuppressLint;
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
import com.custu.project.walktogether.adapter.HomeCaretakerPagerAdapter;
import com.custu.project.walktogether.controller.LoginActivity;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.UserManager;

import me.leolin.shortcutbadger.ShortcutBadger;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReHomeCaretakerActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private TabLayout tabLayout;
    private RelativeLayout editProfileRelativeLayout;
    private RelativeLayout addProfileRelativeLayout;
    private TextView titleTextView;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_re_homecaretaker);
        initValue();
        setUI();
        setListener();
    }

    @Override
    public void initValue() {
        page = getIntent().getStringExtra("page") == null ? "" : getIntent().getStringExtra("page");
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUI() {
        ShortcutBadger.removeCount(this);
        titleTextView = findViewById(R.id.title);
        addProfileRelativeLayout = findViewById(R.id.add);
        editProfileRelativeLayout = findViewById(R.id.edit_profile);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));

        HomeCaretakerPagerAdapter adapter = new HomeCaretakerPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(ReHomeCaretakerActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

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
                } else {
                    titleTextView.setText("รายชื่อผู้สูงอายุ");
                    editProfileRelativeLayout.setVisibility(View.GONE);
                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (page.equalsIgnoreCase("list"))
            viewPager.setCurrentItem(1);
    }


    private void setListener() {
        addProfileRelativeLayout.setOnClickListener(this);
        editProfileRelativeLayout.setOnClickListener(this);
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
        Intent intent;
        switch (id) {
            case R.id.add:
                intent = new Intent(ReHomeCaretakerActivity.this, AddTabCaretakerActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_profile:
                intent = new Intent(ReHomeCaretakerActivity.this, EditCaretakerProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
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
