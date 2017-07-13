package com.vfconsulting.barbieri.parroquia.Adapters;

import android.animation.ValueAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        public ImageView ibtn;
        public TextView descripcion;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo_actividad);
            descripcion = (TextView) view.findViewById(R.id.descripcion_actividad);
            collapse(descripcion, 500, 0);

            /*hora_inicio = (TextView) view.findViewById(R.id.hora_inicio);
            hora_fin = (TextView) view.findViewById(R.id.hora_fin);*/
            fecha_inicio = (TextView) view.findViewById(R.id.fecha_inicio_actividad);
            ibtn= (ImageView)view.findViewById(R.id.btn_colapsar);

            ibtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(descripcion.getLayoutParams().height==0){
                        int tamaño_fin = LinearLayout.LayoutParams.MATCH_PARENT;
                        expand(descripcion, 500, tamaño_fin);
                        Log.e("mensaje","esta abriendose");
                    }else{
                        collapse(descripcion, 500, 0);
                        Log.e("mensaje","esta cerrandose");
                    }

                }
            });
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

    }


    @Override
    public int getItemCount() {
        return listarActividades.size();
    }


    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }



}
