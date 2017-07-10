package com.example.minu.demoapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.minu.demoapp.Fragment.RegisterFragment;
import com.example.minu.demoapp.Fragment.SignInFragment;
import com.example.minu.demoapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callFragment();
    }
    private void callFragment() {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_frame, new SignInFragment(), "signIn").addToBackStack(null).commit();
    }
}
