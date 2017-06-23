package com.vfconsulting.barbieri.parroquia;


import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.EventoAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 21/06/2017.
 */

public class EventosFragment extends Fragment {

    private List<EventoBean> listaEventos = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventoAdapter eAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eventos_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_reciclada);

        eAdapter = new EventoAdapter(listaEventos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);

        prepareEventosData();


        //EVENTOS CLICK
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                EventoBean evento = listaEventos.get(position);


                //ENVIANDO INFORMACION A DETALLE_ACTIVITY
                Intent intent = new Intent(getContext(), DetalleActivity.class);
                intent.putExtra("id_evento",evento.getId());
                intent.putExtra("titulo_evento",evento.getTitulo());
                intent.putExtra("nombre_parroquia",evento.getNombre_parroquia());
                intent.putExtra("fecha_inicio",evento.getFec_ini());
                intent.putExtra("fecha_fin",evento.getFec_fin());
                intent.putExtra("descripcion_evento",evento.getDescripcion());
                startActivity(intent);

                Toast.makeText(getContext(), evento.getTitulo() + " is selected!"+evento.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return view;
    }


    //CARGANDO DATOS CON VOLLEY
    private void prepareEventosData() {

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/eventos";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String titulo = jsonObject.getString("titulo");
                                String descripcion = jsonObject.getString("descripcion");
                                String parroquia = jsonObject.getString("parroquia");
                                String fechaInicio = jsonObject.getString("fecha_inicio");
                                String fechaFin = jsonObject.getString("fecha_fin");

                                EventoBean evento = new EventoBean();
                                evento.setId(id);
                                evento.setTitulo(titulo);
                                evento.setDescripcion(descripcion);
                                evento.setNombre_parroquia(parroquia);
                                evento.setFec_ini(fechaInicio);
                                evento.setFec_fin(fechaFin);
                                listaEventos.add(evento);
                                eAdapter.notifyDataSetChanged();
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

        MySingleton.getInstance(getContext()).addToRequestQueue(arrayreq);


    }



}
