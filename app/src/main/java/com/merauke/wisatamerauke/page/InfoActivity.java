package com.merauke.wisatamerauke.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.merauke.wisatamerauke.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Info Aplikasi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}