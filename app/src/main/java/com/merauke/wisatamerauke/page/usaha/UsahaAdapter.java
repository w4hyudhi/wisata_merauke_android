package com.merauke.wisatamerauke.page.usaha;

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
import com.merauke.wisatamerauke.BuildConfig;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.modul.FasilitasResponse;
import com.merauke.wisatamerauke.modul.UsahaResponse;
import com.merauke.wisatamerauke.page.ViewActivity;
import com.merauke.wisatamerauke.page.fasilitas.FasilitasAdapter;
import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.util.ArrayList;
import java.util.List;

public class UsahaAdapter extends RecyclerView.Adapter<UsahaAdapter.UsahaViewHolder> {

    private final Activity activity;
    private final List<UsahaResponse> UsahaList = new ArrayList<>();
    private static final String TAG = UsahaAdapter.class.getSimpleName();

    public UsahaAdapter(Activity activity) {
        this.activity = activity;
    }


    public void setUsahaList(List<UsahaResponse> listData){
        if (listData != null){
            this.UsahaList.clear();
            this.UsahaList.addAll(listData);
        } else{
            return;
        }

    }

    @NonNull
    @Override
    public UsahaAdapter.UsahaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fasilitas, parent, false);
        return new UsahaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsahaAdapter.UsahaViewHolder holder, int position) {
        final UsahaResponse data = UsahaList.get(position);
        holder.txtData1.setText(data.getNama());
        holder.txtData2.setText(data.getJenis());
        holder.txtData3.setText(data.getKeterangan());
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.API_URL+ data.getFoto())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24))
                .into(holder.imgData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ViewActivity.class);

                intent.putExtra(ViewActivity.EXTRA_FOTO, data.getFoto());
                intent.putExtra(ViewActivity.EXTRA_NAMA, data.getNama());
                intent.putExtra(ViewActivity.EXTRA_JENIS, data.getJenis());
                intent.putExtra(ViewActivity.EXTRA_KETERANGAN, data.getKeterangan());
                intent.putExtra(ViewActivity.EXTRA_BAR, "Detail UMKM");
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return UsahaList.size();
    }

    public class UsahaViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgData;
        final TextView txtData1, txtData2, txtData3;
        public UsahaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgData = itemView.findViewById(R.id.img_view);
            txtData1 = itemView.findViewById(R.id.txt_data1);
            txtData2 = itemView.findViewById(R.id.txt_data2);
            txtData3 = itemView.findViewById(R.id.txt_data3);
        }
    }
}
