package com.vfconsulting.barbieri.parroquia.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.vfconsulting.barbieri.parroquia.Beans.ActividadBean;
import com.vfconsulting.barbieri.parroquia.R;

import java.util.List;

/**
 * Created by barbb on 21/06/2017.
 */

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.MyViewHolder> {

    private List<ActividadBean> listarActividades;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView hora_inicio;
        public TextView hora_fin;
        public TextView fecha_inicio;
        public CardView carta;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo_actividad);
            /*hora_inicio = (TextView) view.findViewById(R.id.hora_inicio);
            hora_fin = (TextView) view.findViewById(R.id.hora_fin);*/
            fecha_inicio = (TextView) view.findViewById(R.id.fecha_inicio_actividad);
        }
    }

    public ActividadAdapter(List<ActividadBean> listarActividades) {

        this.listarActividades = listarActividades;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actividades_lista, parent, false);

        MyViewHolder vh = new MyViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ActividadBean actividad = listarActividades.get(position);
        holder.titulo.setText(actividad.getTitulo());
       /* holder.hora_inicio.setText(actividad.getHora_inicio());
        holder.hora_fin.setText(actividad.getHora_fin());*/
        holder.fecha_inicio.setText(actividad.getFecha_inicio_actividad());
        // holder.fondo.setImageResource(evento.));
    }


    @Override
    public int getItemCount() {
        return listarActividades.size();
    }






}
