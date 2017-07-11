package com.example.minu.demoapp.UploadPic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.minu.demoapp.Constant;

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File out = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // out = new File(MyApplication.getInstance().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + timeStamp + ".jpg");
        } else {
            // out=new File(c.getFilesDir().getAbsolutePath(),timeStamp+"jpg");
            Toast.makeText(c, "You dont have Sd-card", Toast.LENGTH_LONG).show();
        }
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, timeStamp + ".jpg");
        try {
            // Make sure the Pictures directory exists.
            path.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(file.getAbsoluteFile());
        //store in sharedPreference
        Constant.camUri = outputUri;
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
        return outputUri;
    }

}
