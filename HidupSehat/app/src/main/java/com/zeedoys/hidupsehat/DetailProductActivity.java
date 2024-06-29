package com.zeedoys.hidupsehat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailNama, detailharga, detailDesc;
    private ImageButton back, keranjangBtn, addFav;
    private Button addBag;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Initialize views
        detailImage = findViewById(R.id.detailImage);
        detailNama = findViewById(R.id.detailNama);
        detailharga = findViewById(R.id.detailharga);
        detailDesc = findViewById(R.id.detailDesc);
        back = findViewById(R.id.back);
        keranjangBtn = findViewById(R.id.keranjangbtn);
        addFav = findViewById(R.id.add_fav);
        addBag = findViewById(R.id.add_bag);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Get data from Intent
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");

        // Set data to views
        Glide.with(this).load(image).into(detailImage);
        detailNama.setText(name);
        detailDesc.setText(description);
        detailharga.setText(price);

        addBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // Add item to cart in Firebase
                    DatabaseReference cartRef = databaseReference.child("Keranjang").child(currentUser.getUid()).push();
                    cartRef.child("dataimage").setValue(image);
                    cartRef.child("datanama").setValue(name);
                    cartRef.child("datadeskripsi").setValue(description);
                    cartRef.child("dataharga").setValue(price);

                    // Redirect to Cart Activity
                    startActivity(new Intent(DetailProductActivity.this, CartActivity.class));
                }
            }
        });

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // Add item to wishlist in Firebase
                    DatabaseReference wishlistRef = databaseReference.child("Wishlist").child(currentUser.getUid()).push();
                    wishlistRef.child("dataimage").setValue(image);
                    wishlistRef.child("datanama").setValue(name);
                    wishlistRef.child("datadeskripsi").setValue(description);
                    wishlistRef.child("dataharga").setValue(price);

                    // Redirect to Wishlist Activity
                    startActivity(new Intent(DetailProductActivity.this, WishlistActivity.class));
                }
            }
        });

        // Set onClick listeners if necessary
        back.setOnClickListener(v -> finish());
        // Add other listeners as needed
    }
}
