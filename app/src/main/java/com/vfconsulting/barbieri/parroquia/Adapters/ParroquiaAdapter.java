package com.vfconsulting.barbieri.parroquia.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vfconsulting.barbieri.parroquia.Beans.ActividadBean;
import com.vfconsulting.barbieri.parroquia.Beans.ParroquiaBean;
import com.vfconsulting.barbieri.parroquia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 23/06/2017.
 */

public class ParroquiaAdapter extends RecyclerView.Adapter<ParroquiaAdapter.MyViewHolder> {

    private List<ParroquiaBean> listar_parroquias = new ArrayList<>();
    private  MyViewHolder vh;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre_parroquia;
        public TextView direccion_parroquia;

        public MyViewHolder(View view) {
            super(view);
            nombre_parroquia = (TextView) view.findViewById(R.id.nombre_parroquia);
            direccion_parroquia = (TextView) view.findViewById(R.id.direccion_parroquia);
        }
    }

    public ParroquiaAdapter(List<ParroquiaBean> listar_parroquias) {
        this.listar_parroquias = listar_parroquias;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parroquia_lista, parent, false);

       vh = new MyViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ParroquiaBean parroquia = listar_parroquias.get(position);
        holder.nombre_parroquia.setText(parroquia.getNombre());
        holder.direccion_parroquia.setText(parroquia.getDireccion());
    }


    @Override
    public int getItemCount() {
        return listar_parroquias.size();
    }

    public long getPostion(){

        return vh.getAdapterPosition();

    }

}
