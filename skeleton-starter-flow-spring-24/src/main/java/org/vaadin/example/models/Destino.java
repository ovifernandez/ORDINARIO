package org.vaadin.example.models;

public class Destino {
    private String comunidad;
    private String provincia;

    public String getComunidad() {
        return comunidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Destino(String comunidad, String provincia) {
        this.comunidad = comunidad;
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Destino{" +
                "comunidad='" + comunidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}

