package com.vfconsulting.barbieri.parroquia.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import android.location.LocationListener;
import android.widget.EditText;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
    private UiSettings mUiSettings;
    Location mi_localizacion;
    private List<ParroquiaBean> parroquias = new ArrayList<>();
    private List<Polyline> direcciones = new ArrayList<>();
    private List<Double>latitudesI = new ArrayList<>();
    private List<Double>longitudesI = new ArrayList<>();
    private List<Double>latitudesF = new ArrayList<>();
    private List<Double>longitudesF = new ArrayList<>();

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

        return inflatedView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        miUbicacion();
        mUiSettings=mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                borrarLineas();

                Log.e("POSICION MARCADOR -->",marker.getPosition().toString());
                LatLng posicion = marker.getPosition();
                CameraUpdate destino = CameraUpdateFactory.newLatLngZoom(posicion, 16);
                obtenerCoordenadas(marker.getPosition());
                marker.showInfoWindow();
                mMap.animateCamera(destino);
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

    private void borrarLineas(){

        for(int i=0;i<direcciones.size();i++){
            direcciones.get(i).remove();
        }

        direcciones.clear();
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            log = location.getLongitude();
            agregarMarcador(lat, log);
        }

    }

    private void crearLinea(GoogleMap mapa, LatLng origen, LatLng destino){

        Log.e("ORIGEN ",origen.toString());
        Log.e("DESTINO ",destino.toString());

        Polyline linea = mapa.addPolyline(new PolylineOptions()
            .add(origen, destino)
            .width(5)
            .color(Color.RED));

        direcciones.add(linea);
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
        mi_localizacion= locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        actualizarUbicacion(mi_localizacion);
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

        String url = "http://env-1201049.jelasticlw.com.br/serviciosparroquia/index.php/parroquia";

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
                                parroquia.setLongitud(jsonObject.getDouble("longitud"));
                                parroquia.setLatitud(jsonObject.getDouble("latitud"));

                                LatLng parroquia_lugar = new LatLng(jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"));
                                mMap.addMarker(new MarkerOptions()
                                        .position(parroquia_lugar)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_iglesia_1))
                                        .snippet(jsonObject.getString("nombre")));


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

    private void obtenerCoordenadas(LatLng destino) {

        String ori = mi_localizacion.getLatitude()+","+mi_localizacion.getLongitude();

        //String ori = "-12.085414,-77.086199";
        Log.e("destino ---> ",destino.toString());
        String des = destino.latitude+","+destino.longitude;

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+ ori +"&destination=" + des + "&key=AIzaSyBcCy8g5hBDmw5bvA4VvUSfVwGSS2cZ1FA";

       // String url= "https://maps.googleapis.com/maps/api/directions/json?origin=av+la+marina+1100+san+miguel&destination=av+varela+miraflores&key=AIzaSyBcCy8g5hBDmw5bvA4VvUSfVwGSS2cZ1FA";


        Log.e("url ----> ",url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());


                        try {
                            Log.e("respuesta --->", String.valueOf(response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps")));


                            latitudesF.clear();
                            latitudesI.clear();
                            longitudesI.clear();
                            longitudesF.clear();

                            JSONArray arreglo = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

                            for (int i = 0; i < arreglo.length(); i++) {

                                JSONObject jsonObject =arreglo.getJSONObject(i);

                                Log.e("objetvo de refrrencia ----->" ,jsonObject.toString());

                                latitudesI.add(jsonObject.getJSONObject("start_location").getDouble("lat"));
                                Log.d("latitudI", String.valueOf(jsonObject.getJSONObject("start_location").getDouble("lat")));
                                longitudesI.add(jsonObject.getJSONObject("start_location").getDouble("lng"));
                                Log.d("longitudI", String.valueOf(jsonObject.getJSONObject("start_location").getDouble("lng")));
                                latitudesF.add(jsonObject.getJSONObject("end_location").getDouble("lat"));
                                Log.d("latitudF", String.valueOf(jsonObject.getJSONObject("end_location").getDouble("lat")));
                                longitudesF.add(jsonObject.getJSONObject("end_location").getDouble("lng"));
                                Log.d("longitudF", String.valueOf(jsonObject.getJSONObject("end_location").getDouble("lng")));

                                crearLinea(mMap,new LatLng(latitudesI.get(i),longitudesI.get(i)),new LatLng(latitudesF.get(i),longitudesF.get(i)));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        MySingleton.getInstance(getContext()).addToRequestQueue(getRequest);
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
