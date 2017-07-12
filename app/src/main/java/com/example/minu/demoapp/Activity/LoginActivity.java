package com.example.minu.demoapp.Activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.minu.demoapp.Constant;
import com.example.minu.demoapp.Fragment.RegisterFragment;
import com.example.minu.demoapp.Fragment.SignInFragment;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;
import com.example.minu.demoapp.UploadPic.PicassoLoad;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage.getReference();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, new SignInFragment(), "signIn").addToBackStack(null).commit();

    }

    public void callFragment() {

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.fragment_frame, (Fragment) new RegisterFragment(), "register").addToBackStack(null).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("camera", "mainActivity");
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String outputUri = null;
        if (resultCode == Activity.RESULT_OK) {
            ShowLog.log("camera", "Result Ok" + Constant.camUri);
            if (requestCode == Constant.gallary) {
                outputUri = String.valueOf(data.getData());
                ((RegisterFragment) getSupportFragmentManager().findFragmentByTag("register")).uploadPic(data.getData());
            } else if (requestCode == Constant.cam) {
                outputUri = sp.getString("uri", "no data");

                ((RegisterFragment) getSupportFragmentManager().findFragmentByTag("register")).uploadPic(Uri.parse(outputUri));

            }

        }

        SharedPreferences.Editor et = sp.edit();
        et.putString("uri", String.valueOf(outputUri));
        et.commit();
//        firebasseStorage(Uri.parse(outputUri));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }

    }

    private void firebasseStorage(Uri parse) {
        //get Firebase Instance


        // Points to "images/space.jpg"
        // we can use variables to create child values
        String fileName = "userProfilePic.jpg";
        storageRef = storageRef.child(fileName);

    }
}
