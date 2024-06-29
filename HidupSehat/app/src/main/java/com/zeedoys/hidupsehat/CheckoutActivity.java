package com.zeedoys.hidupsehat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private TextView alamatTujuan;
    private RecyclerView checkoutListView;
    private Button confirmPaymentButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private ArrayList<DataClass> cartItems;
    private TextView totalharga;
    private CarttransAdapter carttransAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        alamatTujuan = findViewById(R.id.alamatTujuan);
        checkoutListView = findViewById(R.id.recyclerViewCheckout);
        confirmPaymentButton = findViewById(R.id.confirmPaymentButton);
        totalharga = findViewById(R.id.totalharga);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        cartItems = (ArrayList<DataClass>) getIntent().getSerializableExtra("cartItems");
        carttransAdapter = new CarttransAdapter(cartItems);
        checkoutListView.setAdapter(carttransAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        checkoutListView.setLayoutManager(layoutManager);
        carttransAdapter = new CarttransAdapter(cartItems);
        checkoutListView.setAdapter(carttransAdapter);

        // hitung total belanja
        if (currentUser != null) {
            databaseReference.child("Keranjang").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int total = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                        // Calculate total
                        String hargaString = dataClass.getDataharga();
                        if (hargaString != null && !hargaString.isEmpty()) {
                            try {
                                int harga = Integer.parseInt(hargaString);
                                total += harga;
                            } catch (NumberFormatException e) {
                                // Log error and continue
                                Log.e("CartActivity", "Invalid harga value: " + hargaString, e);
                            }
                        }
                    }
                    totalharga.setText("Rp " + total);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                }
            });
        }

        // Load user address
        if (currentUser != null) {
            databaseReference.child("Alamat User").child(currentUser.getUid()).child("alamat").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String address = task.getResult().getValue(String.class);
                    alamatTujuan.setText(address);
                } else {
                    Toast.makeText(CheckoutActivity.this, "Gagal memuat alamat", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Handle confirm payment button click
        confirmPaymentButton.setOnClickListener(v -> confirmPayment());
    }

    private void confirmPayment() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference transaksiRef = databaseReference.child("Transaksi").child(userId);

            transaksiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long transaksiCount = snapshot.getChildrenCount();
                    String transaksiName = "transaksi_" + (transaksiCount + 1);

                    DatabaseReference newTransaksiRef = transaksiRef.child(transaksiName);
                    newTransaksiRef.child("nama").setValue(transaksiName);
                    for (DataClass item : cartItems) {
                        newTransaksiRef.child("items").push().setValue(item);
                    }
                    newTransaksiRef.child("total").setValue(totalharga.getText().toString());

                    databaseReference.child("Keranjang").child(userId).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(CheckoutActivity.this, "Transaksi berhasil", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        } else {
                            Toast.makeText(CheckoutActivity.this, "Gagal menyelesaikan transaksi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                    Log.e("CheckoutActivity", "Database error: " + error.getMessage());
                }
            });
        }
    }
}
