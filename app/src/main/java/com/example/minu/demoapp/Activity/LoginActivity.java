package com.example.minu.demoapp.Activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.minu.demoapp.Constants.Constant;
import com.example.minu.demoapp.Fragment.RegisterFragment;
import com.example.minu.demoapp.Fragment.SignInFragment;
import com.example.minu.demoapp.IdlingResource.SimpleIdleResource;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.UploadPic.Camera;
import com.example.minu.demoapp.UploadPic.Gallery;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseStorage storage;
    @Nullable
    public SimpleIdleResource mIdlingResource;

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

        SharedPreferences sp = getSharedPreferences(Constant.userInfo, MODE_PRIVATE);
        String outputUri = null;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.gallary) {
                outputUri = String.valueOf(data.getData());
                ((RegisterFragment) getSupportFragmentManager().findFragmentByTag("register")).uploadPic(data.getData());
            } else if (requestCode == Constant.cam) {
                outputUri = sp.getString("uri", "no data");
                ((RegisterFragment) getSupportFragmentManager().findFragmentByTag("register")).uploadPic(Uri.parse(outputUri));

            }

        }

        SharedPreferences.Editor et = sp.edit();
        et.putString("uri", (outputUri));
        et.commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case (Constant.cam):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Uri outputuri = Camera.cameraIntent(this);
                        Constant.camUri = outputuri;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case (Constant.gallary):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Gallery.galleryIntent(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
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

    public void updateIdleState(boolean state) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(state);
        }

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdleResource();
        }
        return mIdlingResource;
    }
}

