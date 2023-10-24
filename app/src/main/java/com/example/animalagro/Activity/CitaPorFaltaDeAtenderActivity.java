package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.animalagro.Adapter.CitaAdminAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.CitaAdmin;
import com.example.animalagro.data.Servicio;
import com.example.animalagro.io.ApiService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CitaPorFaltaDeAtenderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CitaAdminAdapter citaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_por_falta_de_atender);


        // Configura el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCitasAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //solicitud Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com") // URL de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CitaAdmin>> call = apiService.getCitasAdmin();

        call.enqueue(new Callback<List<CitaAdmin>>() {
            @Override
            public void onResponse(Call<List<CitaAdmin>> call, Response<List<CitaAdmin>> response) {
                if (response.isSuccessful()) {
                    List<CitaAdmin> citas = response.body();

                    // Verifica que la lista de citas no sea nula
                    if (citas != null && !citas.isEmpty()) {
                        // Crea una instancia del adaptador y asígnale los datos
                        citaAdapter = new CitaAdminAdapter(CitaPorFaltaDeAtenderActivity.this, citas);

                        // Configura el RecyclerView para usar el adaptador
                        recyclerView.setAdapter(citaAdapter);
                    } else {
                        // Maneja el caso en que la lista de citas sea nula
                        showToast("La lista de citas es nula");
                    }
                } else {
                    // Maneja errores de solicitud (por ejemplo, códigos de respuesta no exitosos)
                    showToast("Error en la solicitud: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CitaAdmin>> call, Throwable t) {
                // Maneja errores de red o de solicitud
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}