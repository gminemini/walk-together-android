package com.custu.project.walktogether.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;

/**
 * Created by pannawatnokket on 5/2/2018 AD.
 */

public class DialogUtil {

    private static DialogUtil instance;

    public static DialogUtil getInstance() {
        if (instance == null)
            instance = new DialogUtil();
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

    public void showDialog(final Context context, String message, final Intent intent) {
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
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
