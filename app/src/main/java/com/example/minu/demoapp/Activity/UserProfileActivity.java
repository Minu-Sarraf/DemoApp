package com.example.minu.demoapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minu.demoapp.Constants.Constant;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog.ShowLog;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txName;
    Button btn;
    ImageView imPic;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        txName = (TextView) findViewById(R.id.userName);
        btn = (Button) findViewById(R.id.logout);
        btn.setOnClickListener(this);
        imPic = (ImageView) findViewById(R.id.userImg);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        AuthListener();
        uploadImg();

    }

    public void AuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                ShowLog.log("updatePROFILE", "SIGNOUT");
                if (user != null) {
                    ShowLog.log("SignIn", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    private void uploadImg() {
        //saved data during registration
        SharedPreferences sp = getSharedPreferences(Constant.userInfo, MODE_PRIVATE);
        txName.setText(sp.getString("name", "Unknown User"));
        Picasso.with(this).load(Uri.parse(sp.getString("uri", ""))).resize(150, 150).placeholder(R.drawable.profile).into(imPic);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

    public void logout() {
        Log.e("logout", "logout");
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
            logout();
        }
    }
}
