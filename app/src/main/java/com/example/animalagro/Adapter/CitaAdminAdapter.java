package com.example.animalagro.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.R;
import com.example.animalagro.data.CitaAdmin;

import java.util.List;
public class CitaAdminAdapter extends RecyclerView.Adapter<CitaAdminAdapter.ViewHolder> {
    private List<CitaAdmin> citas;
    private Context context;

    public CitaAdminAdapter(Context context, List<CitaAdmin> citas) {
        this.context = context;
        this.citas = citas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cita_admin_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CitaAdmin cita = citas.get(position);

        // Asigna los datos a los elementos de la vista
        holder.textServicio.setText("Servicio: " + cita.getCiServicio());
        holder.textFecha.setText("Fecha: " + cita.getCiFecha());
        holder.textUsuario.setText("Usuario: " + cita.getCiUsuario());
        holder.textEmpleado.setText("Empleado: " + cita.getCiEmpleado());
    }

    @Override
    public int getItemCount() {
        return citas != null ? citas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textServicio;
        public TextView textFecha;
        public TextView textUsuario;
        public TextView textEmpleado;

        public ViewHolder(View itemView) {
            super(itemView);
            textServicio = itemView.findViewById(R.id.textServicio);
            textFecha = itemView.findViewById(R.id.textFecha);
            textUsuario = itemView.findViewById(R.id.textUsuario);
            textEmpleado = itemView.findViewById(R.id.textEmpleado);
        }
    }
}
