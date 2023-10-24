package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.animalagro.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.Adapter.PopularListAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.Categoria;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.io.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductosPorCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProductosDeLaCategoria;
    private List<PopularDomain> listaProductosCategoria;
    private PopularListAdapter adaptador;
    private TextView catNombreTextView;
    private ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_por_categoria);

        catNombreTextView = findViewById(R.id.nombreCategoriaTxt);

        recyclerViewProductosDeLaCategoria = findViewById(R.id.recyclerViewProductosPorCategoria);
        listaProductosCategoria = new ArrayList<>();
        adaptador = new PopularListAdapter(new ArrayList<>(listaProductosCategoria));

        int categoriaId = getIntent().getIntExtra("categoriaProducto", -1);

        // Configura el nombre de la categoría
        obtenerNombreCategoria(categoriaId);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewProductosDeLaCategoria.setLayoutManager(layoutManager);
        recyclerViewProductosDeLaCategoria.setAdapter(adaptador);

        // Llama a la función para obtener los productos de la categoría seleccionada
        obtenerProductosPorCategoria(categoriaId);
        // Inicializa backBtn
        backBtn = findViewById(R.id.backBtn);
        setVariable();
    }
    private void setVariable() {
        // Configura un OnClickListener para el botón de retroceso
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void obtenerNombreCategoria(int categoriaId) {
        // Implementa Retrofit para obtener el nombre de la categoría
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/") // URL de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Categoria>> call = apiService.obtenerCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> categorias = response.body();
                    for (Categoria categoria : categorias) {
                        if (categoria.getId() == categoriaId) {
                            catNombreTextView.setText(categoria.getCatNombre());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Toast.makeText(ProductosPorCategoriaActivity.this, "Error al obtener el nombre de la categoría", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerProductosPorCategoria(int categoriaId) {
        // Implementa Retrofit para obtener los productos
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/") // Url de la api
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<PopularDomain>> call = apiService.getPopularItems();
        call.enqueue(new Callback<ArrayList<PopularDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<PopularDomain>> call, Response<ArrayList<PopularDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PopularDomain> productos = response.body();


                    // Filtra los productos por categoría
                    for (PopularDomain producto : productos) {
                        if (producto.getProCategoria() == categoriaId) {
                            listaProductosCategoria.add(producto);
                        }
                    }

                    // Verificar si listaProductosCategoria no está vacía antes de configurar el adaptador
                    if (!listaProductosCategoria.isEmpty()) {
                        // Configurar el adaptador solo si hay datos
                        PopularListAdapter adaptador = new PopularListAdapter((ArrayList<PopularDomain>) listaProductosCategoria);
                        recyclerViewProductosDeLaCategoria.setAdapter(adaptador);
                    }

                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged();
                } else {
                    Toast.makeText(ProductosPorCategoriaActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PopularDomain>> call, Throwable t) {
                Toast.makeText(ProductosPorCategoriaActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}