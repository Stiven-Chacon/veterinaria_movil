package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animalagro.R;
import com.example.animalagro.io.ApiClient;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class IndexUsuarioActivity extends AppCompatActivity {
    public int id;
    private String username;
    private String password;
    private String first_name;
    private String lastName;
    private String email;
    private String userNoDoc;
    private String userTelefono;
    private String userFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_usuario);

        //Metodo para la navegacion de las funcionalidades del usuario
        bottom_navigation_user();


        // Traemos los datos del usuario
        id = getIntent().getIntExtra("id", 0);
        String firstName = getIntent().getStringExtra("first_name");
        String email = getIntent().getStringExtra("email");
        String lastName = getIntent().getStringExtra("last_name");

        TextView txtFirstName = findViewById(R.id.txtnombreApe);
        TextView txtemail = findViewById(R.id.txtemail2);

        txtFirstName.setText(firstName + " " + lastName);
        txtemail.setText(email);

        // Traemos la URL de la imagen del usuario
        String userFotoUrl = ApiClient.BASE_URL + getIntent().getStringExtra("userFoto");

        ShapeableImageView imgPerfil = findViewById(R.id.imageView3);

        Picasso.get().load(userFotoUrl).into(imgPerfil);
    }

    private void bottom_navigation_user() {
        //Fragmento de codigo para ir al EditarUserActibity
        ConstraintLayout editarUser = findViewById(R.id.btnEditarPerfil);
        editarUser.setOnClickListener(view -> {
            // Enviamos los datos al Editar Usuario
            Intent editarUserIntent = new Intent(IndexUsuarioActivity.this, EditarUserActivity.class);
            editarUserIntent.putExtra("id", getIntent().getIntExtra("id", 0));
            editarUserIntent.putExtra("username", getIntent().getStringExtra("username"));
            editarUserIntent.putExtra("password", getIntent().getStringExtra("password"));
            editarUserIntent.putExtra("first_name", getIntent().getStringExtra("first_name"));
            editarUserIntent.putExtra("last_name", getIntent().getStringExtra("last_name"));
            editarUserIntent.putExtra("email", getIntent().getStringExtra("email"));
            editarUserIntent.putExtra("userNoDoc", getIntent().getStringExtra("userNoDoc"));
            editarUserIntent.putExtra("userTelefono", getIntent().getStringExtra("userTelefono"));
            editarUserIntent.putExtra("userFoto", getIntent().getStringExtra("userFoto"));

            startActivity(editarUserIntent);
        });
        //Fragmento de codigo para salir de la actividad
        ConstraintLayout salir = findViewById(R.id.BackBtn);
        salir.setOnClickListener(view -> finish());

        //Fragmento de codigo para ir al ListarMascotasActivity
        ConstraintLayout misMascotas = findViewById(R.id.BtnMisMascotas);
        misMascotas.setOnClickListener(view -> {
            // Enviamos los datos al Editar Usuario
            Intent editarUserIntent = new Intent(IndexUsuarioActivity.this, ListarMascotasActivity.class);
            editarUserIntent.putExtra("id", getIntent().getIntExtra("id", 0));
            startActivity(editarUserIntent);
        });

        //Fragmento de codigo para ir al AgregarMascotaActivity
        ConstraintLayout AgregarMascotas = findViewById(R.id.BtnAgregarMascota);
        AgregarMascotas.setOnClickListener(view -> {
            // Enviamos los datos al Editar Usuario
            Intent editarUserIntent = new Intent(IndexUsuarioActivity.this, AgregarMascotaActivity.class);
            editarUserIntent.putExtra("id", getIntent().getIntExtra("id", 0));
            startActivity(editarUserIntent);
        });

        //Fragmento de codigo para ir al MisPedidosActivity
        ConstraintLayout MisPedidos = findViewById(R.id.BtnMisPedidos);
        MisPedidos.setOnClickListener(view -> {
            // Enviamos los datos al Editar Usuario
            Intent editarUserIntent = new Intent(IndexUsuarioActivity.this, MisPedidosActivity.class);
            editarUserIntent.putExtra("id", getIntent().getIntExtra("id", 0));
            startActivity(editarUserIntent);
        });
        //Fragmento de codigo para ir al MisPedidosActivity
        ConstraintLayout MisCitas = findViewById(R.id.BtnMisCitas);
        MisCitas.setOnClickListener(view -> {
            // Enviamos los datos al Editar Usuario
            Intent editarUserIntent = new Intent(IndexUsuarioActivity.this, MisCitasActivity.class);
            editarUserIntent.putExtra("id", getIntent().getIntExtra("id", 0));
            startActivity(editarUserIntent);
        });
        // Fragmento de código para salir de mi perfil
        ConstraintLayout Salir = findViewById(R.id.BtnSalir);
        Salir.setOnClickListener(v -> {
            // Limpia las preferencias compartidas para eliminar los datos de inicio de sesión
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Elimina todos los datos de inicio de sesión
            editor.apply();

            // Inicia MainActivity
            startActivity(new Intent(IndexUsuarioActivity.this, MainActivity.class));
        });

    }
}