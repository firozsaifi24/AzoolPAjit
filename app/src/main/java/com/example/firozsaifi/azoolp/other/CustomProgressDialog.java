package com.example.firozsaifi.azoolp.other;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.example.firozsaifi.azoolp.R;

public class CustomProgressDialog {

    public static ProgressDialog createProgressDialog(Context mContext, boolean cancelable) {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.d("progress_error", e.getMessage());
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
        return progressDialog;
    }

}
