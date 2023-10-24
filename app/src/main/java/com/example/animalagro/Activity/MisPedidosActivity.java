package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.animalagro.Adapter.PedidoAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.Pedido;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MisPedidosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PedidoAdapter pedidoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);

        recyclerView = findViewById(R.id.RecyclerMisMascotas);

        // Configura el adaptador
        pedidoAdapter = new PedidoAdapter(new ArrayList<>());
        recyclerView.setAdapter(pedidoAdapter);

        // Configura el LayoutManager para RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Realiza una solicitud para obtener los pedidos desde la API
        obtenerPedidosDesdeAPI();
    }

    private void obtenerPedidosDesdeAPI() {
        ApiService apiService = ApiClient.retrofit.create(ApiService.class);

        int idUser = getIntent().getIntExtra("id", 0);

        Call<List<Pedido>> call = apiService.listarPedidosUsuario(idUser);

        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if (response.isSuccessful()) {
                    List<Pedido> pedidos = response.body();
                    if (pedidos != null) {
                        // Actualiza el adaptador con los datos de l1a API
                        pedidoAdapter = new PedidoAdapter(pedidos);
                        recyclerView.setAdapter(pedidoAdapter);
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
            }
        });
    }
}