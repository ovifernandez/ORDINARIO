package org.vaadin.example;

public class Origen {
    private String provincia;
    private String comunidad;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public Origen(String provincia, String comunidad) {
        this.provincia = provincia;
        this.comunidad = comunidad;
    }

    @Override
    public String toString() {
        return "Origen{" +
                "provincia='" + provincia + '\'' +
                ", comunidad='" + comunidad + '\'' +
                '}';
    }
}

