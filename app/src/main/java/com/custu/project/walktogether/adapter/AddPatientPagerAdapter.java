package com.custu.project.walktogether.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.custu.project.walktogether.AddPatientFragment;
import com.custu.project.walktogether.AddPatientQRCodeFragment;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class AddPatientPagerAdapter extends FragmentPagerAdapter {

    public AddPatientPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddPatientFragment();
            case 1:
                return new AddPatientQRCodeFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
