package com.vfconsulting.barbieri.parroquia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by barbb on 21/06/2017.
 */

public class MapaFragment extends Fragment implements OnMapReadyCallback{

    private MapView mMapView;
    private GoogleMap mMap;
    private Bundle mBundle;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1 ;
    View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView= inflater.inflate(R.layout.mapa_fragment, container, false);


        MapsInitializer.initialize(getActivity());

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);
        onResume();
        mMapView.getMapAsync(this);




        return inflatedView;
    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //COMPROBANDO PERMISOS
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            double longitude = myLocation.getLongitude();
            double latitude = myLocation.getLatitude();

            LatLng me = new LatLng(latitude,longitude);

            CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(me,15);
            mMap.moveCamera(cameraPosition);
            mMap.animateCamera(cameraPosition);


        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setBuildingsEnabled(true);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);

        String url = "http://env-4981020.jelasticlw.com.br/serviciosparroquia/index.php/parroquia";

        JsonArrayRequest arrayreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("respuesta --->",response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                LatLng parroquia_lugar = new LatLng(jsonObject.getLong("latitud"), jsonObject.getLong("longitud"));
                                mMap.addMarker(new MarkerOptions().position(parroquia_lugar).title(jsonObject.getString("nombre")+" "+jsonObject.get("direccion")));

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
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

   /* public void irPosicion(double lat,double lon){


        LatLng me = new LatLng(lat,lon);
        Log.e("latitues",String.valueOf(lat));
        Log.e("latitues",String.valueOf(lon));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(me)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
*/
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
        mMapView.onDestroy();
        super.onDestroy();
    }



}



