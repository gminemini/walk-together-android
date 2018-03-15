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

public class DialogQrCode {

    private static DialogQrCode instance;

    public static DialogQrCode getInstance() {
        if (instance == null) {
            instance = new DialogQrCode();
        }
        return instance;
    }

    public void showDialog(Context context, String path) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_qr_code);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView qrCodeImageView = dialog.findViewById(R.id.qrcode);
        ImageView close = dialog.findViewById(R.id.dismiss);

        PicassoUtil.getInstance().setImage(context, path, qrCodeImageView);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
