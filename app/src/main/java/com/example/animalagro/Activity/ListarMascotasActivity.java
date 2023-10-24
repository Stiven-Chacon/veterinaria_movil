package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.animalagro.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animalagro.R;
import com.example.animalagro.io.ApiService;
import com.example.animalagro.data.Mascota;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
public class ListarMascotasActivity extends AppCompatActivity {
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mascotas);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        int idUser = getIntent().getIntExtra("id", 0);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMascotas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Call<List<Mascota>> call = apiService.getMascotasByUserId(idUser);
        call.enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                if (response.isSuccessful()) {
                    List<Mascota> mascotas = response.body();
                    if (mascotas != null) {
                        MascotasAdapter adapter = new MascotasAdapter(mascotas);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(ListarMascotasActivity.this, "Error al obtener mascotas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                Toast.makeText(ListarMascotasActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.MascotaViewHolder> {
        private List<Mascota> mascotas;

        class MascotaViewHolder extends RecyclerView.ViewHolder {
            TextView nombreTextView;
            ImageView fotoImageView;

            MascotaViewHolder(View itemView) {
                super(itemView);
                nombreTextView = itemView.findViewById(R.id.textNombreMascota);
                fotoImageView = itemView.findViewById(R.id.imgMascotas);
            }
        }

        MascotasAdapter(List<Mascota> mascotas) {
            this.mascotas = mascotas;
        }

        @Override
        public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_mascotas_list, parent, false);
            return new MascotaViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MascotaViewHolder holder, int position) {
            Mascota mascota = mascotas.get(position);

            holder.nombreTextView.setText(mascota.getNombre());
            String baseUrl = "https://veterinariaanimalagro.pythonanywhere.com/";
            String fotoUrlCompleta = mascota.getFotoUrlCompleta(baseUrl);

            Picasso.get().load(fotoUrlCompleta).into(holder.fotoImageView);
        }

        @Override
        public int getItemCount() {
            return mascotas.size();
        }
    }
}