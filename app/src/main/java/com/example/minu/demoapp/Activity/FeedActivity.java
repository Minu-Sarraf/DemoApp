package com.example.minu.demoapp.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.minu.demoapp.Model.FeedDataModel;
import com.example.minu.demoapp.Model.Id;
import com.example.minu.demoapp.R;
import com.example.minu.demoapp.ShowLog;
import com.example.minu.demoapp.adapter.Recyclerview_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    private Recyclerview_adapter rec_adapter;
    List<FeedDataModel> userFeed = new ArrayList<FeedDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //getReference of database
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("feeds");


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


    }


    protected void onStart() {
        super.onStart();

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("feeds");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("feeds").child(Id.userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShowLog.log("mData", dataSnapshot.getKey());
                ShowLog.log("mData", String.valueOf(dataSnapshot.hasChildren()));
                ShowLog.log("mData", String.valueOf(dataSnapshot.getChildrenCount()));

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    ShowLog.log("mData Key", userSnapshot.getKey());
                    FeedDataModel feedData = userSnapshot.getValue(FeedDataModel.class);
                    userFeed.add(feedData);

                    ShowLog.log("mData idvalue", feedData.toString());
                    ShowLog.log("mData User", feedData.getFeeds().get(0).getStatus());
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

                rec_adapter = new Recyclerview_adapter(userFeed);
                recyclerView.setAdapter(rec_adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
