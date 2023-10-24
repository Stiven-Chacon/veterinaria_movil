package com.example.animalagro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.Helper.ChangeNumberItemsListener;
import com.example.animalagro.Helper.ManagmentCart;
import com.example.animalagro.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<PopularDomain> listItemSelected;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<PopularDomain> listItemSelected,Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        managmentCart=new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        holder.title.setText(listItemSelected.get(position).getProNombre());
        holder.freeEachItem.setText("$"+listItemSelected.get(position).getProPrecio());
        holder.totalEachItem.setText("$"+Math.round((listItemSelected.get(position).getNumberinCart() * listItemSelected.get(position).getProPrecio())));
        holder.num.setText(String.valueOf(listItemSelected.get(position).getNumberinCart()));

        String imageUrl = listItemSelected.get(position).getProFoto();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.pic);


        holder.plusItem.setOnClickListener(v -> {
            managmentCart.plusNumberItem(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });

        });
        holder.minusItem.setOnClickListener(v -> {
            managmentCart.minusNumberItem(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, freeEachItem, plusItem, minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.titleTxt);
            pic= itemView.findViewById(R.id.pic);
            freeEachItem= itemView.findViewById(R.id.freeEachItem);
            totalEachItem= itemView.findViewById(R.id.totalEachItem);
            plusItem= itemView.findViewById(R.id.pludCartBtn);
            minusItem= itemView.findViewById(R.id.minusCartBtn);
            num= itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
