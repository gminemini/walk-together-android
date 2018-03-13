package com.custu.project.walktogether.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.custu.project.walktogether.AddPatientFragment;
import com.custu.project.walktogether.AddPatientQRCodeFragment;
import com.custu.project.walktogether.ListNameFragment;
import com.custu.project.walktogether.ProfileFragment;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class HomePatientPagerAdapter extends FragmentPagerAdapter {

    public HomePatientPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileFragment();
            case 1:
                return new ListNameFragment();
            case 2:
                return new ListNameFragment();
            case 3:
                return new ListNameFragment();
            case 4:
                return new ListNameFragment();
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return 2;
    }
}
