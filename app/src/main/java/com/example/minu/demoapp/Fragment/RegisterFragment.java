package com.example.minu.demoapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.minu.demoapp.AlertUtils.SelectImageDialog;
import com.example.minu.demoapp.BaseClass;
import com.example.minu.demoapp.Constant;
import com.example.minu.demoapp.Model.Id;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;
import com.example.minu.demoapp.UploadPic.Camera;
import com.example.minu.demoapp.UploadPic.Gallery;
import com.example.minu.demoapp.UploadPic.PicassoLoad;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 * Created by minu on 7/9/2017.
 */

public class RegisterFragment extends BaseClass {


    private EditText rEmailField, rPasswordField, rNameField;
    ImageView rUserImage;
    Button rbtnRegister;
    private DatabaseReference mFirebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ShowLog.log("BaseClass", "MainFragment");
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        rEmailField = view.findViewById(R.id.ruserEmail);
        rPasswordField = view.findViewById(R.id.ruserPassword);
        rbtnRegister = view.findViewById(R.id.register1);
        rNameField = view.findViewById(R.id.ruserName);
        rUserImage = view.findViewById(R.id.uploadImage);
        rUserImage.setOnClickListener(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users");
        rbtnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register1) {
            int status = createAccount(rEmailField.getText().toString(), rPasswordField.getText().toString());
            if (status == 1) {
                createUser();
            }
        } else if (view.getId() == R.id.uploadImage) {
            SelectImageDialog.selectImage(getActivity());
        }
    }

    //store user data to Firebase Database
    public void createUser() {
        String key = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(Id.userId).child(key).child("name").setValue(rNameField.getText());
        mFirebaseDatabase.child(Id.userId).child(key).child("email").setValue(rEmailField.getText());
    }

    //listen
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.e("gallery", "galery");

            Bundle args = new Bundle();
            if (requestCode == Constant.gallary) {
                PicassoLoad.picassoload(data.getData(), rUserImage, getActivity());

            } else if (requestCode == Constant.cam) {
                PicassoLoad.picassoload(Constant.camUri, rUserImage, getActivity());
            }

        }
    }

    Uri outputuri;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case (Constant.cam):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        outputuri = Camera.cameraIntent(getActivity());
                        Constant.camUri=outputuri;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case (Constant.gallary):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Gallery.galleryIntent(getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}
