package com.custu.project.walktogether;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.AddPatientPagerAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddTabCaretakerActivity extends FragmentActivity {

    private TabLayout tabLayout;
    private TabItem qrTabItem;
    private TabItem numberTabItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tab);
        setUI();
    }

    private void setUI() {
        tabLayout = findViewById(R.id.tabs);
        qrTabItem = findViewById(R.id.tab_qr);
        numberTabItem = findViewById(R.id.tab_number);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));

        AddPatientPagerAdapter adapter = new AddPatientPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                }
                if (position == 1) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View view = AddTabCaretakerActivity.this.getCurrentFocus();
                    if (imm != null) {
                        if (view != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(AddTabCaretakerActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        tab = tabLayout.getTabAt(1);
        tabIconColor = ContextCompat.getColor(AddTabCaretakerActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(AddTabCaretakerActivity.this, R.color.colorBackground);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(AddTabCaretakerActivity.this, R.color.colorMiddleGray);
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
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (imm != null) {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
