package com.example.animalagro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animalagro.Adapter.PasarelaDePagoAdapter;
import com.example.animalagro.R;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.data.RespuestaDePedido;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasarelaDePagoActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private TextView userNoDocTextView;
    private TextView userTelefonoTextView;
    private Button btnOrdenarPedido;
    private ImageView backBtn;
    public int userId; // Variable para almacenar el ID del Usuario



    private ArrayList<PopularDomain> selectedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarela_de_pago);
        // Recuperar la lista de productos seleccionados del Intent
        selectedProducts = (ArrayList<PopularDomain>) getIntent().getSerializableExtra("selectedProducts");

        // Inicializa los elementos de la interfaz de usuario
        firstNameTextView = findViewById(R.id.firstNameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        userNoDocTextView = findViewById(R.id.userNoDocTextView);
        userTelefonoTextView = findViewById(R.id.userTelefonoTextView);

        // Configura el RecyclerView
        RecyclerView recyclerView = findViewById(R.id.pasarelapagoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PasarelaDePagoAdapter productAdapter = new PasarelaDePagoAdapter(selectedProducts);
        recyclerView.setAdapter(productAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("userId", 0);
            String firstName = intent.getStringExtra("firstName");
            String lastName = intent.getStringExtra("lastName");
            String email = intent.getStringExtra("email");
            String userNoDoc = intent.getStringExtra("userNoDoc");
            String userTelefono = intent.getStringExtra("userTelefono");
            String totalFee = intent.getStringExtra("totalFee");
            String tax = intent.getStringExtra("tax");
            String delivery = intent.getStringExtra("delivery");
            String total = intent.getStringExtra("total");

            // Muestra los datos del usuario en los elementos de la interfaz de usuario

            firstNameTextView.setText(firstName + " " + lastName);
            emailTextView.setText(email);
            userNoDocTextView.setText(userNoDoc);
            userTelefonoTextView.setText(userTelefono);

            TextView totalFeeTextView = findViewById(R.id.totalFeeTextView);
            TextView taxTextView = findViewById(R.id.taxTextView);
            TextView deliveryTextView = findViewById(R.id.deliveryTextView);
            TextView totalTextView = findViewById(R.id.totalTextView);

            totalFeeTextView.setText(totalFee);
            taxTextView.setText(tax);
            deliveryTextView.setText(delivery);
            totalTextView.setText(total);
        }
        // Obtén una referencia al botón
        btnOrdenarPedido = findViewById(R.id.btnOrdenarPedido);

        // Configura un OnClickListener para el botón
        btnOrdenarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedidoALaAPI();
            }
        });
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
    private void enviarPedidoALaAPI() {

        // Obtiene el valor seleccionado del Spinner
        Spinner spinner = findViewById(R.id.txtHoraCita);
        String metodoPago = spinner.getSelectedItem().toString();

        // Obtiene el valor seleccionado del Spinner
        Spinner spinnermunici = findViewById(R.id.txtMunicipo);
        String municipio = spinnermunici.getSelectedItem().toString();

        // Obtiene el valor ingresado en el EditText
        EditText direccionEditText = findViewById(R.id.editTextText2);
        String direccion = direccionEditText.getText().toString();

        // Construye el JSON con los datos necesarios
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("idUser", userId);
            jsonBody.put("pago", metodoPago);
            jsonBody.put("direccion", direccion);
            jsonBody.put("departamento", "Huila");
            jsonBody.put("municipio", municipio);

            JSONArray productosArray = new JSONArray();
            for (PopularDomain producto : selectedProducts) {
                JSONObject productoObj = new JSONObject();
                productoObj.put("id", producto.getId());
                productoObj.put("cantidad", producto.getNumberinCart());
                productosArray.put(productoObj);
            }
            jsonBody.put("productos", productosArray);

            Log.d("JSON_PEDIDO", jsonBody.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Configura Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com") // URL de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Realiza la solicitud HTTP POST utilizando Retrofit
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());
        Call<RespuestaDePedido> call = apiService.enviarPedido(requestBody);
        call.enqueue(new Callback<RespuestaDePedido>() {
            @Override
            public void onResponse(Call<RespuestaDePedido> call, Response<RespuestaDePedido> response) {
                if (response.isSuccessful()) {
                    RespuestaDePedido respuesta = response.body();
                    mostrarDialogo();

                } else {
                    mostrarMensaje("Error en el pedido: Código " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RespuestaDePedido> call, Throwable t) {
                mostrarMensaje("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Señor usuario, su pedido fue exitoso. En breve, recibirá la factura en su correo electrónico. Por favor, realice el pago y envíe una captura de pantalla al número de WhatsApp proporcionado en el correo.")
                .setTitle("Pedido Exitoso");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface
                                        dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}