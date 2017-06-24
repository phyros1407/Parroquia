package com.vfconsulting.barbieri.parroquia.Beans;

/**
 * Created by barbb on 23/06/2017.
 */

public class ParroquiaBean {

    private int id_parroquia;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;

    public int getId_Parroquia() {
        return id_parroquia;
    }

    public void setId_Parroquia(int id_parroquia) {
        this.id_parroquia = id_parroquia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
