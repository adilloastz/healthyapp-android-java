package com.zeedoys.hidupsehat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupName, signupUsername;
    private Button SignupButton;
    private TextView loginRedirectText;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        SignupButton = findViewById(R.id.signup_button);
        signupName = findViewById(R.id.signup_name);
        signupUsername = findViewById(R.id.signup_username);
        loginRedirectText = findViewById(R.id.loginredirecttext);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        if (user != null) {
                                            String uid = user.getUid();

                                            database = FirebaseDatabase.getInstance();
                                            reference = database.getReference("users");

                                            // Menyimpan data pengguna dengan UID sebagai kunci
                                            HelperClass helperClass = new HelperClass(name, email, username, password);
                                            reference.child(uid).setValue(helperClass);

                                            Toast.makeText(SignUpActivity.this, "Signup berhasil!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUpActivity.this, loginnn.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Signup gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Harap isi semua bidang", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, loginnn.class);
                startActivity(intent);
            }
        });
    }
}
