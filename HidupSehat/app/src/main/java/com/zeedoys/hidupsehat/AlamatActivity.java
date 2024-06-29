package com.zeedoys.hidupsehat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlamatActivity extends AppCompatActivity {

    private EditText addalamat;
    private Button saveButton, kembaliButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);

        addalamat = findViewById(R.id.addalamat);
        saveButton = findViewById(R.id.savebutton);
        kembaliButton = findViewById(R.id.kembali);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Load saved address
        loadAddress();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        kembaliButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveAddress() {
        String address = addalamat.getText().toString().trim();
        if (!address.isEmpty()) {
            if (currentUser != null) {
                databaseReference.child("Alamat User").child(currentUser.getUid()).child("alamat").setValue(address)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AlamatActivity.this, "Alamat berhasil disimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AlamatActivity.this, "Gagal menyimpan alamat", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAddress() {
        if (currentUser != null) {
            databaseReference.child("users").child(currentUser.getUid()).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String address = snapshot.getValue(String.class);
                        addalamat.setText(address);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AlamatActivity.this, "Gagal memuat alamat", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
