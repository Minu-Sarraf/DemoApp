package com.example.minu.demoapp.CheckInternet;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by User on 4/14/2016.
 */
public class CheckInternet {


    public static boolean isWifiEnable() {
        return isWifiEnable;
    }

    public static void setIsWifiEnable(boolean isWifiEnable) {
        CheckInternet.isWifiEnable = isWifiEnable;
    }

    public static boolean isMobileNetworkAvailable() {
        return isMobileNetworkAvailable;
    }

    public static void setIsMobileNetworkAvailable(boolean isMobileNetworkAvailable) {
        CheckInternet.isMobileNetworkAvailable = isMobileNetworkAvailable;
    }

    private static boolean isWifiEnable = false;
    private static boolean isMobileNetworkAvailable = false;

    public static boolean isNetWorkAvailableNow(Context context) {

        boolean isNetworkAvailable = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        setIsWifiEnable(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
        setIsMobileNetworkAvailable(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());

        if (isWifiEnable() || isMobileNetworkAvailable()) {
        /*Sometime wifi is connected but service provider never connected to internet
        so cross check one more time*/
            // if (isOnline())
            isNetworkAvailable = true;
        }

        return isNetworkAvailable;
    }

    public static boolean isOnline() {
    /*Just to check Time delay*/
        long t = Calendar.getInstance().getTimeInMillis();

        Runtime runtime = Runtime.getRuntime();
        try {
        /*Pinging to Google server*/
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            long t2 = Calendar.getInstance().getTimeInMillis();
            Log.i("NetWork check Time", (t2 - t) + "");
        }
        return false;
    }

    static boolean error;

    public static boolean displaynetstatus(final Context c, final boolean a) {
        error = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isNetWorkAvailableNow(c)) {
                    ((Activity) c).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            error = true;
                            if (a) {
                                // AlertDialogClass.displaySnackBar(c, "Cannot connect to the internet! Please Try Again.", R.color.materialred);
                                Toast.makeText(((Activity) c), "Cannot connect to the internet! <br>Please Try Again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else if (isNetWorkAvailableNow(c) && !isOnline()) {
                    ((Activity) c).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            error = true;
                            if (a) {
                                Toast.makeText(((Activity) c), "OOPS! ERROR !Poor Internet Connection", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
        th.start();

        return error;
    }


}


