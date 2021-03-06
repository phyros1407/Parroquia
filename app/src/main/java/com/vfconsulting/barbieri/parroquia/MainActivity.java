package com.vfconsulting.barbieri.parroquia;

import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.vfconsulting.barbieri.parroquia.Adapters.TabAdapter;
import com.vfconsulting.barbieri.parroquia.Fragments.EventosFragment;
import com.vfconsulting.barbieri.parroquia.Fragments.MapaFragment;
import com.vfconsulting.barbieri.parroquia.Fragments.MisasFragment;

import java.util.ArrayList;
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

        titulos.add("Evento");
        titulos.add("Misas");
        titulos.add("Mapa");

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        tabTitle();

    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventosFragment());
        adapter.addFragment(new MisasFragment());
        adapter.addFragment(new MapaFragment());
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


}
