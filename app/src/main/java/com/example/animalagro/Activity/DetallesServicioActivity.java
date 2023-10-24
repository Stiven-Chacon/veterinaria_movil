package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.animalagro.R;
import com.example.animalagro.data.Mascota;
import com.example.animalagro.data.RespuestaApi;
import com.example.animalagro.io.ApiService;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class DetallesServicioActivity extends AppCompatActivity {
    private Spinner spinnerMascotas;
    private ApiService apiService;
    private EditText fechaEditText;
    private EditText horaEditText;
    private EditText sintomasEditText;
    private Button confirmarCitaButton;
    public int userId; // Variable para almacenar el ID del Usuario
    private int idMascotaSeleccionada; // Variable para almacenar el ID de la mascota

    public int idServicio;// Variable para almacenar el ID del Servicio seleccionado
    private List<Mascota> mascotas; // Variable para almacenar la lista de mascotas
    private Calendar calendar;
    private Spinner horaSpinner; // Agregamos un Spinner para la hora

    // Array de horas
    private String[] horas = {
            "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
            "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_servicio);

        // Recupera los datos del servicio desde el Intent
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String medico = intent.getStringExtra("Medico");
        idServicio = intent.getIntExtra("idServicio", 0);

        // Encuentra los TextView en tu diseño XML y establece los datos del servicio
        EditText nombreTextView = findViewById(R.id.detalleNombreEditText);
        EditText MedicoTextView = findViewById(R.id.DetalleMedicoEditText);


        nombreTextView.setText(nombre);
        MedicoTextView.setText(medico);


        // Recupera los datos del usuario desde el Intent
        userId = intent.getIntExtra("id", 0);
        String username = intent.getStringExtra("username");
        String firstName = intent.getStringExtra("first_name");
        String lastName = intent.getStringExtra("last_name");
        String email = intent.getStringExtra("email");
        String userNoDoc = intent.getStringExtra("userNoDoc");

        // Encuentra los TextView en tu diseño XML para mostrar los detalles del usuario
        EditText firstNameTextView = findViewById(R.id.firstNameEditText);
        EditText emailTextView = findViewById(R.id.emailEditText);
        EditText userNoDocTextView = findViewById(R.id.userNoDocEditText);

        // Establece los datos del usuario en los TextViews
        firstNameTextView.setText(firstName+ " "+ lastName);
        emailTextView.setText(email);
        userNoDocTextView.setText(userNoDoc);

        // Encuentra los elementos de UI para fecha, hora, síntomas y el botón de confirmación
        fechaEditText = findViewById(R.id.fechaEditText);
        sintomasEditText = findViewById(R.id.txtSintomasAgg);
        confirmarCitaButton = findViewById(R.id.confirmarCitaButton);

        // Inicializa el calendario
        calendar = Calendar.getInstance();

        // Encuentra el Spinner para seleccionar la hora
        horaSpinner = findViewById(R.id.txtHoraCita);

        ArrayAdapter<String> horaAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, horas
        );

        // Establecer el diseño de la lista desplegable
        horaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        horaSpinner.setAdapter(horaAdapter);

        // Configura un ClickListener para el botón de confirmación
        confirmarCitaButton.setOnClickListener(v -> confirmarCita());


        // Inicializa el Spinner y carga la lista de mascotas
        spinnerMascotas = findViewById(R.id.spinnerMascotas);
        mascotas = new ArrayList<>(); // Inicializa la lista de mascotas

        // Configura un adaptador vacío para el Spinner
        ArrayAdapter<Mascota> adapter = new ArrayAdapter<>(
                DetallesServicioActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMascotas.setAdapter(adapter);

        // Metodo para sacar el id de la mascota selecionada
        spinnerMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!mascotas.isEmpty()) {
                    // Obtiene la mascota seleccionada del Spinner y guarda su ID
                    Mascota mascotaSeleccionada = mascotas.get(position);
                    idMascotaSeleccionada = mascotaSeleccionada.getId(); // Obtén el ID de la mascota seleccionada directamente
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        cargarListaMascotas(userId);

        // Encuentra el botón que mostrará el DatePicker
        Button mostrarDatePickerButton = findViewById(R.id.mostrarDatePickerButton);

        // Configura un OnClickListener para mostrar el DatePicker cuando se haga clic en el botón
        mostrarDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }


    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public DatePickerDialog onCreateDialog(Bundle savedInstanceState) {
            // Obtiene la fecha actual para mostrarla como predeterminada en el DatePicker
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Crea una instancia de DatePickerDialog y devuelve la fecha seleccionada
            return new DatePickerDialog(requireActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Actualiza la fecha en el EditText
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            ((DetallesServicioActivity) requireActivity()).updateDate(selectedDate);
        }
    }

    // Método para actualizar el valor del EditText con la fecha seleccionada
    private void updateDate(String selectedDate) {
        fechaEditText.setText(selectedDate);
    }


    private void cargarListaMascotas(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://veterinariaanimalagro.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Llama a la API para obtener la lista de mascotas del usuario
        Call<List<Mascota>> call = apiService.getMascotasByUserId(userId);
        call.enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                if (response.isSuccessful()) {
                    mascotas = response.body(); // Asigna los datos de mascotas obtenidos desde la API
                    if (mascotas != null && !mascotas.isEmpty()) {
                        // Crea una lista de nombres de mascotas
                        List<String> nombresMascotas = new ArrayList<>();

                        for (Mascota mascota : mascotas) {
                            nombresMascotas.add(mascota.getNombre());
                        }

                        // Crea un ArrayAdapter y establece los datos en el Spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                DetallesServicioActivity.this, android.R.layout.simple_spinner_item, nombresMascotas
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMascotas.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                // Manejar error de conexión
            }
        });
    }


    private void confirmarCita() {
        // Formatea la fecha y hora seleccionadas en una cadena
        String fecha = fechaEditText.getText().toString();
        String horaSeleccionada = horaSpinner.getSelectedItem().toString();
        String sintomas = sintomasEditText.getText().toString();

        // Verifica si los campos obligatorios están llenos
        if (fecha.isEmpty() || horaSeleccionada.isEmpty() || sintomas.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return;
        }


        Call<RespuestaApi> call = apiService.agregarCita(idServicio, userId, idMascotaSeleccionada, fecha, horaSeleccionada, sintomas);
        call.enqueue(new Callback<RespuestaApi>() {
            @Override
            public void onResponse(Call<RespuestaApi> call, Response<RespuestaApi> response) {
                if (response.isSuccessful()) {
                    RespuestaApi respuesta = response.body();
                    // Procesa la respuesta del servidor, por ejemplo, muestra un mensaje de éxito.
                    if (respuesta != null && respuesta.getMensaje() != null) {
                        mostrarMensaje(respuesta.getMensaje());
                    } else {
                        mostrarMensaje("Cita confirmada con éxito.");
                    }
                } else {
                    // Maneja un error en la respuesta del servidor, si es necesario.
                    if (response.code() == 400) {
                        mostrarMensaje("Error: Datos de entrada incompletos o cita ya existente.");
                    } else if (response.code() == 500) {
                        mostrarMensaje("Error interno del servidor.");
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaApi> call, Throwable t) {
                // Maneja un error de conexión
                mostrarMensaje("Error de conexión: " + t.getMessage());
            }
        });
    }


    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}