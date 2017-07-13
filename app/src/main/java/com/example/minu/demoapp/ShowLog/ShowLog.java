package com.example.minu.demoapp.ShowLog;

import android.util.Log;

/**
 * Created by minu on 7/10/2017.
 */

public class ShowLog {
    public static boolean show=true;
    public static void log(String title,String msg){
        if(show){
            Log.e(title,msg);
        }
    }
}
