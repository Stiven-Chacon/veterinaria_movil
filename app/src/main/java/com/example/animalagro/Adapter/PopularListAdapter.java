package com.example.animalagro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.animalagro.Activity.DetailsActivity2;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.R;

import java.util.ArrayList;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.Viewholder> {
    ArrayList<PopularDomain> items;
    Context context;

    public PopularListAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }
    public static final String BASE_URL = "https://veterinariaanimalagro.pythonanywhere.com/";


    @NonNull
    @Override
    public PopularListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListAdapter.Viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getProNombre());
        holder.freeTxt.setText("$"+items.get(position).getProPrecio());


        String imageUrl = items.get(position).getProFoto();


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);

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
    public class Viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt,freeTxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt= itemView.findViewById(R.id.titleTxt);
            freeTxt= itemView.findViewById(R.id.freeTxt);
            pic = itemView.findViewById(R.id.pic);


        }
    }
}
