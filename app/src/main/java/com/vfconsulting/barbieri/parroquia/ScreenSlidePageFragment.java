package com.vfconsulting.barbieri.parroquia;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePageFragment extends Fragment {


    List<HorarioBean> horario_dia = new ArrayList<>();
    private RecyclerView recyclerView;
    private HorarioAdapter hAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        ViewGroup root_view = (ViewGroup) inflater.inflate(R.layout.horarios_fragment, container, false);

        recyclerView = (RecyclerView) root_view.findViewById(R.id.lista_reciclada_4);

        if(getArguments()!=null){

            List<HorarioBean> horarios = new ArrayList<>();
            horarios = (List<HorarioBean>)getArguments().getSerializable("lista_horario");
            horario_dia.clear();

            for(int i = 0;i<horarios.size();i++){

                if(horarios.get(i).getId_dia() == getArguments().getInt("dia")){

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

            hAdapter = new HorarioAdapter(horario_dia);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root_view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(hAdapter);

            hAdapter.notifyDataSetChanged();


        }

        //SETEANDO EL ADAPTER Y RECYCLER VIEW

        return root_view;
    }



}
