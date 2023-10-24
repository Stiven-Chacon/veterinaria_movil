package com.example.animalagro.data;

public class GananciasMensuales {
    private String mes;
    private int ganancias;

    public GananciasMensuales(String mes, int ganancias) {
        this.mes = mes;
        this.ganancias = ganancias;
    }

    public String getMes() {
        return mes;
    }

    public int getGanancias() {
        return ganancias;
    }
}
