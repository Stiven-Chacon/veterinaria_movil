package com.example.animalagro.data;

public class Servicio {
    private int id;
    private String serNombre;
    private String serTipo;
    private double serPrecio;
    private String serDescripcion;
    private Empleado serEmpleado;

    public Servicio(int id, String serNombre, String serTipo, double serPrecio, String serDescripcion, Empleado serEmpleado) {
        this.id = id;
        this.serNombre = serNombre;
        this.serTipo = serTipo;
        this.serPrecio = serPrecio;
        this.serDescripcion = serDescripcion;
        this.serEmpleado = serEmpleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerNombre() {
        return serNombre;
    }

    public void setSerNombre(String serNombre) {
        this.serNombre = serNombre;
    }

    public String getSerTipo() {
        return serTipo;
    }

    public void setSerTipo(String serTipo) {
        this.serTipo = serTipo;
    }

    public double getSerPrecio() {
        return serPrecio;
    }

    public void setSerPrecio(double serPrecio) {
        this.serPrecio = serPrecio;
    }

    public String getSerDescripcion() {
        return serDescripcion;
    }

    public void setSerDescripcion(String serDescripcion) {
        this.serDescripcion = serDescripcion;
    }

    public Empleado getSerEmpleado() {
        return serEmpleado;
    }

    public void setSerEmpleado(Empleado serEmpleado) {
        this.serEmpleado = serEmpleado;
    }

    public class Empleado {
        private int idEmpleado;
        private String serNombre;
        private String serTipo;

        public Empleado(int idEmpleado, String serNombre, String serTipo) {
            this.idEmpleado = idEmpleado;
            this.serNombre = serNombre;
            this.serTipo = serTipo;
        }

        public int getIdEmpleado() {
            return idEmpleado;
        }

        public void setIdEmpleado(int idEmpleado) {
            this.idEmpleado = idEmpleado;
        }

        public String getSerNombre() {
            return serNombre;
        }

        public void setSerNombre(String serNombre) {
            this.serNombre = serNombre;
        }

        public String getSerTipo() {
            return serTipo;
        }

        public void setSerTipo(String serTipo) {
            this.serTipo = serTipo;
        }
    }
}
