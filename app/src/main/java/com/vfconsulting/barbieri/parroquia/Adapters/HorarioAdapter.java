package com.vfconsulting.barbieri.parroquia.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;
import com.vfconsulting.barbieri.parroquia.R;

import java.util.List;


/**
 * Created by barbb on 25/06/2017.
 */

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.MyViewHolder> {


    private List<HorarioBean> listaHorarios;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView inicio_misa;
        public TextView fin_misa;
        public TextView tipo_misa;

        public MyViewHolder(View view) {
            super(view);
            inicio_misa = (TextView)view.findViewById(R.id.inicio_misa);
            fin_misa = (TextView)view.findViewById(R.id.fin_misa);
            tipo_misa = (TextView)view.findViewById(R.id.tipo_misa);
        }
    }

    public HorarioAdapter(List<HorarioBean> listaHorarios) {
        this.listaHorarios = listaHorarios;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horarios_lista, parent, false);

        MyViewHolder vh = new MyViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HorarioBean horario =listaHorarios.get(position);
        holder.inicio_misa.setText(horario.getInicio_misa().toString());
        holder.tipo_misa.setText(horario.getTipo_misa());
        holder.fin_misa.setText(horario.getFin_misa().toString());
    }

    @Override
    public int getItemCount() {
        return listaHorarios.size();
    }
}
