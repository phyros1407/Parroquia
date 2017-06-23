package com.vfconsulting.barbieri.parroquia;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.ActividadAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.ActividadBean;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by barbb on 22/06/2017.
 */

public class DetalleActivity extends Activity{

    ActividadAdapter aAdapter;
    List<ActividadBean> actividades;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_activity);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        toolbar.setNavigationIcon(R.drawable.quantum_ic_play_arrow_white_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }

    public void prepararInformacionGeneral(){




    }

    private void prepareMovieData() {

        int id = getIntent().getExtras().getInt("id_evento");

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/actividades?id_evento="+id;

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id_evento = jsonObject.getInt("id_evento");

                                String titulo = jsonObject.getString("titulo");
                                String descripcion = jsonObject.getString("descripcion");
                                String hora_inicio = jsonObject.getString("parroquia");
                                String hora_fin = jsonObject.getString("fecha_inicio");
                                String fecha_inicio_actividad = jsonObject.getString("fecha_inicio_actividad");


                                ActividadBean actividad = new ActividadBean();
                                actividad.setId(id_evento);
                                actividad.setTitulo(titulo);
                                actividad.setDescripcion(descripcion);
                                actividad.setHora_fin(hora_fin);
                                actividad.setHora_inicio(hora_inicio);
                                actividad.setFecha_inicio_actividad(fecha_inicio_actividad);
                                actividades.add(actividad);
                                aAdapter.notifyDataSetChanged();
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        MySingleton.getInstance(this).addToRequestQueue(arrayreq);


    }
}
