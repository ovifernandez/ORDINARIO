package org.vaadin.example;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Turismo {
    private int total;

    @SerializedName("_id")
    private String _id;

    @SerializedName("destino")
    private Destino to;

    @SerializedName("periodo")
    private Periodo timeRange;

    @SerializedName("origen")
    private Origen from;



    public Origen getOrigen() {
        return from;
    }

    public Destino getDestino() {
        return to;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Periodo getPeriodo() {
        return timeRange;
    }

    public int getTotal() {
        return total;
    }

    public void setOrigen(Origen from) {
        this.from = from;
    }

    public void setDestino(Destino to) {
        this.to = to;
    }

    public void setPeriodo(Periodo timeRange) {
        this.timeRange = timeRange;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Turismo(Origen from, Destino to, Periodo timeRange, int total) {
        this.from = from;
        this.to = to;
        this.timeRange = timeRange;
        this._id = UUID.randomUUID().toString();
        this.total = total;
    }
    public Turismo(Origen from, Destino to, Periodo timeRange, int total, String id) {
        this.from = from;
        this.to = to;
        this.timeRange = timeRange;
        this._id = id;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Turismo{" +
                "_id=" + _id +
                ", origen=" + from +
                ", destino=" + to +
                ", periodo=" + timeRange +
                ", total=" + total +
                '}';
    }
}

