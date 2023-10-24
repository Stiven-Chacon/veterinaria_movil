package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.animalagro.R;
import com.example.animalagro.data.Estadisticas;
import com.example.animalagro.data.GananciasMensuales;
import com.example.animalagro.io.ApiClient;
import com.example.animalagro.io.ApiService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;
import com.google.android.material.imageview.ShapeableImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndexAdminActivity extends AppCompatActivity {
    private int id;
    private String first_name;
    private String lastName;
    private String email;
    private String userFoto;
    private ApiService apiService;
    private TextView txtGananciasSemana;
    private TextView txtPedidosHoy;
    private TextView txtComprasUsuarioMes;
    private ImageView backBtn;

    private static final String[] MESES_ABREVIADOS = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_admin);

        //Metodo para la navegacion de las funcionalidades del Administrador
        bottom_navigation_admin();
        // Traemos los datos del usuario
        int id = getIntent().getIntExtra("id", 0);
        String firstName = getIntent().getStringExtra("first_name");
        String email = getIntent().getStringExtra("email");
        String lastName = getIntent().getStringExtra("last_name");

        TextView txtFirstName = findViewById(R.id.txtNombreAdmin);
        TextView txtemail = findViewById(R.id.txtemailAdmin);

        txtFirstName.setText(firstName + " " + lastName);
        txtemail.setText(email);

        // Traemos la URL de la imagen del usuario
        String userFotoUrl = ApiClient.BASE_URL + getIntent().getStringExtra("userFoto");

        ShapeableImageView imgPerfil = findViewById(R.id.imgAdminPerfil);

        Picasso.get().load(userFotoUrl).into(imgPerfil);


        //Grafica
        BarChart barChart = findViewById(R.id.barChart);
        GananciasMensuales(barChart);

        // Inicializar TextViews de las estadísticas
        txtGananciasSemana = findViewById(R.id.txtGananciasSemana);
        txtPedidosHoy = findViewById(R.id.txtPedidosHoy);
        txtComprasUsuarioMes = findViewById(R.id.txtComprasUsuarioMes);

        // Obtener datos de estadísticas desde la API
        obtenerEstadisticasDesdeAPI();

        // Inicializa backBtn
        backBtn = findViewById(R.id.backBtn);
        setVariable();
    }
    private void setVariable() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editarUserIntent = new Intent(IndexAdminActivity.this, MainActivity.class);
                startActivity(editarUserIntent);
            }
        });
    }


    private void obtenerEstadisticasDesdeAPI() {
        // Inicializa Retrofit para realizar la solicitud HTTP a la API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Realiza la solicitud HTTP para obtener los datos de estadísticas.
        Call<Estadisticas> call = apiService.obtenerEstadisticas();
        call.enqueue(new Callback<Estadisticas>() {
            @Override
            public void onResponse(Call<Estadisticas> call, Response<Estadisticas> response) {
                if (response.isSuccessful()) {
                    Estadisticas stats = response.body();
                    // Llama a la función para mostrar los datos en TextViews
                    mostrarDatosEnTextView(stats);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Estadisticas> call, Throwable t) {
                // Maneja errores de conexión
            }
        });
    }

    private void mostrarDatosEnTextView(Estadisticas stats) {
        // Mostrar datos en TextViews
        txtGananciasSemana.setText("$" + String.valueOf(stats.getGananciasSemana()));
        txtPedidosHoy.setText(String.valueOf(stats.getPedidosHoy()));
        txtComprasUsuarioMes.setText(String.valueOf(stats.getComprasUsuarioMes()));
    }
    private void GananciasMensuales(BarChart chart) {
        // Inicializa Retrofit para realizar la solicitud HTTP a la API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Realiza la solicitud HTTP para obtener los datos de ganancias mensuales.
        Call<List<GananciasMensuales>> call = apiService.obtenerGananciasMensuales();
        call.enqueue(new Callback<List<GananciasMensuales>>() {
            @Override
            public void onResponse(Call<List<GananciasMensuales>> call, Response<List<GananciasMensuales>> response) {
                if (response.isSuccessful()) {
                    List<GananciasMensuales> gananciasMensualesList = response.body();

                    // Llena el gráfico de barras con los datos obtenidos.
                    GraficoDeBarras(gananciasMensualesList, chart);
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<GananciasMensuales>> call, Throwable t) {
            }
        });
    }

    private void GraficoDeBarras(List<GananciasMensuales> gananciasMensualesList, BarChart chart) {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> meses = new ArrayList<>();
        for (int i = 0; i < gananciasMensualesList.size(); i++) {
            GananciasMensuales gananciasMensuales = gananciasMensualesList.get(i);
            float ganancias = (float) gananciasMensuales.getGanancias();
            String mes = gananciasMensuales.getMes();

            String mesAbreviado = mes.substring(0, 3).toLowerCase().toUpperCase();
            // Agrega las ganancias al gráfico.
            values.add(new BarEntry(i, ganancias));
            meses.add(mesAbreviado);
        }
        // Obtén una referencia al objeto Resources desde el contexto de tu aplicación
        Resources resources = getResources();

        // Accede a los colores definidos en colors.xml
        int colorNaranja = resources.getColor(R.color.naranja);
        int colorRed = resources.getColor(R.color.red);
        int colorGreen = resources.getColor(R.color.green);

        BarDataSet dataSet = new BarDataSet(values, "");
        dataSet.setColors(new int[]{colorNaranja, colorRed, colorGreen});


        BarData data = new BarData(dataSet);
        // Configura el eje X para mostrar los meses
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(meses));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setLabelCount(meses.size());

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void bottom_navigation_admin() {
        //Fragmento de codigo para Citas Agendadas
        ConstraintLayout BtnCitasAgendadas = findViewById(R.id.BtnCitasAgendadas);
        BtnCitasAgendadas.setOnClickListener(v -> startActivity(new Intent(IndexAdminActivity.this,CitaPorFaltaDeAtenderActivity.class)));

        //Fragmento de codigo para Pedidos Pendientes
        ConstraintLayout BtnPedidosPendientes = findViewById(R.id.BtnPedidosPendientes);
        BtnPedidosPendientes.setOnClickListener(v -> startActivity(new Intent(IndexAdminActivity.this,PedidosPendientesAdminActivity.class)));

    }

}