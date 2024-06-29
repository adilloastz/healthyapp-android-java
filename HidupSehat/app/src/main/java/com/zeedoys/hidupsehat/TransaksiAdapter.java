package com.zeedoys.hidupsehat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    private List<Transaksi> transaksiList;

    public TransaksiAdapter(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        Transaksi transaksi = transaksiList.get(position);
        holder.namaTextView.setText(transaksi.getNama());
        holder.totalTextView.setText("Rp " + transaksi.getTotal());
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView, totalTextView;

        public TransaksiViewHolder(View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.nama_transaksi);
            totalTextView = itemView.findViewById(R.id.total_transaksi);
        }
    }
}
