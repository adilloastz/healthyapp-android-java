package com.zeedoys.hidupsehat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



class GAdapter extends BaseAdapter {
    private ArrayList<DataClass> gridList;
    private Context context;
    private LayoutInflater layoutInflater;

    public GAdapter(Context context, ArrayList<DataClass> gridList) {
        this.context = context;
        this.gridList = gridList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gridList.size();  // Mengembalikan ukuran dari daftar data
    }

    @Override
    public Object getItem(int position) {
        return gridList.get(position);  // Mengembalikan item pada posisi tertentu
    }

    @Override
    public long getItemId(int position) {
        return position;  // Mengembalikan ID dari item, bisa berupa posisi
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView gridImage = convertView.findViewById(R.id.gridImage);
        TextView gridCaption = convertView.findViewById(R.id.gridCaption);
        TextView gridharga = convertView.findViewById(R.id.gridharga);

        DataClass currentItem = gridList.get(position);

        Glide.with(context).load(currentItem.getDataimage()).into(gridImage);
        gridCaption.setText(currentItem.getDatanama());
        gridharga.setText("Rp. "+currentItem.getDataharga()+" /kg");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("image", currentItem.getDataimage());
                intent.putExtra("name", currentItem.getDatanama());
                intent.putExtra("description", currentItem.getDatadeskripsi());
                intent.putExtra("price", currentItem.getDataharga());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;
    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_seller, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataimage()).into(holder.recImage);
        holder.recNama.setText(dataList.get(position).getDatanama());
        holder.recDesc.setText(dataList.get(position).getDatadeskripsi());
        holder.recHarga.setText("Rp. "+dataList.get(position).getDataharga()+"\n /kg");
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recNama, recDesc, recHarga;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recHarga = itemView.findViewById(R.id.recHarga);
        recNama = itemView.findViewById(R.id.recNama);
    }
}
class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DataClass> cartList;
    private LayoutInflater layoutInflater;

    public CartAdapter(Context context, ArrayList<DataClass> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cart_item, parent, false);
        }

        ImageView cartImage = convertView.findViewById(R.id.cartImage);
        TextView cartName = convertView.findViewById(R.id.cartName);
        TextView cartPrice = convertView.findViewById(R.id.cartPrice);

        DataClass currentItem = cartList.get(position);

        Glide.with(context).load(currentItem.getDataimage()).into(cartImage);
        cartName.setText(currentItem.getDatanama());
        cartPrice.setText(currentItem.getDataharga());

        return convertView;
    }
}

class CarttransAdapter extends RecyclerView.Adapter<CarttransAdapter.ViewHolder> {

    private ArrayList<DataClass> cartItems;

    public CarttransAdapter(ArrayList<DataClass> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartt_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataClass item = cartItems.get(position);
        holder.itemName.setText(item.getDatanama());
        holder.itemPrice.setText("Rp " + item.getDataharga());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}



