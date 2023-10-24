package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.animalagro.Adapter.ProductoAdapter;
import com.example.animalagro.R;

import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTodosLosProductos);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 elementos por fila
        recyclerView.setLayoutManager(layoutManager);

        // Llamar a la función para obtener productos
        obtenerProductos(recyclerView);
    }
    private void obtenerProductos(final RecyclerView recyclerView) {
        ApiService apiService = ApiClient.retrofit.create(ApiService.class);

        Call<ArrayList<PopularDomain>> call = apiService.getPopularItems();

        call.enqueue(new Callback<ArrayList<PopularDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<PopularDomain>> call, Response<ArrayList<PopularDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PopularDomain> products = response.body();
                    if (products != null && !products.isEmpty()) {
                        ProductoAdapter adapter = new ProductoAdapter(products);
                        recyclerView.setAdapter(adapter);
                    } else {
                        mostrarMensaje("No hay productos disponibles");
                    }
                } else {
                    mostrarMensaje("Error al obtener los productos");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PopularDomain>> call, Throwable t) {
                mostrarMensaje("Error de red: " + t.getMessage());
            }
        });
    }


    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}