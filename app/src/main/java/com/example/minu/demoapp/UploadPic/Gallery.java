package com.example.minu.demoapp.UploadPic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.minu.demoapp.Constant;
import com.example.minu.demoapp.Permission;


/**
 * Created by User on 7/15/2016.
 */
public class Gallery {
    public static void galleryIntent(Context c) {
        boolean result2 = Permission.Utility.checkPermission(c, android.Manifest.permission.READ_EXTERNAL_STORAGE, Constant.gallary, "READ_EXTERNAL_STORAGE permission is necessary");
        if (result2) {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            getIntent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*|application/pdf");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
            ((Activity) c).startActivityForResult(chooserIntent, Constant.gallary);
        }
    }
}
