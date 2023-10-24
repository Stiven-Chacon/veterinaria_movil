package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.animalagro.Adapter.ServicioAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.Servicio;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServicioAdapter adapter;
    private List<Servicio> servicios;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);


        // Inicializar ApiService
        apiService = ApiClient.retrofit.create(ApiService.class);
        recyclerView = findViewById(R.id.recyclerViewServicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com") // URL de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Servicio>> call = apiService.getServicios();
        final Context context = this;
        call.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful()) {
                    servicios = response.body();
                    // Crear una instancia de ServicioAdapter con el contexto correcto
                    adapter = new ServicioAdapter(servicios, context);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ServicioActivity.this, "Error al obtener servicios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                Toast.makeText(ServicioActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}