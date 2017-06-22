package com.vfconsulting.barbieri.parroquia.Beans;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by barbb on 21/06/2017.
 */

public class EventoBean {

    private int id;
    private String titulo;
    private String descripcion;
    private Bitmap fondo;
    private String nombre_parroquia;
    private String estado;


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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
