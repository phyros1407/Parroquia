package com.vfconsulting.barbieri.parroquia;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vfconsulting.barbieri.parroquia.Adapters.TabAdapter;

import java.util.ArrayList;

import static com.vfconsulting.barbieri.parroquia.MisasFragment.listar_parroquia;
import static com.vfconsulting.barbieri.parroquia.MisasFragment.pAdapter;

public class MainActivity  extends FragmentActivity {

    TabLayout tabs;
    ViewPager viewPager;
    ArrayList<String> titulos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tab_padre);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        titulos.add("Mapa");
        titulos.add("Evento");
        titulos.add("Misas");
        //  AGREGANDO A LA TABs

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        tabTitle();
    }
    //CONSTRUIR PENSTAÑAS

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new MapaFragment());
        adapter.addFragment(new EventosFragment());
        adapter.addFragment(new MisasFragment());
        viewPager.setAdapter(adapter);
    }

    private void tabTitle(){
        for(int i=0;i<3;i++){
            tabs.getTabAt(i).setText(titulos.get(i));
        }
    }

    public interface ClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    void abrirHorario(View view){

        int posicion = (int) pAdapter.getPostion();

        Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
        Log.e("posicion ---->" , Integer.toString(posicion));
        int id_parroquia = listar_parroquia.get(posicion).getId_Parroquia();
        intent.putExtra("id_parroquia",1);

        startActivity(intent);
    }


}
