package com.example.minu.demoapp.AlertUtils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by minu on 7/10/2017.
 */

public class ProgressDialog {
    public static void displayMaterialProgressDialog(String title, String msg) {


    }

    public static void progress(Context context,MaterialDialog dialog) {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("Loading.....")
                .content("Please wait")
                .progress(true, 0);
        dialog = builder.build();

        dialog.show();

    }

    public static void dismissDialog(Context context, MaterialDialog dialog) {
        if(dialog!=null) {
            dialog.dismiss();
        }
    }

}
