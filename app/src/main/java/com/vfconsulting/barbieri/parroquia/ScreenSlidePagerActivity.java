package com.vfconsulting.barbieri.parroquia;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Adapters.TabAdapter;
import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePagerActivity extends FragmentActivity  {

    TabLayout tabs;
    ViewPager mPager;
    ArrayList<String> titulos = new ArrayList<>();
    List<HorarioBean> horarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_activity);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpager2);
        tabs = (TabLayout) this.findViewById(R.id.tab_padre);

        titulos.add("Lu");
        titulos.add("Ma");
        titulos.add("Mi");
        titulos.add("Ju");
        titulos.add("Vi");
        titulos.add("Sa");
        titulos.add("Do");
        //  AGREGANDO A LA TABs

        loadSlides(mPager);
        tabs.setupWithViewPager(mPager);
        tabTitle();

        conseguirHorarios(getIntent().getExtras().getInt("id_parroquia"));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT);

    }


    private void loadSlides( ViewPager viewPager){

        Log.e("id_parroquia", String.valueOf(getIntent().getExtras().getInt("id_parroquia")));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(newInstance(1,horarios));
        adapter.addFragment(newInstance(2, horarios));
        adapter.addFragment(newInstance(3, horarios));
        adapter.addFragment(newInstance(4, horarios));
        adapter.addFragment(newInstance(5, horarios));
        adapter.addFragment(newInstance(6, horarios));
        adapter.addFragment(newInstance(7, horarios));

        viewPager.setAdapter(adapter);
    }

    private ScreenSlidePageFragment newInstance(int dia, List<HorarioBean> horarios){
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lista_horario",(Serializable) horarios);
        bundle.putInt("dia",dia);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().commit();

        return fragment;
    }

    private void tabTitle(){
        for(int i=0;i<7;i++){
            tabs.getTabAt(i).setText(titulos.get(i));
        }
    }

public void conseguirHorarios(int id){

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/horarios_misa?id_parroquia=" + id;

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->",response.toString());

                            horarios.clear();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                HorarioBean horario = new HorarioBean();
                                horario.setId(jsonObject.getInt("id"));
                                horario.setId_dia(jsonObject.getInt("id_dia"));
                                horario.setInicio_misa(jsonObject.getString("inicio_misa"));
                                horario.setFin_misa(jsonObject.getString("fin_misa"));
                                horario.setTipo_misa("Regular");
                                horario.setId_parroquia(jsonObject.getInt("id_parroquia"));

                                horarios.add(horario);


                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
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

}
