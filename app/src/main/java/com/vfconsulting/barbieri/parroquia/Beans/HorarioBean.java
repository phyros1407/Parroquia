package com.vfconsulting.barbieri.parroquia.Beans;

import java.sql.Time;

/**
 * Created by barbb on 25/06/2017.
 */

public class HorarioBean {

    private int id;
    private int id_dia;
    private Time inicio_misa;
    private Time fin_misa;
    private String tipo_misa;
    private int id_parroquia;

    public String getTipo_misa() {
        return tipo_misa;
    }

    public void setTipo_misa(String tipo_misa) {
        this.tipo_misa = tipo_misa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_dia() {
        return id_dia;
    }

    public void setId_dia(int id_dia) {
        this.id_dia = id_dia;
    }

    public Time getInicio_misa() {
        return inicio_misa;
    }

    public void setInicio_misa(Time inicio_misa) {
        this.inicio_misa = inicio_misa;
    }

    public Time getFin_misa() {
        return fin_misa;
    }

    public void setFin_misa(Time fin_misa) {
        this.fin_misa = fin_misa;
    }

    public int getId_parroquia() {
        return id_parroquia;
    }

    public void setId_parroquia(int id_parroquia) {
        this.id_parroquia = id_parroquia;
    }
}
