package com.custu.project.walktogether.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;

import static android.app.Activity.RESULT_OK;

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

    public void showDialogStartIntent(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        if (dialog.isShowing())
            dialog.cancel();
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

    public void showDialogExitEvaluation(final Context context) {
        final Dialog dialog = new Dialog(context);
        if (dialog.isShowing())
            dialog.cancel();
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.submit);
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();
                System.exit(0);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogExitMission(final Context context) {
        final Dialog dialog = new Dialog(context);
        if (dialog.isShowing())
            dialog.cancel();
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.submit);
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        TextView title = dialog.findViewById(R.id.title);
        TextView detail = dialog.findViewById(R.id.detail);
        title.setText(context.getResources().getString(R.string.exit_mission_title));
        detail.setText(context.getResources().getString(R.string.exit_mission));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("index", ((Activity) context).getIntent().getIntExtra("index", 0));
                returnIntent.putExtra("isComplete", false);
                ((Activity) context).setResult(RESULT_OK, returnIntent);
                ((Activity) context).onBackPressed();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogStartIntent(final Context context, String message, final Intent intent) {
        final Dialog dialog = new Dialog(context);
        if (dialog.isShowing())
            dialog.cancel();

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
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
