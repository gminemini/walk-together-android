package com.custu.project.walktogether.util.lib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.util.PicassoUtil;

/**
 * Created by pannawatnokket on 13/2/2018 AD.
 */

public class DialogReward {

    private static DialogReward instance;

    public static DialogReward getInstance() {
        if (instance == null) {
            instance = new DialogReward();
        }
        return instance;
    }

    public void showDialog(Context context, String path) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_show_reward);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView qrCodeImageView = dialog.findViewById(R.id.qrcode);
        ImageView close = dialog.findViewById(R.id.dismiss);

        PicassoUtil.getInstance().setImage(context, path, qrCodeImageView);

        close.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

}
