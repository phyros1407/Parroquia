package com.vfconsulting.barbieri.parroquia.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;
import com.vfconsulting.barbieri.parroquia.R;

import java.util.List;

/**
 * Created by barbb on 21/06/2017.
 */

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.MyViewHolder> {

    private List<EventoBean> listaEventos;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public ImageView fondo;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo);
            fondo = (ImageView) view.findViewById(R.id.fondo);
        }
    }

    public EventoAdapter(List<EventoBean> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventos_lista, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventoBean evento = listaEventos.get(position);
        holder.titulo.setText(evento.getTitulo());
       // holder.fondo.setImageResource(evento.));
    }


    @Override
    public int getItemCount() {
        return listaEventos.size();
    }






}
