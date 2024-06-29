package com.zeedoys.hidupsehat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private GridView gridwishView;
    private GAdapter wishlistAdapter;
    private ArrayList<DataClass> gridlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // Initialize views
        gridwishView = findViewById(R.id.gridwishView);
        gridlist = new ArrayList<>();
        wishlistAdapter = new GAdapter(this, gridlist);
        gridwishView.setAdapter(wishlistAdapter);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Fetch wishlist items from Firebase
        if (currentUser != null) {
            databaseReference.child("Wishlist").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    gridlist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                        if (dataClass != null) {
                            gridlist.add(dataClass);
                        }
                    }
                    wishlistAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors
                }
            });
        }
    }
}