package com.example.minu.demoapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.minu.demoapp.Activity.LoginActivity;
import com.example.minu.demoapp.BaseFragment.BaseFragment;
import com.example.minu.demoapp.R;
import com.google.firebase.auth.FirebaseAuth;


public class SignInFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "Leapfrog.SiginIn";
    private EditText mEmailField, mPasswordField;
    Button btnLogin, btnRegister;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_layout, container, false);
        mEmailField = view.findViewById(R.id.userEmail);
        mPasswordField = view.findViewById(R.id.userPassword);
        btnRegister = view.findViewById(R.id.register);
        btnLogin = view.findViewById(R.id.login);
        btnLogin.setOnClickListener(this);
        btnRegister.setText(Html.fromHtml("<u>Register</u>"));
        btnRegister.setOnClickListener(this);
        return view;
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            if (!validateForm()) {
                return;
            } else {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        } else if (view.getId() == R.id.register) {
            ((LoginActivity) getActivity()).callFragment();

        }
    }


}
