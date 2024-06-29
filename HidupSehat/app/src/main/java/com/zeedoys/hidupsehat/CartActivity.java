package com.zeedoys.hidupsehat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private CartAdapter cartAdapter;
    private ArrayList<DataClass> cartList;
    private TextView totalBelanja;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartListView = findViewById(R.id.cartListView);
        totalBelanja = findViewById(R.id.totalBelanja);
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartList);
        cartListView.setAdapter(cartAdapter);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Fetch cart items from Firebase
        if (currentUser != null) {
            databaseReference.child("Keranjang").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartList.clear();
                    int total = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                        if (dataClass != null) {
                            cartList.add(dataClass);
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
                    }
                    totalBelanja.setText("Rp " + total);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                }
            });

            // Handle checkout button click
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cartList.isEmpty()) {
                        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                        intent.putExtra("cartItems", cartList);  // Ensure cartItems is Serializable
                        startActivity(intent);
                    } else {
                        Toast.makeText(CartActivity.this, "Keranjang Anda kosong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
