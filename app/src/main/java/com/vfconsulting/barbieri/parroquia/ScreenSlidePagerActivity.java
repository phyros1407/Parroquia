package com.vfconsulting.barbieri.parroquia;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Beans.HorarioBean;
import com.vfconsulting.barbieri.parroquia.Beans.ParroquiaBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbb on 25/06/2017.
 */

public class ScreenSlidePagerActivity extends FragmentActivity  {

    private static final int NUM_PAGES = 7;
    private ViewPager mPager;
    public List<HorarioBean> horarios = new ArrayList<>();
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        loadSlides(mPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private void loadSlides( ViewPager viewPager){
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newInstance("Lunes"));
        adapter.addFragment(newInstance("Martes"));
        adapter.addFragment(newInstance("Miercoles"));
        adapter.addFragment(newInstance("Jueves"));
        adapter.addFragment(newInstance("Viernes"));
        adapter.addFragment(newInstance("Sabado"));
        adapter.addFragment(newInstance("Domingo"));

        viewPager.setAdapter(adapter);
    }

    private ScreenSlidePageFragment newInstance(String titulo){
        Bundle bundle = new Bundle();
        bundle.putString("titulo",titulo);
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public void addFragment(Fragment fragment){

            fragmentList.add(fragment);

        }
    }

    public List<HorarioBean> conseguirHorarios(int id){

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
                                horario.setInicio_misa((Time)jsonObject.getString("inici_misa"));


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


        return
    }




}
