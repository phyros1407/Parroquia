package com.vfconsulting.barbieri.parroquia.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;
import com.vfconsulting.barbieri.parroquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by barbb on 21/06/2017.
 */

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private MapView mMapView;
    private static GoogleMap mMap;
    private Bundle mBundle;
    private Marker marcador;
    private double lat;
    private double log;
    View inflatedView;

    private static final String TAG = MapaFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.mapa_fragment, container, false);

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);


        mMapView.getMapAsync(this);


        return inflatedView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        miUbicacion();

    }

    private void agregarMarcador(double lat, double log) {

        LatLng cordenadas = new LatLng(lat, log);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(cordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }

        if(!(mMap.isMyLocationEnabled())){
             marcador = mMap.addMarker(new MarkerOptions()
                .position(cordenadas).title("Mi posición").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        }
        mMap.animateCamera(miUbicacion);
    }


    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            log = location.getLongitude();
            agregarMarcador(lat, log);
        }

    }


    LocationListener loListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_PERMISSIONS_REQUEST_CODE);
        }

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 15000, 0, loListener);

        mMap.setMyLocationEnabled(true);
    }



    @Override
    public void onStart() {

        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    private void setUpMapIfNeeded(View inflatedView) {
        if (mMap != null) {
            setUpMap();
        }
    }

    private void setUpMap() {

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/parroquia";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                LatLng parroquia_lugar = new LatLng(jsonObject.getLong("latitud"), jsonObject.getLong("longitud"));
                                mMap.addMarker(new MarkerOptions().position(parroquia_lugar).title(jsonObject.getString("nombre") + " " + jsonObject.get("direccion")));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(inflatedView, "OCCURRIÓ UN PROBLEMA, INTENTE MAS TARDE", Snackbar.LENGTH_LONG).show();
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

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
