package com.merauke.wisatamerauke.page.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.merauke.wisatamerauke.BuildConfig;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.databinding.ActivityMapsBinding;
import com.merauke.wisatamerauke.modul.EvenResponse;
import com.merauke.wisatamerauke.modul.FasilitasResponse;
import com.merauke.wisatamerauke.modul.UsahaResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.page.even.EvenAdapter;
import com.merauke.wisatamerauke.page.fasilitas.FasilitasAdapter;
import com.merauke.wisatamerauke.page.map.MapsActivity;
import com.merauke.wisatamerauke.page.usaha.UsahaAdapter;
import com.merauke.wisatamerauke.page.wisata.WisataAdapter;
import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_ID = "extra_id";
    private RecyclerView rvData,rvData2,rvData3;
    private FasilitasAdapter adapterFasilitas;
    private UsahaAdapter adapterUsaha;
    private EvenAdapter adapterEven;
    private ImageView imageWisata;
    private TextView Txt_Nama,Txt_Alamat,Txt_Jadwal,Txt_Harga,Txt_Overview;
    LinearLayout Txt_NotFound3, Txt_NotFound1,Txt_NotFound2;
    List<FasilitasResponse> fasilitasResponses  = new ArrayList<>();
    List<UsahaResponse> usahaResponses  = new ArrayList<>();
    List<EvenResponse> evenResponses  = new ArrayList<>();
    private static final String TAG = WisataViewModel.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng wisata_map;
    String nama_wisata;
    private static final int REQUEST_CODE = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Txt_Nama = findViewById(R.id.txt_nama);
        Txt_Alamat = findViewById(R.id.txt_alamat);
        Txt_Jadwal = findViewById(R.id.txt_jadwal);
        Txt_Harga = findViewById(R.id.txt_harga);
        Txt_Overview = findViewById(R.id.txt_overview);
        rvData = findViewById(R.id.rv_recycler);
        rvData2 = findViewById(R.id.rv_recycler2);
        rvData3 = findViewById(R.id.rv_recycler3);
        imageWisata= findViewById(R.id.img_wisata);
        Txt_NotFound1 = findViewById(R.id.txt_notfound1);
        Txt_NotFound2 = findViewById(R.id.txt_notfound2);
        Txt_NotFound3 = findViewById(R.id.txt_notfound3);
        Txt_NotFound1.setVisibility(View.GONE);
        Txt_NotFound2.setVisibility(View.GONE);
        Txt_NotFound3.setVisibility(View.GONE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Gson gson = new Gson();
            WisataResponse data = gson.fromJson(getIntent().getStringExtra(EXTRA_ID), WisataResponse.class);
            Txt_Nama.setText(data.getNama());
            Txt_Alamat.setText(data.getAlamat());
            Txt_Jadwal.setText(data.getJadwal());
            Txt_Harga.setText(data.getHarga());
            Txt_Overview.setText(data.getKeterangan());
            fasilitasResponses.addAll(data.getFasilitas());
            setActionBarTitle(data.getNama());
            usahaResponses.addAll(data.getUsaha());
            evenResponses.addAll(data.getEven());
            nama_wisata = data.getNama();

            Glide.with(DetailActivity.this)
                    .load(BuildConfig.API_URL+ data.getFoto())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24))
                    .into(imageWisata);
            wisata_map = new LatLng(data.getLatitude(), data.getLongitude());
            setDataFasilitas();
            setDataUsaha();
            setDataEven();

            mapFragment.getMapAsync(this);

        }


    }


    private void setDataFasilitas(){


        adapterFasilitas = new FasilitasAdapter(this);
        if (fasilitasResponses.size() != 0){
            adapterFasilitas.setFasilitasList(fasilitasResponses);
            adapterFasilitas.notifyDataSetChanged();
            Txt_NotFound1.setVisibility(View.VISIBLE);
        }else{
            Txt_NotFound1.setVisibility(View.GONE);
        }
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setHasFixedSize(true);
        rvData.setAdapter(adapterFasilitas);


    }
    private void setDataEven(){


        adapterEven = new EvenAdapter(this);
        if (evenResponses.size() != 0){
            Txt_NotFound3.setVisibility(View.VISIBLE);
            adapterEven.setEvenList(evenResponses);
            adapterEven.notifyDataSetChanged();

        }else{
            Txt_NotFound3.setVisibility(View.GONE);
        }
        rvData3.setLayoutManager(new LinearLayoutManager(this));
        rvData3.setHasFixedSize(true);
        rvData3.setAdapter(adapterEven);


    }


    private void setDataUsaha(){


        adapterUsaha = new UsahaAdapter(this);
        if (usahaResponses.size() != 0){
            adapterUsaha.setUsahaList(usahaResponses);
            adapterUsaha.notifyDataSetChanged();
            Txt_NotFound2.setVisibility(View.VISIBLE);
        }else{
            Txt_NotFound2.setVisibility(View.GONE);
        }
        rvData2.setLayoutManager(new LinearLayoutManager(this));
        rvData2.setHasFixedSize(true);
        rvData2.setAdapter(adapterUsaha);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setDataFasilitas();
        setDataUsaha();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(wisata_map).title(nama_wisata));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wisata_map));


        setupGoogleMapScreenSettings(googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    fetchLocation();
                }
                break;
        }
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {

        if (ActivityCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wisata_map,12));

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
    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

//    private void showAlertDetail(String img,String nama,String jenis,String keterangan) {
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this,R.style.AlertDialogCustom);
//        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_detail, null);
//        alertDialog.setView(customLayout);
//        AlertDialog alert = alertDialog.create();
//        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        ImageView foto = customLayout.findViewById(R.id.img_wisata);
//        TextView text_nama    = customLayout.findViewById(R.id.txt_nama);
//        TextView text_keterangan   = customLayout.findViewById(R.id.txt_keterangan);
//        TextView text_jenis   = customLayout.findViewById(R.id.txt_jenis);
//
//        text_nama.setText(nama);
//        text_jenis.setText(jenis);
//        text_keterangan.setText(keterangan);
//        Glide.with(DetailActivity.this)
//                .load(BuildConfig.API_URL+ foto)
//                .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24))
//                .into(imageWisata);
//
//        alert.setCanceledOnTouchOutside(true);
//
//        alert.show();
//
//    }
}