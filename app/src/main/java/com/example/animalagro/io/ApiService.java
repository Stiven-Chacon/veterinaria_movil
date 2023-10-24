package com.example.animalagro.io;

import com.example.animalagro.data.Categoria;
import com.example.animalagro.data.Cita;
import com.example.animalagro.data.CitaAdmin;
import com.example.animalagro.data.Estadisticas;
import com.example.animalagro.data.GananciasMensuales;
import com.example.animalagro.data.Mascota;
import com.example.animalagro.data.MascotaData;
import com.example.animalagro.data.MascotaResponse;
import com.example.animalagro.data.Pedido;
import com.example.animalagro.data.PedidoPendienteAdmin;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.data.LoginResponse;
import com.example.animalagro.data.RespuestaApi;
import com.example.animalagro.data.RespuestaDePedido;
import com.example.animalagro.data.Servicio;
import com.example.animalagro.data.UpdateUserRequest;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("/login/")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
    @GET("/todoLosProductos/")
    Call<ArrayList<PopularDomain>> getPopularItems();

    @POST("/editarUsuario/")
    Call<LoginResponse> updateUser(@Body UpdateUserRequest updateUserRequest);

    @GET("/buscar_mascota/{id}")
    Call<List<Mascota>> getMascotasByUserId(@Path("id") int userId);

    @POST("/agregarMascotaApi/")
    Call<MascotaResponse> agregarMascota(@Body MascotaData mascotaData);

    @GET("/buscar_pedidos/{id}/")
    Call<List<Pedido>> listarPedidosUsuario(@Path("id") int userId);

    @GET("/buscar_citas/{id}/")
    Call<List<Cita>> listarCitasUsuario(@Path("id") int userId);

    @GET("/listarServicios/")
    Call<List<Servicio>> getServicios();

    @FormUrlEncoded
    @POST("/agregarcitaApi/")
    Call<RespuestaApi> agregarCita(
            @Field("idServicio") int idServicio,
            @Field("idUser") int idUsuario,
            @Field("idMascota") int idMascota,
            @Field("fecha") String fecha,
            @Field("hora") String hora,
            @Field("sintomas") String sintomas
    );

    @GET("/informacionAdministradorGanancias/")
    Call<List<GananciasMensuales>> obtenerGananciasMensuales();


    @GET("/informacionAdministradorCitas/")
    Call<List<CitaAdmin>> getCitasAdmin();

    @GET("/informacionAdministradorPedidos/")
    Call<List<PedidoPendienteAdmin>> getPedidosAdmin();

    @GET("/informacionAdministradorPedidosHoy/")
    Call<Estadisticas> obtenerEstadisticas();

    @POST("/pedidoRegistrarApi/")
    Call<RespuestaDePedido> enviarPedido(@Body RequestBody jsonBody);

    @GET("/CategoriaList/")
    Call<List<Categoria>> obtenerCategorias();


}
