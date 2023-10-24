package com.example.animalagro.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.Activity.DetallesServicioActivity;
import com.example.animalagro.Activity.UserActivity;
import com.example.animalagro.R;
import com.example.animalagro.data.Servicio;

import java.util.ArrayList;
import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {
    private List<Servicio> servicios;
    private Context context;


    public ServicioAdapter(List<Servicio> servicios, Context context) {
        this.servicios = servicios;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_servicio_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Servicio servicio = servicios.get(position);
        holder.nombreTextView.setText(servicio.getSerNombre());
        holder.tipoTextView.setText(servicio.getSerTipo());
        holder.precioTextView.setText(String.valueOf(servicio.getSerPrecio()));

        holder.btnAgendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verificar si el usuario está logeado
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.contains("id"); // Verificar si el ID del usuario está presente en SharedPreferences
                if (isLoggedIn) {
                    // El usuario está logeado, puedes permitir que continúe
                    // Aquí iniciamos la nueva actividad DetallesServicioActivity
                    Intent intent = new Intent(context, DetallesServicioActivity.class);

                    // Pasamos los datos del servicio a la actividad DetallesServicioActivity
                    intent.putExtra("nombre", servicio.getSerNombre());
                    intent.putExtra("tipo", servicio.getSerTipo());
                    intent.putExtra("precio", servicio.getSerPrecio());
                    intent.putExtra("idServicio", servicio.getId());
                    intent.putExtra("Medico", servicio.getSerEmpleado().getSerNombre());

                    // Aquí obtienes y pasas los datos del usuario desde SharedPreferences
                    int userId = sharedPreferences.getInt("id", 0);
                    String username = sharedPreferences.getString("username", "");
                    String firstName = sharedPreferences.getString("first_name", "");
                    String lastName = sharedPreferences.getString("last_name", "");
                    String email = sharedPreferences.getString("email", "");
                    String userNoDoc = sharedPreferences.getString("userNoDoc", "");
                    String userTelefono = sharedPreferences.getString("userTelefono", "");
                    String userFoto = sharedPreferences.getString("userFoto", "");

                    // Puedes agregar los datos del usuario a los extras del intent
                    intent.putExtra("id", userId);
                    intent.putExtra("username", username);
                    intent.putExtra("first_name", firstName);
                    intent.putExtra("last_name", lastName);
                    intent.putExtra("email", email);
                    intent.putExtra("userNoDoc", userNoDoc);
                    intent.putExtra("userTelefono", userTelefono);
                    intent.putExtra("userFoto", userFoto);

                    context.startActivity(intent);
                } else {
                    showLoginRequiredDialog();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView nombreTextView;
        public TextView tipoTextView;
        public TextView precioTextView;

        public Button btnAgendarCita;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardServicio);
            nombreTextView = view.findViewById(R.id.nombreTextView);
            tipoTextView = view.findViewById(R.id.tipoTextView);
            precioTextView = view.findViewById(R.id.precioTextView);
            btnAgendarCita = view.findViewById(R.id.btnAgendarCita);
        }
    }

    public void showLoginRequiredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Iniciar Sesión Requerido")
                .setMessage("Debes iniciar sesión para realizar esta acción.")
                .setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirige a la pantalla de inicio de sesión
                        Intent loginIntent = new Intent(context, UserActivity.class);
                        context.startActivity(loginIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
