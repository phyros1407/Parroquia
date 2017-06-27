package com.vfconsulting.barbieri.parroquia.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.vfconsulting.barbieri.parroquia.MainActivity;
import com.vfconsulting.barbieri.parroquia.Support.MySingleton;
import com.vfconsulting.barbieri.parroquia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by barbb on 21/06/2017.
 */

public class MapaFragment extends Fragment {

    private MapView mMapView;
    private static GoogleMap mMap;
    private Bundle mBundle;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    View inflatedView;

    private static final String TAG = MapaFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.mapa_fragment, container, false);

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //requestPermissions();
                    return;
                }

                // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                try {

                    mMap = googleMap;
                    MapsInitializer.initialize(getActivity());



                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.setBuildingsEnabled(true);
                    UiSettings uiSettings = mMap.getUiSettings();
                    uiSettings.setZoomControlsEnabled(true);
                    uiSettings.setZoomGesturesEnabled(true);
                    uiSettings.setMyLocationButtonEnabled(true);
                    setUpMap();

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
/*
                                    double longitude = location.getLatitude();
                                    double latitude = location.getLongitude();

                                    Log.e("longitud",String.valueOf(longitude));
                                    Log.e("latitud",String.valueOf(latitude));

                                    LatLng me = new LatLng(longitude,latitude);

                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(me)      // Sets the center of the map to Mountain View
                                            .zoom(15)                   // Sets the zoom
                                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                            .build();                   // Creates a CameraPosition from the builder

                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

*/

                                    if (location != null) {

                                        Snackbar.make(inflatedView, getString(R.string.no_location_detected), Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });



/*


                    mFusedLocationClient.getLastLocation()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        mLastLocation = task.getResult();

                                        double longitude = mLastLocation.getLatitude();
                                        double latitude = mLastLocation.getLongitude();

                                        Log.e("longitud",String.valueOf(longitude));
                                        Log.e("latitud",String.valueOf(latitude));

                                        LatLng me = new LatLng(longitude,latitude);

                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(me)      // Sets the center of the map to Mountain View
                                                .zoom(15)                   // Sets the zoom
                                                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                                .build();                   // Creates a CameraPosition from the builder

                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    } else {
                                        Log.w(TAG, "getLastLocation:exception", task.getException());
                                        Snackbar.make(inflatedView, getString(R.string.no_location_detected), Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });

*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return inflatedView;
    }


    public void irPosicion(double lat, double lon) {

        TabLayout tabs = (TabLayout)inflatedView.findViewById(R.id.tab_padre);

        tabs.getTabAt(0).select();

        LatLng me = new LatLng(lat, lon);
        Log.e("latitues", String.valueOf(lat));
        Log.e("latitues", String.valueOf(lon));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(me)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    /*
        @Override
        public void onStart() {
            super.onStart();

            if (!checkPermissions()) {
                requestPermissions();
            } else {
                getLastLocation();
            }
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

/*
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            double longitude = mLastLocation.getLatitude();
                            double latitude = mLastLocation.getLongitude();

                            LatLng me = new LatLng(latitude,longitude);

                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(me)      // Sets the center of the map to Mountain View
                                    .zoom(15)                   // Sets the zoom
                                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                                    .build();                   // Creates a CameraPosition from the builder

                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    private void showSnackbar(final String text) {

        if (inflatedView != null) {
            Snackbar.make(inflatedView, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }


    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(inflatedView.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

}


*/
