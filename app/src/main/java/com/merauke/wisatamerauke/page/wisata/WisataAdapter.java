package com.merauke.wisatamerauke.page.wisata;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.merauke.wisatamerauke.BuildConfig;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.modul.RootResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.page.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.WisataViewHolder> {

    private final Activity activity;
    private List<WisataResponse> WisataList = new ArrayList<>();


    public void setWisataList(List<WisataResponse> listWisata){
        if (listWisata != null){
            this.WisataList.clear();
            this.WisataList.addAll(listWisata);
        } else{
            return;
        }

    }

    public WisataAdapter(Activity activity) {
        this.activity = activity;
    }

    public void filterList(ArrayList<WisataResponse> filterllist) {
        WisataList = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WisataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisata, parent, false);
        return new WisataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WisataViewHolder holder, int position) {
        final WisataResponse wisata = WisataList.get(position);
        holder.txtNama.setText(wisata.nama);
        holder.txtAlamat.setText(wisata.alamat);
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.API_URL+ wisata.getFoto())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24))
                .into(holder.imgWisata);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(activity, DetailActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson( wisata);
                intent.putExtra(DetailActivity.EXTRA_ID, myJson);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return WisataList.size();
    }



    public class WisataViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgWisata;
        final TextView txtNama, txtAlamat;
        public WisataViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWisata = itemView.findViewById(R.id.img_wisata);
            txtNama = itemView.findViewById(R.id.txt_nama);
            txtAlamat = itemView.findViewById(R.id.txt_alamat);
        }
    }
}
