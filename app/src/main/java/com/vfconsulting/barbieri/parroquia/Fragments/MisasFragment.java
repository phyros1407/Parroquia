package com.vfconsulting.barbieri.parroquia.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.vfconsulting.barbieri.parroquia.Adapters.ParroquiaAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.ParroquiaBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;
import com.vfconsulting.barbieri.parroquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 21/06/2017.
 */

public class MisasFragment extends Fragment {

    public  static List<ParroquiaBean> listar_parroquia = new ArrayList<>();
    private RecyclerView recyclerView;
    public static ParroquiaAdapter pAdapter;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.misas_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_reciclada_3);

        //SETEANDO EL ADAPTER Y RECYCLER VIEW
        Log.e("LISTA ---> ",listar_parroquia.toString() );

        pAdapter = new ParroquiaAdapter(listar_parroquia);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        pAdapter.notifyDataSetChanged();
        prepareParroquiaData();


        return view;
}


    private void prepareParroquiaData() {

        String url = "http://52.15.40.243/serviciosparroquia/index.php/parroquia";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->",response.toString());

                            listar_parroquia.clear();

                            for (int i = 0; i < response.length(); i++) {



                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String nombre = jsonObject.getString("nombre");
                                String direccion = jsonObject.getString("direccion");
                                double latitud = jsonObject.getDouble("latitud");
                                double longitud = jsonObject.getDouble("longitud");


                                ParroquiaBean parroquia = new ParroquiaBean();
                                parroquia.setId_Parroquia(id);
                                parroquia.setNombre(nombre);
                                parroquia.setDireccion(direccion);
                                parroquia.setLatitud(latitud);
                                parroquia.setLongitud(longitud);

                                listar_parroquia.add(parroquia);

                            }

                            pAdapter.notifyDataSetChanged();

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(view, "OCCURRIÓ UN PROBLEMA, INTENTE MAS TARDE", Snackbar.LENGTH_LONG).show();
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