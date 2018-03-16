package com.custu.project.walktogether.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.custu.project.walktogether.ListPatientFragment;
import com.custu.project.walktogether.ProfileCaretakerFragment;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class HomeCaretakerPagerAdapter extends FragmentPagerAdapter {

    public HomeCaretakerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileCaretakerFragment();
            case 1:
                return new ListPatientFragment();
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return 2;
    }
}
