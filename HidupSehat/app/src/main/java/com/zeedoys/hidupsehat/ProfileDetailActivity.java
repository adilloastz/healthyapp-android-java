package com.zeedoys.hidupsehat;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileDetailActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView profileName , profileEmail, profileUsername, profilePassword;
    Button editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileemail);
        profileUsername = findViewById(R.id.profileusername);
        profilePassword = findViewById(R.id.profilepassword);
        editprofile = findViewById(R.id.editbutton);

        showUserData();

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserdata();
            }
        });

    }

    public  void showUserData(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            profileEmail.setText(email);

            mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String password = snapshot.child("password").getValue(String.class);

                        profileUsername.setText(username);
                        profileName.setText(name);
                        profilePassword.setText(password);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            profileEmail.setText("No user is signed in");
        }
    }

    public void passUserdata(){
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    Intent intent = new Intent(ProfileDetailActivity.this, EditProfileActivity.class);

                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}