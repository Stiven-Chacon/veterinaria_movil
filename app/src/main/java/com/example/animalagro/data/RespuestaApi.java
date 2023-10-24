package com.example.animalagro.data;

import com.google.gson.annotations.SerializedName;

public class RespuestaApi {
    @SerializedName("mensaje")
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }
}