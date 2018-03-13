package com.custu.project.walktogether;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.HomeCaretakerPagerAdapter;
import com.custu.project.walktogether.adapter.HomePatientPagerAdapter;
import com.custu.project.walktogether.util.BasicActivity;

public class ReHomepatientActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private TextView titleTextView;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_homepatient);
        setUI();
        setListener();
    }

    @Override
    public void onClick(View v) {

    }
    private void setListener() {

    }
    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        titleTextView = findViewById(R.id.title);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#8E8E93"), Color.parseColor("#389A1E"));
        HomePatientPagerAdapter adapter = new HomePatientPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);


        TabLayout.Tab tab = tabLayout.getTabAt(0);
        int tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorBackground);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

     /*   tab = tabLayout.getTabAt(1);
        tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(2);
        tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(3);
        tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tab = tabLayout.getTabAt(4);
        tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorMiddleGray);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
*/

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorBackground);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(ReHomepatientActivity.this, R.color.colorMiddleGray);
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
//                    editProfileRelativeLayout.setVisibility(View.VISIBLE);
//                    addProfileRelativeLayout.setVisibility(View.GONE);
                } else if (position == 1){
                    titleTextView.setText("รายชื่อ");
//                    editProfileRelativeLayout.setVisibility(View.GONE);
//                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                }
                else if (position == 2){
                    titleTextView.setText("ภารกิจ");
//                    editProfileRelativeLayout.setVisibility(View.GONE);
//                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                }
                else if (position == 3){
                    titleTextView.setText("ประวัติ");
//                    editProfileRelativeLayout.setVisibility(View.GONE);
//                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                }
                else {
                    titleTextView.setText("ประวัติ");
//                    editProfileRelativeLayout.setVisibility(View.GONE);
//                    addProfileRelativeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void initProgressDialog() {

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
