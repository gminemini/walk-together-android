package com.custu.project.walktogether.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by pannawatnokket on 7/3/2018 AD.
 */

public class HintAdapter extends ArrayAdapter<String> {

    public HintAdapter(Context theContext, List<String> objects, int theLayoutResId) {
        super(theContext, theLayoutResId, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
