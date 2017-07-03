package com.vfconsulting.barbieri.parroquia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.ActividadAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.ActividadBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 22/06/2017.
 */

public class DetalleActivity extends AppCompatActivity{

    private ActividadAdapter aAdapter;
    private List<ActividadBean> actividades = new ArrayList<>();
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getExtras().getString("titulo_evento"));

        recyclerView = (RecyclerView) findViewById(R.id.lista_reciclada_5);

        //SETEANDO EL ADAPTER Y RECYCLER VIEW
        aAdapter = new ActividadAdapter(actividades);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aAdapter);

        prepararInformacionGeneral();
        prepareActividadesData();
        //dynamicToolbarColor();
        toolbarTextAppernce();

    }

    public void prepararInformacionGeneral(){

        //CARGANDO DATOS GENERALES DEL EVENTO EN DETALLE
        TextView titulo = (TextView)findViewById(R.id.titulo_evento_general);
        TextView parroquia = (TextView)findViewById(R.id.parroquia_evento_general);
        TextView descripcion = (TextView)findViewById(R.id.descripcion_general);
        TextView rango_fecha = (TextView)findViewById(R.id.rango_fecha);

        String titulo_evento = getIntent().getExtras().getString("titulo_evento");
        String nombre_parroquia = getIntent().getExtras().getString("nombre_parroquia");
        String descripcion_evento = getIntent().getExtras().getString("descripcion_evento");
        String fecha_inicio = getIntent().getExtras().getString("fecha_inicio");
        String fecha_fin = getIntent().getExtras().getString("fecha_fin");

        titulo.setText(titulo_evento);
        parroquia.setText(nombre_parroquia);
        descripcion.setText(descripcion_evento);
        rango_fecha.setText("DE "+fecha_inicio+" A "+fecha_fin+"\t");


    }

    private void prepareActividadesData() {

        int id = getIntent().getExtras().getInt("id_evento");

        String url = "http://env-1201049.jelasticlw.com.br/serviciosparroquia/index.php/actividades?id_evento="+id;

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("respuesta --->",response.toString());

                            actividades.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);



                                String titulo = jsonObject.getString("titulo");
                                String descripcion = jsonObject.getString("descripcion");
                                String hora_inicio = jsonObject.getString("hora_inicio_actividad");
                                String hora_fin = jsonObject.getString("hora_fin_actividad");
                                String fecha_inicio_actividad = jsonObject.getString("fecha_inicio_actividad");


                                ActividadBean actividad = new ActividadBean();


                                actividad.setTitulo(titulo);
                                actividad.setDescripcion(descripcion);
                                actividad.setHora_fin(hora_fin);
                                actividad.setHora_inicio(hora_inicio);
                                actividad.setFecha_inicio_actividad(fecha_inicio_actividad);

                                actividades.add(actividad);
                                aAdapter.notifyDataSetChanged();
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(findViewById(android.R.id.content), "OCCURRIÓ UN PROBLEMA, INTENTE MAS TARDE", Snackbar.LENGTH_LONG).show();
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

        MySingleton.getInstance(this).addToRequestQueue(arrayreq);


    }


    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.logo_congreso);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.color.primary_dark));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.color.primary_dark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
