package com.vfconsulting.barbieri.parroquia;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by barbb on 22/06/2017.
 */

public class DetalleActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_activity);

        int id = getIntent().getExtras().getInt("id_evento");

        getActionBar().setDisplayHomeAsUpEnabled(true);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });



    }



}
