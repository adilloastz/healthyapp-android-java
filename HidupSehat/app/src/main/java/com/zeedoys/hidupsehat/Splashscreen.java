package com.zeedoys.hidupsehat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Splashscreen extends AppCompatActivity {

    private Button btnactivitymasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        btnactivitymasuk = findViewById(R.id.masuk);
        btnactivitymasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splashscreen.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}