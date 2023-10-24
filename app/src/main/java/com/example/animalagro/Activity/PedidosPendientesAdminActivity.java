package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.animalagro.Adapter.PedidoPendienteAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.PedidoPendienteAdmin;
import com.example.animalagro.io.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidosPendientesAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PedidoPendienteAdapter pedidoPendienteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_pendientes_admin);


        // Configura el RecyclerView
        recyclerView = findViewById(R.id.RecyclerPedidosPendientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicia la solicitud Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com") // URL de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<PedidoPendienteAdmin>> call = apiService.getPedidosAdmin();

        call.enqueue(new Callback<List<PedidoPendienteAdmin>>() {
            @Override
            public void onResponse(Call<List<PedidoPendienteAdmin>> call, Response<List<PedidoPendienteAdmin>> response) {
                if (response.isSuccessful()) {
                    List<PedidoPendienteAdmin> pedidoPendienteAdmins = response.body();

                    // Verifica que la lista de citas no sea nula
                    if (pedidoPendienteAdmins != null && !pedidoPendienteAdmins.isEmpty()) {
                        // Crea una instancia del adaptador y asígnale los datos
                        pedidoPendienteAdapter = new PedidoPendienteAdapter(pedidoPendienteAdmins);

                        // Configura el RecyclerView para usar el adaptador
                        recyclerView.setAdapter(pedidoPendienteAdapter);
                    } else {
                        showToast("La lista de citas es nula");
                    }
                } else {
                    showToast("Error en la solicitud: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PedidoPendienteAdmin>> call, Throwable t) {
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}