package com.example.animalagro.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import com.example.animalagro.Activity.ProductosPorCategoriaActivity;
import com.example.animalagro.R;
import com.example.animalagro.data.Categoria;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private List<Categoria> categoriaList;
    private Context context;

    public CategoriaAdapter(List<Categoria> categoriaList, Context context) {
        this.categoriaList = categoriaList;
        this.context = context;
    }

    public CategoriaAdapter() {

    }

    public void setCategoriaList(List<Categoria> categorias) {
        this.categoriaList = categorias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_categoria_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categoriaList.get(position);
        holder.catNombreTextView.setText(categoria.getCatNombre());

        // Carga la imagen en el ImageView en función del nombre de la categoría
        String nombreImagen = categoria.getImagen();
        int resourceId = context.getResources().getIdentifier(nombreImagen, "drawable", context.getPackageName());
        holder.imageViewCategoria.setImageResource(resourceId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando se hace clic en un elemento, inicia ProductosPorCategoriaActivity
                Intent intent = new Intent(context, ProductosPorCategoriaActivity.class);
                intent.putExtra("categoriaProducto", categoria.getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView catNombreTextView;
        ImageView imageViewCategoria;

        public ViewHolder(View itemView) {
            super(itemView);
            catNombreTextView = itemView.findViewById(R.id.CategoriaText);
            imageViewCategoria = itemView.findViewById(R.id.imgCategoria);
        }
    }
}
