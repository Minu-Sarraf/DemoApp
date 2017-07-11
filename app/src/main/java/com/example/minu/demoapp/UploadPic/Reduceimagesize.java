package com.example.minu.demoapp.UploadPic;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 6/16/2016.
 */
public class Reduceimagesize {
    static Bitmap bitmapPhoto;
    static Uri uri1;


    public static Bitmap get_Picture_bitmap(String imagePath, final Context c) throws IOException {
        final Bitmap bitmap = null;


        //  bitoption.inJustDecodeBounds = true;
        if (imagePath == null) {
            Log.e("reducesize", "reduce");
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                         bitmapPhoto= MediaStore.Images.Media.getBitmap(c.getContentResolver(),uri1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ( (Activity)c). runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] imageInByte = stream.toByteArray();
                            long lengthbmp = imageInByte.length;
                            Log.e("length", String.valueOf(lengthbmp / 1000));
                            BitmapFactory.Options bitoption = new BitmapFactory.Options();
                            bitoption.inSampleSize = amplesize(lengthbmp);

                            try {
                                ParcelFileDescriptor parcelFileDescriptor1 =
                                        null;
                                try {
                                    parcelFileDescriptor1 = c.getContentResolver().openFileDescriptor(uri1, "r");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                FileDescriptor fileDescriptor1 = parcelFileDescriptor1.getFileDescriptor();
                                bitmapPhoto = BitmapFactory.decodeFileDescriptor(fileDescriptor1, null, bitoption);
                                parcelFileDescriptor1.close();
                            } catch (Error z) {
                                z.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            th.start();
            return bitmapPhoto;
            //content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3D17492abc/document/acc=1;doc=17492
        } else {
            Log.e("reducesize", "gallery");
            long size_file = getFileSize(new File(imagePath));
            BitmapFactory.Options bitoption = new BitmapFactory.Options();
            bitoption.inSampleSize = amplesize(size_file);
            try {
                bitmapPhoto = BitmapFactory.decodeFile(imagePath, bitoption);
            } catch (Error z) {
                z.printStackTrace();
            }
            return bitmapPhoto;
        }
    }
    public static Bitmap decodeSampledBitmapFromByte(byte[] res,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(res, 0, res.length,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(res, 0, res.length,options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }
    public static int amplesize(long size_file) {
        size_file = (size_file) / 1000;// in Kb now
        int ample_size = 1;

        if (size_file <= 500) {

            System.out.println("SSSSS1111= " + size_file);
            ample_size = 2;

        } else if (size_file > 512 && size_file < 1000) {

            System.out.println("SSSSS2222= " + size_file);
            ample_size = 4;
        } else if (size_file >= 1000 && size_file < 1500) {

            System.out.println("SSSSS3333= " + size_file);
            ample_size = 8;


        } else if (size_file >= 1500 && size_file < 3000) {
        } else if (size_file >= 1500 && size_file < 3000) {

            System.out.println("SSSSS3333= " + size_file);
            ample_size = 16;

        } else if (size_file >= 3000 && size_file <= 4500) {

            System.out.println("SSSSS4444= " + size_file);
            ample_size = 12;

        } else if (size_file >= 4500) {

            System.out.println("SSSSS4444= " + size_file);
            ample_size = 16;
        }
        return ample_size;
    }

    public static String getgallerypath(Intent data, Context context) {
        uri1 = data.getData();
        String wholeID = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.e("uri1", uri1.toString());
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri1)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri1)) {
                    final String docId = DocumentsContract.getDocumentId(uri1);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri1)) {

                    String id = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        id = DocumentsContract.getDocumentId(uri1);
                    }
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri1)) {
                    final String docId = DocumentsContract.getDocumentId(uri1);
                    Log.e("docid", docId);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }

        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri1.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri1) || isDrivePhotosUri(uri1)) {
                Log.e("google", "gogle");
                return uri1.getLastPathSegment();
            }
            return getDataColumn(context, uri1, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri1.getScheme())) {
            return uri1.getPath();
        }
    }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        //  content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3D17492abc/document/acc=1;doc=17492
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isDrivePhotosUri(Uri uri) {
        //  content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3D17492abc/document/acc=1;doc=17492
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public static long getFileSize(final File file) {
        Log.e("getfilesize", String.valueOf((file.length()) / 1024));
        if (file == null || !file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();
        final List<File> dirs = new LinkedList<File>();
        dirs.add(file);
        long result = 0;
        while (!dirs.isEmpty()) {
            final File dir = dirs.remove(0);
            if (!dir.exists())
                continue;
            final File[] listFiles = dir.listFiles();
            if (listFiles == null || listFiles.length == 0)
                continue;
            for (final File child : listFiles) {
                result += child.length();
                if (child.isDirectory())
                    dirs.add(child);
            }
        }
        Log.e("result", String.valueOf(result));
        return result;
    }

    private static Uri getUri() {
        String state = Environment.getExternalStorageState();
        if (!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
}
/*
public void orientation(){
    */
/*

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
            } catch (IOException e) {
                // Auto-generated catch block
                e.printStackTrace();
            }
            int orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();

            if ((orientation == 3)) {
                matrix.postRotate(180);
                bitmap = Bitmap.createBitmap(bitmapPhoto, 0, 0,
                        bitmapPhoto.getWidth(), bitmapPhoto.getHeight(), matrix,
                        true);

            } else if (orientation == 6) {
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmapPhoto, 0, 0,
                        bitmapPhoto.getWidth(), bitmapPhoto.getHeight(), matrix,
                        true);

            } else if (orientation == 8) {
                matrix.postRotate(270);
                bitmap = Bitmap.createBitmap(bitmapPhoto, 0, 0,
                        bitmapPhoto.getWidth(), bitmapPhoto.getHeight(), matrix,
                        true);

            } else {
                matrix.postRotate(0);
                bitmap = Bitmap.createBitmap(bitmapPhoto, 0, 0,
                        bitmapPhoto.getWidth(), bitmapPhoto.getHeight(), matrix,
                        true);

            }
*//*

}*/
