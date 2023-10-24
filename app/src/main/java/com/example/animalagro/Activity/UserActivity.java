package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.animalagro.R;
import com.example.animalagro.data.LoginResponse;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import android.net.Uri;



public class UserActivity extends AppCompatActivity {
    private EditText User, Password;
    private Button buttonLogin;
    private ApiService apiService;
    private String url;
    public ArrayList<LoginResponse> userArrayList = new ArrayList<>(); // ArrayList para almacenar los datos del usuario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Obtén una referencia al TextView
        TextView registrarTextView = findViewById(R.id.registrar);
        url="http://veterinariaanimalagro.pythonanywhere.com/vistaRegistrarse/";

        registrarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        // Inicializar ApiService
        apiService = ApiClient.retrofit.create(ApiService.class);

        User = findViewById(R.id.TxtUser);
        Password = findViewById(R.id.TxtPassword);
        buttonLogin = findViewById(R.id.btnLogin);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = User.getText().toString();
                String password = Password.getText().toString();

                Call<LoginResponse> call = apiService.login(username, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            // Procesar la respuesta y obtener los roles del usuario
                            List<Integer> groups = loginResponse.getGroups();

                            if (groups != null && groups.contains(1)) {
                                // El usuario tiene el rol 1 (usuario), dirígelo a la actividad de usuario
                                Intent indexUserIntent = new Intent(UserActivity.this, MainActivityUser.class);


                                // Pasa los datos necesarios al UsuarioActivity
                                indexUserIntent.putExtra("id", loginResponse.getId());
                                indexUserIntent.putExtra("username", loginResponse.getUsername());
                                indexUserIntent.putExtra("password", loginResponse.getPassword());
                                indexUserIntent.putExtra("first_name", loginResponse.getFirst_name());
                                indexUserIntent.putExtra("last_name", loginResponse.getLast_name());
                                indexUserIntent.putExtra("email", loginResponse.getEmail());
                                indexUserIntent.putExtra("userNoDoc", loginResponse.getUserNoDoc());
                                indexUserIntent.putExtra("userTelefono", loginResponse.getUserTelefono());
                                indexUserIntent.putExtra("userFoto", loginResponse.getUserFoto());

                                // Guardar los datos del usuario en SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                // Guardar los datos del usuario
                                editor.putInt("id", loginResponse.getId());
                                editor.putString("username", loginResponse.getUsername());
                                editor.putString("password", loginResponse.getPassword());
                                editor.putString("first_name", loginResponse.getFirst_name());
                                editor.putString("last_name", loginResponse.getLast_name());
                                editor.putString("email", loginResponse.getEmail());
                                editor.putString("userNoDoc", loginResponse.getUserNoDoc());
                                editor.putString("userTelefono", loginResponse.getUserTelefono());
                                editor.putString("userFoto", loginResponse.getUserFoto());

                                // Aplicar los cambios
                                editor.apply();

                                // Mostrar mensaje de bienvenida
                                String firstName = loginResponse.getFirst_name();
                                Toast.makeText(UserActivity.this, "¡Bienvenido! " + firstName, Toast.LENGTH_SHORT).show();

                                startActivity(indexUserIntent);

                            } else if (groups != null && groups.contains(2)) {
                                // El usuario tiene el rol 2 (administrador), dirígelo a la actividad de administrador
                                Intent adminIntent = new Intent(UserActivity.this, IndexAdminActivity.class);
                                // Pasa los datos necesarios al AdminActivity
                                adminIntent.putExtra("id", loginResponse.getId());
                                adminIntent.putExtra("username", loginResponse.getUsername());
                                adminIntent.putExtra("userFoto", loginResponse.getUserFoto());
                                adminIntent.putExtra("first_name", loginResponse.getFirst_name());
                                adminIntent.putExtra("last_name", loginResponse.getLast_name());
                                adminIntent.putExtra("email", loginResponse.getEmail());
                                adminIntent.putExtra("userFoto", loginResponse.getUserFoto());


                                // Mostrar mensaje de bienvenida
                                String firstName = loginResponse.getFirst_name();
                                Toast.makeText(UserActivity.this, "¡Bienvenido! " + firstName, Toast.LENGTH_SHORT).show();
                                startActivity(adminIntent);
                            }
                        } else {
                            Toast.makeText(UserActivity.this, "¡Credenciales Incorrectas!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(UserActivity.this, "¡Error en la Conexion de internet!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}