package com.vfconsulting.barbieri.parroquia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.HorarioAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;
import com.vfconsulting.barbieri.parroquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class HorarioFragment extends Fragment {


    List<HorarioBean> horario_dia = new ArrayList<>();
    private RecyclerView recyclerView;
    private HorarioAdapter hAdapter;
    private  ViewGroup root_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        root_view = (ViewGroup) inflater.inflate(R.layout.horarios_fragment, container, false);

        recyclerView = (RecyclerView) root_view.findViewById(R.id.lista_reciclada_4);

        int id_parroquia = getArguments().getInt("id_parroquia");
        int id_dia = getArguments().getInt("id_dia");

        hAdapter = new HorarioAdapter(horario_dia);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root_view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hAdapter);

        conseguirHorarios(id_parroquia,id_dia);

        return root_view;
    }



    public void conseguirHorarios(int id_parroquia,int id_dia){

        String url = "http://52.15.40.243/serviciosparroquia/index.php/horarios_misa?id_parroquia=" + id_parroquia+"&id_dia="+id_dia ;
        Log.e("url---> ",url);

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->",response.toString());

                            horario_dia.clear();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                HorarioBean horario = new HorarioBean();
                                horario.setId(jsonObject.getInt("id"));
                                horario.setId_dia(jsonObject.getInt("id_dia"));
                                horario.setInicio_misa(jsonObject.getString("inicio_misa"));
                                horario.setFin_misa(jsonObject.getString("fin_misa"));
                                horario.setTipo_misa("Regular");
                                horario.setId_parroquia(jsonObject.getInt("id_parroquia"));

                                horario_dia.add(horario);


                            }

                            hAdapter.notifyDataSetChanged();


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(root_view, "OCCURRIÓ UN PROBLEMA, INTENTE MAS TARDE", Snackbar.LENGTH_LONG).show();
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
