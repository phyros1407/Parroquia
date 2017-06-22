package com.vfconsulting.barbieri.parroquia;


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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.stream.JsonReader;
import com.vfconsulting.barbieri.parroquia.Adapters.EventoAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        prepareMovieData();


        return view;
    }



    private void prepareMovieData() {

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/eventos";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String titulo = jsonObject.getString("titulo");
                                EventoBean evento = new EventoBean();
                                evento.setTitulo(titulo);
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
