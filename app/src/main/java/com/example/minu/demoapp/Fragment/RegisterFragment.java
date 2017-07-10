package com.example.minu.demoapp.Fragment;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.minu.demoapp.BaseClass;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;

/**
 * Created by minu on 7/9/2017.
 */

public class RegisterFragment extends BaseClass {


    private EditText rEmailField, rPasswordField, rNameField;
    ImageView rUserImage;
    Button rbtnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ShowLog.log("BaseClass", "MainFragment");
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        rEmailField = view.findViewById(R.id.ruserEmail);
        rPasswordField = view.findViewById(R.id.ruserPassword);
        rbtnRegister = view.findViewById(R.id.register1);
        rNameField = view.findViewById(R.id.ruserName);
        rbtnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register1) {
            createAccount(rEmailField.getText().toString(), rPasswordField.getText().toString());
        }
    }

}
