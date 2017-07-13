package com.example.minu.demoapp.UploadPic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.minu.demoapp.Constants.Constant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 7/15/2016.
 */
public class Camera {

    public static Uri cameraIntent(Context c) throws IOException {
        Log.e("camera", "camera");
        Uri outputUri = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());


        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, timeStamp + ".jpg");
        try {
            path.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(file.getAbsoluteFile());
        //store in sharedPreference
        storeUri(outputUri, c, file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(c.getPackageManager()) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                List<ResolveInfo> resInfoList = c.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    c.grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
            ((Activity) c).startActivityForResult(intent, Constant.cam);
        }
        return outputUri;
    }

    private static void storeUri(Uri outputUri, Context context, File file) {

        SharedPreferences sp = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("file", String.valueOf(file.getAbsoluteFile()));
        et.putString("uri", String.valueOf(outputUri));
        et.commit();
    }


}
