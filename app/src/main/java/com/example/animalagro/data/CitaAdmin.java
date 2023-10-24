package com.example.animalagro.data;

import java.util.List;

public class CitaAdmin {
    private String ciServicio;
    private String ciFecha;
    private String ciUsuario;
    private String ciEmpleado;


    public CitaAdmin(String ciServicio, String ciFecha, String ciUsuario, String ciEmpleado) {
        this.ciServicio = ciServicio;
        this.ciFecha = ciFecha;
        this.ciUsuario = ciUsuario;
        this.ciEmpleado = ciEmpleado;
    }

    public String getCiServicio() {
        return ciServicio;
    }

    public void setCiServicio(String ciServicio) {
        this.ciServicio = ciServicio;
    }

    public String getCiFecha() {
        return ciFecha;
    }

    public void setCiFecha(String ciFecha) {
        this.ciFecha = ciFecha;
    }

    public String getCiUsuario() {
        return ciUsuario;
    }

    public void setCiUsuario(String ciUsuario) {
        this.ciUsuario = ciUsuario;
    }

    public String getCiEmpleado() {
        return ciEmpleado;
    }

    public void setCiEmpleado(String ciEmpleado) {
        this.ciEmpleado = ciEmpleado;
    }
}
