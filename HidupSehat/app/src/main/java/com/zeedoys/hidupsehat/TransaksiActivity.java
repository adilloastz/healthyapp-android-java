package com.zeedoys.hidupsehat;

import android.os.Bundle;
import android.util.Log;

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
import java.util.List;

public class TransaksiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private List<Transaksi> transaksiList;
    private TransaksiAdapter transaksiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        recyclerView = findViewById(R.id.recyclerViewTransaksi);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        transaksiList = new ArrayList<>();
        transaksiAdapter = new TransaksiAdapter(transaksiList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transaksiAdapter);

        loadTransaksi();
    }

    private void loadTransaksi() {
        if (currentUser != null) {
            databaseReference.child("Transaksi").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    transaksiList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Transaksi transaksi = dataSnapshot.getValue(Transaksi.class);
                        if (transaksi != null) {
                            transaksi.setNama(dataSnapshot.getKey()); // Set the transaction name
                            transaksiList.add(transaksi);
                        } else {
                            Log.e("TransaksiActivity", "Null transaksi object for key: " + dataSnapshot.getKey());
                        }
                    }
                    transaksiAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("TransaksiActivity", "Database error: " + error.getMessage());
                }
            });
        }
    }
}
