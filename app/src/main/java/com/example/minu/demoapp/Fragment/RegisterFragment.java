package com.example.minu.demoapp.Fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.minu.demoapp.AlertUtils.SelectImageDialog;
import com.example.minu.demoapp.BaseFragment.BaseFragment;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog.ShowLog;
import com.example.minu.demoapp.UploadPic.PicassoLoad;

/**
 * Created by minu on 7/9/2017.
 */

public class RegisterFragment extends BaseFragment {



    static ImageView rUserImage;

    Button rbtnRegister;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ShowLog.log("BaseFragment", "MainFragment");
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        rEmailField = view.findViewById(R.id.ruserEmail);
        rPasswordField = view.findViewById(R.id.ruserPassword);
        rbtnRegister = view.findViewById(R.id.register1);
        rNameField = view.findViewById(R.id.ruserName);
        rUserImage = view.findViewById(R.id.uploadImage);
        rUserImage.setOnClickListener(this);
        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        rbtnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        storeData();
        if (view.getId() == R.id.register1) {
            createAccount("minusarraf96@gmail.com","bex432505");
        } else if (view.getId() == R.id.uploadImage) {
            SelectImageDialog.selectImage(getActivity());
        }
    }




    @Override
    public void onResume() {
        super.onResume();
        //resume camera
    }

    @Override
    public void onPause() {
        super.onPause();
        //  Camera.release();
    }

    private void storeData() {

        SharedPreferences.Editor et = sp.edit();
        et.putString("name", rNameField.getText().toString());
        et.commit();
    }



    public void uploadPic(Uri uri) {
        ShowLog.log("camera", "upload");
        PicassoLoad.picassoload(uri, rUserImage, getActivity());
    }


}
