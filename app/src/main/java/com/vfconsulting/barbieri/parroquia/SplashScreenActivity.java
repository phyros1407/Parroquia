package com.vfconsulting.barbieri.parroquia;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by barbb on 28/06/2017.
 */

public class SplashScreenActivity extends Activity implements PermissionCallback, ErrorCallback {

    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };


        reqPermission();

        //checkLocationPermission();

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;



    private void reqPermission() {
        new AskPermission.Builder(this).setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .setCallback(this)
                .setErrorCallback(this)
                .request(MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Timer timer = new Timer();
        timer.schedule(task, 2000);
        Toast.makeText(this, "Permisos Concedidos", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode) {
        Toast.makeText(this, "Permisos Rechazados", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowRationalDialog(final PermissionInterface permissionInterface, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se necesitan los permisos requeridos para esta aplicación");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionInterface.onDialogShown();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    public void onShowSettings(final PermissionInterface permissionInterface, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se necesitan los permisos para esta aplicación ¿Desea abrir el menú de configuraciones?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionInterface.onSettingsShown();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

/*
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("mensaje","Entra aca 1");

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.e("mensaje","Entra aca 2");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permisos Requeridos")
                        .setMessage("Otorguele permisos de ubicación a la aplicacion Familia Don de Dios")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                Log.e("mensaje","Entra aca 3");
                                ActivityCompat.requestPermissions(SplashScreenActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


                                Timer timer = new Timer();
                                timer.schedule(task, 3000);

                            }
                        })
                        .create()
                        .show();

                        if (ActivityCompat.checkSelfPermission(this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            Log.e("mensaje","Entra aca 4");
                            Timer timer = new Timer();
                            timer.schedule(task, 3000);

                        }


            } else {
                // No explanation needed, we can request the permission.

                    Log.e("mensaje","Entra aca 5");

                    Timer timer = new Timer();
                    timer.schedule(task, 3000);


            }
            return false;
        } else {

            Log.e("mensaje","Entra aca 6");

            Timer timer = new Timer();
            timer.schedule(task, 3000);
            return true;
        }
    }
*/
}
