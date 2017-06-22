package com.vfconsulting.barbieri.parroquia.Beans;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by barbb on 21/06/2017.
 */

public class EventoBean {

    private int id;
    private String titulo;
    private Bitmap fondo;
    private String nombre_parroquia;
    private String estado;
    private String fec_ini;
    private String fec_fin;

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Bitmap getFondo() {
        return fondo;
    }

    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

    public String getNombre_parroquia() {
        return nombre_parroquia;
    }

    public void setNombre_parroquia(String nombre_parroquia) {
        this.nombre_parroquia = nombre_parroquia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
