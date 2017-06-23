package com.vfconsulting.barbieri.parroquia.Beans;

/**
 * Created by barbb on 22/06/2017.
 */

public class ActividadBean {

    private int id;
    private String titulo;
    private String descripcion;
    private String hora_inicio;
    private String hora_fin;
    private String fecha_inicio_actividad;


    public String getFecha_inicio_actividad() {
        return fecha_inicio_actividad;
    }

    public void setFecha_inicio_actividad(String fecha_inicio_actividad) {
        this.fecha_inicio_actividad = fecha_inicio_actividad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
