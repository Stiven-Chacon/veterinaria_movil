package com.example.animalagro.data;

import com.google.gson.annotations.SerializedName;

public class RespuestaDePedido {
    @SerializedName("mensaje")
    private String mensaje;  // Puedes ajustar el nombre del campo según la respuesta de tu API

    public String getMensaje() {
        return mensaje;
    }
}
