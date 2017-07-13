package com.example.minu.demoapp.UploadPic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.example.minu.demoapp.AlertUtils.ProgressDialog;
import com.example.minu.demoapp.CheckInternet.CheckInternet;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by minu on 7/11/2017.
 */

public class PicassoLoad {

    public static void picassoload(final Uri a, final ImageView ivimage, final Context context) {
      //  ProgressDialog.progress(context);
        Picasso.with(context).load(a).resize(200,200).onlyScaleDown().memoryPolicy(MemoryPolicy.NO_CACHE)
                .centerInside().into(ivimage, new Callback() {
            @Override
            public void onSuccess() {
                ProgressDialog.dismissDialog(context);
            }

            @Override
            public void onError() {
                if (!CheckInternet.displaynetstatus(context, true)) {
                    Bitmap abc = null;
                    ByteArrayOutputStream bytes = null;
                    Bitmap result = null;
                    try {
                        abc = MediaStore.Images.Media.getBitmap(context.getContentResolver(),a);
                        bytes = new ByteArrayOutputStream();
                        abc.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                        byte[] imageInByte = bytes.toByteArray();
                        result = Reduceimagesize.decodeSampledBitmapFromByte(imageInByte,150,150);
                        result.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                         Log.e("error","document during compress");
                    }
                    ivimage.setImageBitmap(result);
                } else {
                    ProgressDialog.dismissDialog(context);
                }



            }
        });
    }

}
