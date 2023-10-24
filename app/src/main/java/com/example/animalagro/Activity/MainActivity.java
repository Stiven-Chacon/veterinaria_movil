package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animalagro.Adapter.CategoriaAdapter;
import com.example.animalagro.Adapter.PopularListAdapter;
import com.example.animalagro.data.Categoria;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.R;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    private RecyclerView.Adapter adapterPupolar;
    private RecyclerView recyclerViewPopular;
    private RecyclerView recyclerViewCategorias; // Agrega RecyclerView para categorías
    private CategoriaAdapter categoriaAdapter; // Agrega adaptador para categorías
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPopular = findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categoriaAdapter = new CategoriaAdapter();

        initRecyclerView();
        bottom_navigation();
        initRecyclerViewCategorias();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Limpia las preferencias compartidas para eliminar los datos de inicio de sesión
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Elimina todos los datos de inicio de sesión
        editor.apply();
        super.onBackPressed();
    }

    private void bottom_navigation() {
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);
        LinearLayout Userbtn=findViewById(R.id.Userbtn);
        LinearLayout ServicioBtn=findViewById(R.id.ServicioBtn);
        TextView btnverTodos= findViewById(R.id.btnverTodos);




        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MainActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
        Userbtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,UserActivity.class)));
        ServicioBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ServicioActivity.class)));
        btnverTodos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ProductoActivity.class)));

    }
    private void initRecyclerViewCategorias() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Llamada para obtener categorías
        Call<List<Categoria>> call = apiService.obtenerCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()) {
                    List<Categoria> categorias = response.body();

                    // Asigna imágenes predefinidas a las categorías en función de su nombre
                    for (Categoria categoria : categorias) {
                        String catNombre = categoria.getCatNombre().toLowerCase();
                        String nombreImagen;

                        // Asigna el nombre de la imagen en función del nombre de la categoría
                        if (catNombre.equals("comida")) {
                            nombreImagen = "comida_icono";
                        } else if (catNombre.equals("salud")) {
                            nombreImagen = "salud_icono";
                        } else if (catNombre.equals("ciudado e higiene")) {
                            nombreImagen = "higiene_icono";
                        } else if (catNombre.equals("accesorios")) {
                            nombreImagen = "accesorios_icono";
                        } else if (catNombre.equals("medicamentos")) {
                            nombreImagen = "medicamentos_iconos";
                        } else {
                            nombreImagen = "imagen_predeterminada"; // Imagen predeterminada
                        }

                        categoria.setImagen(nombreImagen);
                    }

                    // Configura el adaptador con las categorías y el contexto de MainActivity
                    CategoriaAdapter categoriaAdapter = new CategoriaAdapter(categorias, MainActivity.this);
                    recyclerViewCategorias.setAdapter(categoriaAdapter);

                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
            }
        });
    }
    private void initRecyclerView() {
        recyclerViewPopular = findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/") // URL base API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<PopularDomain>> call = apiService.getPopularItems();
        call.enqueue(new Callback<ArrayList<PopularDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<PopularDomain>> call, Response<ArrayList<PopularDomain>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PopularDomain> items = response.body();
                    for (PopularDomain item : items) {
                        item.setProFoto(item.getProFoto());
                    }

                    adapterPupolar = new PopularListAdapter(items);
                    recyclerViewPopular.setAdapter(adapterPupolar);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PopularDomain>> call, Throwable t) {
            }
        });
    }
}