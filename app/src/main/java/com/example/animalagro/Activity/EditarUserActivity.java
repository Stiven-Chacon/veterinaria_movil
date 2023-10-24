package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.animalagro.R;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;
import com.example.animalagro.data.LoginResponse;
import com.example.animalagro.data.UpdateUserRequest;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.util.Base64;



public class EditarUserActivity extends AppCompatActivity {


    private ImageView imageView;
    private Button btnfoto;
    private Uri selectedImageUri = null;
    private String imageBase64 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_user);

        Intent intent = getIntent();
        int idUser = getIntent().getIntExtra("id", 0);
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String firstName = intent.getStringExtra("first_name");
        String lastName = intent.getStringExtra("last_name");
        String email = intent.getStringExtra("email");
        String userNoDoc = intent.getStringExtra("userNoDoc");
        String userTelefono = intent.getStringExtra("userTelefono");
        String userFotoUrl = ApiClient.BASE_URL + intent.getStringExtra("userFoto");

        EditText txtFirstName = findViewById(R.id.txtFirstName);
        EditText txtLastName = findViewById(R.id.txtLastName);
        EditText txtemail = findViewById(R.id.txtemail);
        EditText txtuserNoDoc = findViewById(R.id.txtuserNoDoc);
        EditText txtuserTelefono = findViewById(R.id.txtuserTelefono);
        imageView = findViewById(R.id.imgPerfil);

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtemail.setText(email);
        txtuserNoDoc.setText(userNoDoc);
        txtuserTelefono.setText(userTelefono);
        Picasso.get().load(userFotoUrl).into(imageView);

        btnfoto = findViewById(R.id.btnfoto);
        imageView = findViewById(R.id.imgPerfil);

        ActivityResultLauncher<Intent> pickImage =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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
                        });

        btnfoto.setOnClickListener(view -> {
            Intent intentPickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImage.launch(intentPickImage);
        });

        Button btnGuardarCambios = findViewById(R.id.btneditaruser);
        btnGuardarCambios.setOnClickListener(view -> {
            Intent nuevosDatosIntent = new Intent();

            String nuevoFirstName = txtFirstName.getText().toString();
            String nuevoLastName = txtLastName.getText().toString();
            String nuevoEmail = txtemail.getText().toString();
            String nuevoUserNoDoc = txtuserNoDoc.getText().toString();
            String nuevoUserTelefono = txtuserTelefono.getText().toString();

            if (!nuevoFirstName.equals(firstName) ||
                    !nuevoLastName.equals(lastName) ||
                    !nuevoEmail.equals(email) ||
                    !nuevoUserNoDoc.equals(userNoDoc) ||
                    !nuevoUserTelefono.equals(userTelefono) ||
                    selectedImageUri != null) {


                UpdateUserRequest updatedUser = new UpdateUserRequest(
                        idUser,
                        username,
                        password,
                        nuevoFirstName,
                        nuevoLastName,
                        nuevoEmail,
                        nuevoUserNoDoc,
                        nuevoUserTelefono,
                        imageBase64,
                        false,
                        "",
                        ""
                );

                ApiService apiService = ApiClient.retrofit.create(ApiService.class);
                apiService.updateUser(updatedUser).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            setResult(RESULT_OK, nuevosDatosIntent);
                            finish();
                            Toast.makeText(EditarUserActivity.this, "¡Datos Actualizados Correctamente!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarUserActivity.this, "¡Error al Actualizar los Datos!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(EditarUserActivity.this, "Error de Conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(EditarUserActivity.this, "No se realizaron cambios en los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertImageToBase64(Uri uri) {
        InputStream inputStream = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            inputStream = getContentResolver().openInputStream(uri);
            byte[] byteArray = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(byteArray)) != -1) {
                buffer.write(byteArray, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT);
    }
}