package com.example.minu.demoapp.BaseFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minu.demoapp.Activity.FeedActivity;
import com.example.minu.demoapp.Model.Id;
import com.example.minu.demoapp.ShowLog.ShowLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by minu on 7/10/2017.
 */


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "Leapfrog.SiginIn";
    public FirebaseAuth mAuth;
    public SharedPreferences sp;
    public DatabaseReference mFirebaseDatabase;
    public EditText rEmailField, rPasswordField, rNameField;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private UploadTask uploadTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShowLog.log("BaseFragment", "Baseclass");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        AuthListener();

    }

      /*  @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
           // View view = inflater.inflate(R.layout.register_fragment, container, false);
            return inflater.inflate(getFragmentManager(), container, false);
            //return view;
        }*/

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //store user data to Firebase Database
    public void createUser() {

        String key = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child(key).child("name").setValue(rNameField.getText().toString());
        mFirebaseDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child(key).child("email").setValue(rEmailField.getText().toString());
    }
    public void AuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                   /* Intent feeds = new Intent(getActivity(), FeedActivity.class);
                    startActivity(feeds);
                    getActivity().finish();*/
                    ShowLog.log(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    int status;

    public int createAccount(String email, String password) {
            /*if (!validateForm()) {
                return;
            }*/

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {


                            firebasseStorage();
                            updateUI(mAuth.getCurrentUser());
                            createUser();
                        }

                        // ...
                    }
                });
        return status;
    }

    FirebaseStorage storage;

    public void firebasseStorage() {
        StorageReference storageRef;

        Uri uri = Uri.parse(sp.getString("uri", ""));
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("images/" + uri.getLastPathSegment());
        uploadTask = riversRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("upload", exception.getCause() + "");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    public void signIn(String email, String password) {
           /* if (!validateForm()) {
                return;
            }*/

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        } else {
                            Log.w(TAG, "signInWithEmail Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            String id = user.getUid();
            Log.d("ididid", id);
            Intent feedAactivity = new Intent(getActivity(), FeedActivity.class);
            startActivity(feedAactivity);
            Id.userId = id;
        } else {
        }
    }

    public void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // The user's ID, unique to the Firebase project. Do NOT use this value to

            String uid = user.getUid();
            Id.userId = uid;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}



