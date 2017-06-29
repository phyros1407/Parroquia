package com.vfconsulting.barbieri.parroquia.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import android.location.LocationListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vfconsulting.barbieri.parroquia.Beans.ParroquiaBean;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;
import com.vfconsulting.barbieri.parroquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    EditText buscador;
    ListView lv;
    int textLen = 0;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private List<ParroquiaBean> parroquias = new ArrayList<>();

    private static final String TAG = MapaFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.mapa_fragment, container, false);

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);
        setUpMap();

        mMapView.getMapAsync(this);

        buscador = (EditText) inflatedView.findViewById(R.id.masked);
        lv = (ListView) inflatedView.findViewById(R.id.ListView01);

        lv.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1));

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lv.setAdapter(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textLen = buscador.getText().length();
                array_sort.clear();

                for (int i = 0; i < parroquias.size(); i++) {
                    if (textLen <= parroquias.get(i).getNombre().length()) {
                        if (buscador.getText().toString().equalsIgnoreCase((String) parroquias.get(i).getNombre().subSequence(0, textLen))) {
                            array_sort.add(parroquias.get(i).getNombre());
                        }
                    }
                }

                lv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array_sort));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return inflatedView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        miUbicacion();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng posicion = marker.getPosition();
                CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(posicion, 16);
                mMap.animateCamera(miUbicacion);
                return true;
            }
        });


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

                            parroquias.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                ParroquiaBean parroquia = new ParroquiaBean();
                                parroquia.setNombre(jsonObject.getString("nombre"));
                                parroquia.setDireccion(jsonObject.getString("direccion"));
                                parroquia.setLongitud(jsonObject.getLong("longitud"));
                                parroquia.setLatitud(jsonObject.getLong("latitud"));

                                LatLng parroquia_lugar = new LatLng(jsonObject.getLong("latitud"), jsonObject.getLong("longitud"));
                                mMap.addMarker(new MarkerOptions()
                                        .position(parroquia_lugar)
                                        .title(jsonObject.getString("nombre") + " " + jsonObject.get("direccion"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_iglesia_mini)));

                                parroquias.add(parroquia);

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
