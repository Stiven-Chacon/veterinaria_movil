package com.example.animalagro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.animalagro.R;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;
import com.example.animalagro.data.MascotaData;
import com.example.animalagro.data.MascotaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AgregarMascotaActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnSeleccionarImagen;
    private Uri selectedImageUri;
    private String imageBase64;
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            selectedImageUri = data.getData();
                            imageView.setImageURI(selectedImageUri);
                            imageBase64 = convertImageToBase64(selectedImageUri);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        EditText editTipo = findViewById(R.id.txtipomas);
        EditText editRaza = findViewById(R.id.txtrazamas);
        EditText editNombre = findViewById(R.id.txtnombremas);
        imageView = findViewById(R.id.ImgMascota);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);


        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImage.launch(intent);
            }
        });
        int idUser = getIntent().getIntExtra("id", 0);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo = editTipo.getText().toString();
                String raza = editRaza.getText().toString();
                String nombre = editNombre.getText().toString();

                if (imageBase64 != null && !imageBase64.isEmpty()) {
                    Log.d("Debug", "Valores antes de crear nuevaMascotaData: id=" + idUser + ", tipo=" + tipo + ", raza=" + raza + ", nombre=" + nombre + ", imageBase64=" + imageBase64);
                    MascotaData nuevaMascotaData = new MascotaData(idUser, tipo, raza, nombre, imageBase64);

                    ApiService apiService = ApiClient.retrofit.create(ApiService.class);
                    Call<MascotaResponse> call = apiService.agregarMascota(nuevaMascotaData);
                    call.enqueue(new Callback<MascotaResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MascotaResponse> call, @NonNull Response<MascotaResponse> response) {
                            if (response.isSuccessful()) {
                                MascotaResponse mascotaResponse = response.body();
                                mostrarMensaje("Mascota agregada correctamente");
                            } else {
                                mostrarMensaje("Error al agregar la mascota");
                            }
                        }

                        @Override
                        public void onFailure(Call<MascotaResponse> call, Throwable t) {
                            // Manejar error de conexión o solicitud a la API
                            mostrarMensaje("Error de conexión");
                        }
                    });
                } else {
                    mostrarMensaje("Por favor, selecciona una imagen antes de agregar la mascota.");
                }
            }
        });
    }

    // Función para convertir la imagen a base64
    private String convertImageToBase64(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            if (inputStream != null) {
                byte[] byteArray = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(byteArray)) != -1) {
                    buffer.write(byteArray, 0, bytesRead);
                }
                inputStream.close();
            }
            return Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}