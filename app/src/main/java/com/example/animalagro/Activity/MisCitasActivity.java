package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.animalagro.Adapter.CitaAdapter;
import com.example.animalagro.Adapter.PedidoAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.Cita;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisCitasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CitaAdapter citaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_citas);

        recyclerView = findViewById(R.id.RecyclerMisCitas);

        // Configura el adaptador
        citaAdapter = new CitaAdapter(new ArrayList<>());
        recyclerView.setAdapter(citaAdapter);

        // Configura el LayoutManager para RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Realiza una solicitud para obtener las citas desde la API
        obtenerCitasDesdeAPI();
    }

    private void obtenerCitasDesdeAPI() {
        ApiService apiService = ApiClient.retrofit.create(ApiService.class);

        int idUser = getIntent().getIntExtra("id", 0);

        Call<List<Cita>> call = apiService.listarCitasUsuario(idUser);

        call.enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
                if (response.isSuccessful()) {
                    List<Cita> citas = response.body();
                    if (citas != null) {
                        // Actualiza los datos en el adaptador
                        citaAdapter = new CitaAdapter(citas);
                        recyclerView.setAdapter(citaAdapter);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
            }
        });
    }
}