package com.example.animalagro.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.animalagro.Activity.DetailsActivity2;
import com.example.animalagro.R;

import com.example.animalagro.data.PopularDomain;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private final List<PopularDomain> items;
    //Para filtrar por categoria
    private List<PopularDomain> productos;

    Context context;

    public ProductoAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }
    public void setProductos(List<PopularDomain> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView productNameTextView;
        public final TextView productPriceTextView;
        public final ImageView productImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.titleTxt);
            productPriceTextView = itemView.findViewById(R.id.freeTxt);
            productImageView = itemView.findViewById(R.id.pic);
        }
    }
    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productos_list, parent, false);
        context = parent.getContext();
        return new ProductoAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        holder.productNameTextView.setText(items.get(position).getProNombre());
        holder.productPriceTextView.setText("$" + items.get(position).getProPrecio());



        String imageUrl = items.get(position).getProFoto();


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.productImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), DetailsActivity2.class);
            intent.putExtra("object", items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}