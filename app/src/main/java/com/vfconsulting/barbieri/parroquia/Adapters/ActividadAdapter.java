package com.vfconsulting.barbieri.parroquia.Adapters;

import android.animation.ValueAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
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
        public TextView rango_hora;
        public TextView fecha_inicio;
        public ImageView ibtn;
        public TextView descripcion;
        public LinearLayout contenido_desplegable;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo_actividad);
            descripcion = (TextView) view.findViewById(R.id.descripcion_actividad);
            contenido_desplegable = (LinearLayout)view.findViewById(R.id.menu_desplegable);
            contenido_desplegable.setVisibility(View.GONE);

            rango_hora = (TextView) view.findViewById(R.id.hora_actividad);
            fecha_inicio = (TextView) view.findViewById(R.id.fecha_inicio_actividad);
            ibtn= (ImageView)view.findViewById(R.id.btn_colapsar);

            ibtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    if(contenido_desplegable.getVisibility()==View.GONE||contenido_desplegable.getVisibility()==View.INVISIBLE){
                        expand(contenido_desplegable);
                        ibtn.setImageResource(R.drawable.ic_group_collapse_00);
                        Log.e("mensaje","esta abriendose");
                    }else{
                        collapse(contenido_desplegable);
                        ibtn.setImageResource(R.drawable.ic_group_collapse_08);
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
        holder.rango_hora.setText("DESDE "+actividad.getHora_inicio()+" HASTA "+actividad.getHora_fin());
        holder.fecha_inicio.setText(actividad.getFecha_inicio_actividad());
        holder.descripcion.setText(actividad.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return listarActividades.size();
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

}
