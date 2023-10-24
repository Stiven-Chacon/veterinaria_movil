package com.example.animalagro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animalagro.R;
import com.example.animalagro.data.Cita;
import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.CitaViewHolder> {

    private List<Cita> citas;
    private Context context;

    public CitaAdapter(List<Cita> citas) {
        this.citas = citas;
    }


    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_citas_list, parent, false);
        return new CitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        Cita cita = citas.get(position);

        holder.textViewFecha.setText("Fecha: " + cita.getCitafechaHora());
        holder.textViewEstado.setText("Estado de la Cita: " + cita.getCitaEstado());
        holder.textViewMascota.setText("Mascota: " + cita.getMascotaNombre());
        holder.textViewServicio.setText("Servicio: " + cita.getServicioNombre());

        // Configurar el botón para abrir la URL del PDF
        holder.buttonVerPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPdfCita = cita.getUrlPdfHistoria();
                if (urlPdfCita != null && !urlPdfCita.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPdfCita));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class CitaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFecha;
        TextView textViewEstado;
        TextView textViewMascota;
        TextView textViewServicio;
        Button buttonVerPDF;

        public CitaViewHolder(View itemView) {
            super(itemView);
            textViewFecha = itemView.findViewById(R.id.txtFechaCita);
            textViewEstado = itemView.findViewById(R.id.txtEstadoCita);
            textViewMascota = itemView.findViewById(R.id.txtMascotaCita);
            textViewServicio = itemView.findViewById(R.id.txtServicioCita);
            buttonVerPDF = itemView.findViewById(R.id.btnPDFCita);
        }
    }
}
