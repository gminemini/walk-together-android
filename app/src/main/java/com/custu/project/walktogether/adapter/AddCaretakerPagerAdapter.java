package com.custu.project.walktogether.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.custu.project.walktogether.AddCaretakerFragment;
import com.custu.project.walktogether.AddCaretakerQRCodeFragment;
import com.custu.project.walktogether.AddPatientFragment;
import com.custu.project.walktogether.AddPatientQRCodeFragment;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class AddCaretakerPagerAdapter extends FragmentPagerAdapter {

    public AddCaretakerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddCaretakerFragment();
            case 1:
                return new AddCaretakerQRCodeFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
