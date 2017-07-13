package com.vfconsulting.barbieri.parroquia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.vfconsulting.barbieri.parroquia.Beans.EventoBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by barbb on 28/06/2017.
 */

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }


}
