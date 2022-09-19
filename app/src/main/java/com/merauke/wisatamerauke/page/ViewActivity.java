package com.merauke.wisatamerauke.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.merauke.wisatamerauke.BuildConfig;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.page.detail.DetailActivity;

public class ViewActivity extends AppCompatActivity {
    public static final String EXTRA_BAR = "extra_bar";
    public static final String EXTRA_FOTO = "extra_foto";
    public static final String EXTRA_NAMA = "extra_nama";
    public static final String EXTRA_KETERANGAN = "extra_keterangan";
    public static final String EXTRA_JENIS = "extra_jenis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ImageView img_foto = findViewById(R.id.img_wisata);
        TextView text_nama    = findViewById(R.id.txt_nama);
        TextView text_keterangan   = findViewById(R.id.txt_keterangan);
        TextView text_jenis   = findViewById(R.id.txt_jenis);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String foto = extras.getString(EXTRA_FOTO);
            final String nama = extras.getString(EXTRA_NAMA);
            final String jenis = extras.getString(EXTRA_JENIS);
            final String keterangan = extras.getString(EXTRA_KETERANGAN);
            final String bar_name = extras.getString(EXTRA_BAR);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(bar_name);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
            text_nama.setText(nama);
            text_jenis.setText(jenis);
            text_keterangan.setText(keterangan);
            Glide.with(ViewActivity.this)
                    .load(BuildConfig.API_URL + foto)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24))
                    .into(img_foto);
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}