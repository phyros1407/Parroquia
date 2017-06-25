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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.EventoAdapter;
import com.vfconsulting.barbieri.parroquia.Adapters.HorarioAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePageFragment extends Fragment {

    List<HorarioBean> horarios = new ArrayList<>();
    List<HorarioBean> horario_dia = new ArrayList<>();
    private RecyclerView recyclerView;
    private HorarioAdapter hAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        ViewGroup root_view = (ViewGroup) inflater.inflate(
                R.layout.horarios_fragment, container, false);


        recyclerView = (RecyclerView) root_view.findViewById(R.id.lista_reciclada_4);

        if(getArguments()!=null){

            int id_dia = obtenerIdDia(getArguments().getString("dia"));
            conseguirHorarios(getArguments().getInt("id_parroquia"));


            for(int i = 0;i<horarios.size();i++){

                if(horarios.get(i).getId_dia() == id_dia){

                    HorarioBean h = new HorarioBean();

                    h.setId_dia(horarios.get(i).getId_dia());
                    h.setId(horarios.get(i).getId());
                    h.setId_parroquia(horarios.get(i).getId_parroquia());
                    h.setTipo_misa(horarios.get(i).getTipo_misa());
                    h.setInicio_misa(horarios.get(i).getInicio_misa());
                    h.setFin_misa(horarios.get(i).getFin_misa());

                    horario_dia.add(h);

                }

            }

        }

        Log.e("HORARIOS DEL DIA -->>",horario_dia.toString());

        //SETEANDO EL ADAPTER Y RECYCLER VIEW
        hAdapter = new HorarioAdapter(horario_dia);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root_view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hAdapter);


        return root_view;
    }


    public int obtenerIdDia(String dia){

        int id_dia = 0;

        switch (dia.toLowerCase()){

            case "lunes" :
                id_dia = 1 ;
                break;
            case "martes" :
                id_dia = 2 ;
                break;
            case "miercoles" :
                id_dia = 3 ;
                break;
            case "jueves" :
                id_dia = 4 ;
                break;
            case "viernes" :
                id_dia = 5 ;
                break;
            case "sabado" :
                id_dia = 6 ;
                break;
            case "domiengo" :
                id_dia = 7 ;
                break;

        }

        return id_dia;
    }

    public void conseguirHorarios(int id){

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/horarios_misa?id_parroquia=" + id;

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->",response.toString());

                            horarios.clear();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                HorarioBean horario = new HorarioBean();
                                horario.setId(jsonObject.getInt("id"));
                                horario.setId_dia(jsonObject.getInt("id_dia"));
                                horario.setInicio_misa(jsonObject.getString("inici_misa"));
                                horario.setFin_misa(jsonObject.getString("fin_misa"));
                                horario.setTipo_misa("Regular");
                                horario.setId_parroquia(jsonObject.getInt("id_parroquia"));

                                horarios.add(horario);


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
