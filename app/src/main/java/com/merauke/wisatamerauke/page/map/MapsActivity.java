package com.merauke.wisatamerauke.page.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.databinding.ActivityMapsBinding;
import com.merauke.wisatamerauke.modul.UsahaResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.page.detail.DetailActivity;
import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ViewGroup transitionsContainer;
    private Double LatTujuan,LongTujuan;
    private String Map_Jarak;
    private int Map_Id;
    private ActivityMapsBinding binding;
    private Button btn_direct,btn_detail;
    private MaterialButton btnType;

    ImageButton  typDefault,typSatelite,typTerrain;


    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Peta Wisata");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        transitionsContainer = (ViewGroup)findViewById(R.id.transitions_container);
        btn_direct = transitionsContainer.findViewById(R.id.btn_direct);
        btn_detail = transitionsContainer.findViewById(R.id.btn_detail);
        btnType = findViewById(R.id.btn_map);
        btn_direct.setVisibility(View.GONE);
        btn_detail.setVisibility(View.GONE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        fetchLocation();
        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupGoogleMapScreenSettings(googleMap);
        initMarker();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
                else {
                    Toast.makeText(MapsActivity.this, "Mohon aktifkan gps anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    private void initMarker() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Menampilkan data marker ..");
        dialog.show();

        WisataViewModel model = new ViewModelProvider(this).get(WisataViewModel.class);
           model.getWisata().observe(this, new Observer<List<WisataResponse>>() {
               @Override
               public void onChanged(List<WisataResponse> wisataResponses) {
                   dialog.dismiss();
                   mMap.clear();

                   for (int i = 0; i < wisataResponses.size(); i++) {
                       final float[] distance = new float[wisataResponses.size()];

                       Location.distanceBetween(wisataResponses.get(i).getLatitude(), wisataResponses.get(i).getLongitude(),
                               currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
                       LatLng location = new LatLng(wisataResponses.get(i).getLatitude(), wisataResponses.get(i).getLongitude());
                       String kondisi = wisataResponses.get(i).getJadwal();
                       //tambahkan markernya
                       mMap.addMarker(new MarkerOptions().position(location).title(wisataResponses.get(i).getNama()).snippet(Jarak(distance[0]))).setTag(i);

                   }
                   CameraPosition googlePlex = CameraPosition.builder()
                           .target(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()))
                           .zoom(12)
                           .bearing(0)
                           //.tilt(45)
                           .build();

                   mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex),1000, null);

                   mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                       boolean visible;
                       @Override
                       public boolean onMarkerClick(Marker marker) {
                           LatTujuan = marker.getPosition().latitude;
                           LongTujuan = marker.getPosition().longitude;
                           Map_Jarak = marker.getSnippet();
                           Map_Id = Integer.parseInt(marker.getTag().toString());

                           TransitionManager.beginDelayedTransition(transitionsContainer);
                           btn_direct.setVisibility(View.VISIBLE);
                           btn_detail.setVisibility(View.VISIBLE);
                           return false;
                       }
                   });

                   mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                       @Override
                       public void onMapClick(LatLng latLng) {
                           TransitionManager.beginDelayedTransition(transitionsContainer);
                           btn_direct.setVisibility(View.GONE);
                           btn_detail.setVisibility(View.GONE);

                       }
                   });

                   btn_detail.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           Intent inten = new Intent(MapsActivity.this, DetailActivity.class);
                           Gson gson = new Gson();
                           String myJson = gson.toJson( wisataResponses.get(Map_Id));
                           inten.putExtra(DetailActivity.EXTRA_ID, myJson);
                           startActivity(inten);
                       }
                   });

                   btn_direct.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           Uri navigationIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + LatTujuan +"," + LongTujuan);//creating intent with latlng
                           Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                           mapIntent.setPackage("com.google.android.apps.maps");
                           startActivity(mapIntent);
                       }
                   });
               }


           });


        }
    static String Jarak(float value){
        NumberFormat formatterKm = new DecimalFormat("#0.00");
        NumberFormat formatterM = new DecimalFormat("#0");
        String jarak;
        if (value <= 1000){
            jarak =formatterM.format(value)+" meter" ;
        } else {
            jarak =formatterKm.format(value/1000)+" kilometer" ;
        }
        return jarak;
    }


    private void setupGoogleMapScreenSettings(GoogleMap mMap) {

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);
    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this,R.style.AlertDialogCustom);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_map, null);
        alertDialog.setView(customLayout);
        AlertDialog alert = alertDialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        typDefault    = customLayout.findViewById(R.id.map_type_default);
        typSatelite    = customLayout.findViewById(R.id.map_type_satelite);
        typTerrain    = customLayout.findViewById(R.id.map_type_terrain);


        typDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                alert.dismiss();
            }
        });
        typSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                alert.dismiss();
            }
        });
        typTerrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                alert.dismiss();
            }
        });



        alert.setCanceledOnTouchOutside(true);

        alert.show();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}


