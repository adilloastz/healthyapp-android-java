package com.zeedoys.hidupsehat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Profile_Activity extends AppCompatActivity {

    LinearLayout alamat, profiledetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        alamat = findViewById(R.id.for_seller);
        alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, AlamatActivity.class);
                startActivity(intent);
            }
        });

        profiledetail = findViewById(R.id.edit_profil_button);
        profiledetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, ProfileDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}