package com.example.animalagro.data;

import com.google.gson.annotations.SerializedName;

public class Estadisticas {
    @SerializedName("PedidosHoy")
    private int pedidosHoy;

    @SerializedName("ComprasUsuarioMes")
    private int comprasUsuarioMes;

    @SerializedName("GananciasSemana")
    private int gananciasSemana;

    public Estadisticas(int pedidosHoy, int comprasUsuarioMes, int gananciasSemana) {
        this.pedidosHoy = pedidosHoy;
        this.comprasUsuarioMes = comprasUsuarioMes;
        this.gananciasSemana = gananciasSemana;
    }

    public int getPedidosHoy() {
        return pedidosHoy;
    }

    public void setPedidosHoy(int pedidosHoy) {
        this.pedidosHoy = pedidosHoy;
    }

    public int getComprasUsuarioMes() {
        return comprasUsuarioMes;
    }

    public void setComprasUsuarioMes(int comprasUsuarioMes) {
        this.comprasUsuarioMes = comprasUsuarioMes;
    }

    public int getGananciasSemana() {
        return gananciasSemana;
    }

    public void setGananciasSemana(int gananciasSemana) {
        this.gananciasSemana = gananciasSemana;
    }
}
