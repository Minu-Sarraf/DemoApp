package com.example.minu.demoapp.AlertUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.minu.demoapp.Constant;
import com.example.minu.demoapp.Permission;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.UploadPic.Camera;
import com.example.minu.demoapp.UploadPic.Gallery;

import java.io.IOException;

/**
 * Created by minu on 7/11/2017.
 */

public class SelectImageDialog {
    public static void selectImage(final Context context) {
        final CharSequence[] items = {"Take Photo", "Choose from Gallary",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {boolean result = Permission.Utility.checkPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE, Constant.gallary, "READ_EXTERNAL_STORAGE permission is necessary");
                ;
                if (items[item].equals("Take Photo")) {

                    if (result)
                        try {

                            Camera.cameraIntent(context);
                            dialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                } else if (items[item].equals("Choose from Gallary")) {
                    if (result)
                        Gallery.galleryIntent(context);
                    dialog.dismiss();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
