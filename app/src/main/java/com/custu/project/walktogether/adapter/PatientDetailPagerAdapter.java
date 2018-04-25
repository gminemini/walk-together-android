package com.custu.project.walktogether.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.custu.project.walktogether.controller.caretaker.HistoryMissionFragment;
import com.custu.project.walktogether.controller.patient.EvaluationHistoryFragment;
import com.custu.project.walktogether.controller.caretaker.ListPatientFragment;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class PatientDetailPagerAdapter extends FragmentPagerAdapter {
    private Long idPatient;

    public PatientDetailPagerAdapter(FragmentManager fm, Long idPatient) {
        super(fm);
        this.idPatient = idPatient;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle;
        switch (position) {
            case 0:
                bundle = new Bundle();
                bundle.putLong("idPatient", idPatient);
                EvaluationHistoryFragment evaluationHistoryFragment = new EvaluationHistoryFragment();
                evaluationHistoryFragment.setArguments(bundle);
                return evaluationHistoryFragment;
            case 1:
                bundle = new Bundle();
                bundle.putLong("idPatient", idPatient);
                HistoryMissionFragment historyMissionFragment = new HistoryMissionFragment();
                historyMissionFragment.setArguments(bundle);
                return historyMissionFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
