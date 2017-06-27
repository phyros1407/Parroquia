package com.vfconsulting.barbieri.parroquia;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vfconsulting.barbieri.parroquia.Adapters.TabAdapter;
import com.vfconsulting.barbieri.parroquia.Fragments.HorarioFragment;

import java.util.ArrayList;

/**
 * Created by barbb on 25/06/2017.
 */

public class HorarioActivity extends FragmentActivity  {

    TabLayout tabs;
    ViewPager mPager;
    ArrayList<String> titulos = new ArrayList<>();

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
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT ,1500);

        Button btn_aceptar = (Button)findViewById(R.id.btn_ok);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });


    }


    private void loadSlides( ViewPager viewPager){

        Log.e("id_parroquia", String.valueOf(getIntent().getExtras().getInt("id_parroquia")));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        int id_parroquia = getIntent().getExtras().getInt("id_parroquia");
        adapter.addFragment(newInstance(1,id_parroquia));
        adapter.addFragment(newInstance(2,id_parroquia));
        adapter.addFragment(newInstance(3,id_parroquia));
        adapter.addFragment(newInstance(4,id_parroquia));
        adapter.addFragment(newInstance(5,id_parroquia));
        adapter.addFragment(newInstance(6,id_parroquia));
        adapter.addFragment(newInstance(7,id_parroquia));

        viewPager.setAdapter(adapter);
    }

    private HorarioFragment newInstance(int id_dia, int id_parroquia){
        HorarioFragment fragment = new HorarioFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id_dia",id_dia);
        bundle.putInt("id_parroquia",id_parroquia);
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

}
