package com.vfconsulting.barbieri.parroquia;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by barbb on 22/06/2017.
 */

public class DetalleActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_activity);

        int id = getIntent().getExtras().getInt("id_evento");



    }

}
