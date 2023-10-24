package com.example.animalagro.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.animalagro.R;
import com.example.animalagro.data.PopularDomain;
import java.util.ArrayList;

public class PasarelaDePagoAdapter extends RecyclerView.Adapter<PasarelaDePagoAdapter.ProductViewHolder> {
    private ArrayList<PopularDomain> productList;

    public PasarelaDePagoAdapter(ArrayList<PopularDomain> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_productos_pasarela_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PopularDomain product = productList.get(position);

        // Configura los elementos de la vista con los datos del producto
        holder.productNameTextView.setText(product.getProNombre());
        holder.productPriceTextView.setText("$" + product.getProPrecio());
        holder.Cantidad.setText(String.valueOf(product.getNumberinCart()));

        String imageUrl = product.getProFoto();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;

        public TextView Cantidad;
        ImageView pic;


        public ProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            Cantidad = itemView.findViewById(R.id.Cantidad);
            pic= itemView.findViewById(R.id.pic);

        }
    }
}
