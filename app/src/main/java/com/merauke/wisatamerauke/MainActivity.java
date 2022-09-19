package com.merauke.wisatamerauke;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.merauke.wisatamerauke.modul.FasilitasResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.page.InfoActivity;
import com.merauke.wisatamerauke.page.detail.DetailActivity;
import com.merauke.wisatamerauke.page.map.MapsActivity;
import com.merauke.wisatamerauke.page.wisata.WisataAdapter;
import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private ProgressBar progressBar;
    private WisataAdapter adapter;
    private LinearLayout LayEmpty;
    private List<WisataResponse> DataArrayList =  new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.logo_app);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        rvData = findViewById(R.id.rv_recycler);
        progressBar = findViewById(R.id.progress_bar);
        LayEmpty=findViewById(R.id.lay_empty);
        LayEmpty.setVisibility(View.GONE);

        setData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.search_bar);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Nama Tempat Wisata");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return true;

    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<WisataResponse> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (WisataResponse item : DataArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.up_map) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.up_info) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void setData(){

        progressBar.setVisibility(View.VISIBLE);
        adapter = new WisataAdapter(this);
        WisataViewModel model = new ViewModelProvider(this).get(WisataViewModel.class);
        model.getWisata().observe(this, new Observer<List<WisataResponse>>() {
            @Override
            public void onChanged(List<WisataResponse> daftarResponses) {
                if (daftarResponses.size() != 0){
                    DataArrayList.addAll(daftarResponses);
                    //  Toast.makeText(getBaseContext(), "mencari data", Toast.LENGTH_SHORT).show();
                    adapter.setWisataList(DataArrayList);
                    adapter.notifyDataSetChanged();
                    LayEmpty.setVisibility(View.GONE);
                } else {
                    LayEmpty.setVisibility(View.VISIBLE);
//                    Toast.makeText(getBaseContext(), "Data Pada Tanggal ini Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setHasFixedSize(true);
        rvData.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}