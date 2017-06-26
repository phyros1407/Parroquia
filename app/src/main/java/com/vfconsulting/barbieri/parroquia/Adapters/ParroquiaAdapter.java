package com.vfconsulting.barbieri.parroquia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vfconsulting.barbieri.parroquia.Beans.ParroquiaBean;
import com.vfconsulting.barbieri.parroquia.R;
import com.vfconsulting.barbieri.parroquia.HorarioActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 23/06/2017.
 */

public class ParroquiaAdapter extends RecyclerView.Adapter<ParroquiaAdapter.MyViewHolder> {

    private List<ParroquiaBean> listar_parroquias = new ArrayList<>();
    private  MyViewHolder vh;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombre_parroquia;
        public TextView direccion_parroquia;
        public LinearLayout parte_derecha_parroquia;
        public ImageView mapa_parroquia;

        public MyViewHolder(View view) {
            super(view);
            mapa_parroquia = (ImageView) view.findViewById(R.id.mapa_parroquia);
            nombre_parroquia = (TextView) view.findViewById(R.id.nombre_parroquia);
            direccion_parroquia = (TextView) view.findViewById(R.id.direccion_parroquia);
            parte_derecha_parroquia = (LinearLayout) view.findViewById(R.id.parte_derecha_parroquia);
            parte_derecha_parroquia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.e("posicion", String.valueOf(position));
                    ParroquiaBean parroquia = listar_parroquias.get(position);
                    Log.e("parroquia", parroquia.getNombre());
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, HorarioActivity.class);
                    intent.putExtra("id_parroquia",parroquia.getId_Parroquia());
                    context.startActivity(intent);
                }
            });
            mapa_parroquia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*int position = getAdapterPosition();

                    ParroquiaBean parroquia = listar_parroquias.get(position);

                    MapaFragment m  = new MapaFragment();

                    m.irPosicion(parroquia.getLatitud(),parroquia.getLongitud());*/

                }
            });

        }

        @Override
        public void onClick(View v) {

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


}
