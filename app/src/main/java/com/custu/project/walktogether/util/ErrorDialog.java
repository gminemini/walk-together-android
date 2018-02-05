package com.custu.project.walktogether.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;

/**
 * Created by pannawatnokket on 5/2/2018 AD.
 */

public class ErrorDialog {

    private static ErrorDialog instance;

    public static ErrorDialog getInstance() {
        if (instance == null)
            instance = new ErrorDialog();
        return instance;
    }

    public void showDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.error_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);


        titleTextView.setText(message);


        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
