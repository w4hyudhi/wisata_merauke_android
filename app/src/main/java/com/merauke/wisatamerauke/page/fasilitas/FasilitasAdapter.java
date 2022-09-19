package com.merauke.wisatamerauke.page.fasilitas;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import com.merauke.wisatamerauke.modul.FasilitasResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.page.ViewActivity;
import com.merauke.wisatamerauke.page.detail.DetailActivity;
import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.util.ArrayList;
import java.util.List;

public class FasilitasAdapter extends RecyclerView.Adapter<FasilitasAdapter.FasilitasViewHolder> {

    private final Activity activity;
    private final List<FasilitasResponse> FasilitasList = new ArrayList<>();
    private static final String TAG = WisataViewModel.class.getSimpleName();

    public FasilitasAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setFasilitasList(List<FasilitasResponse> listData){
        if (listData != null){
            this.FasilitasList.clear();
            this.FasilitasList.addAll(listData);



        } else{
            return;
        }

    }

    @NonNull
    @Override
    public FasilitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fasilitas, parent, false);
        return new FasilitasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasViewHolder holder, int position) {
        Log.d(TAG, "onResponse: data" + FasilitasList.size());

        final FasilitasResponse data = FasilitasList.get(position);
        holder.txtData1.setText(data.getNama());
        holder.txtData2.setText(data.getJenis());
        holder.txtData3.setText(data.getTahun());
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
                intent.putExtra(ViewActivity.EXTRA_KETERANGAN, data.getTahun());
                intent.putExtra(ViewActivity.EXTRA_BAR, "Detail Fasilitas");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FasilitasList.size();
    }
    public class FasilitasViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgData;
        final TextView txtData1, txtData2, txtData3;
        public FasilitasViewHolder(@NonNull View itemView) {
            super(itemView);
            imgData = itemView.findViewById(R.id.img_view);
            txtData1 = itemView.findViewById(R.id.txt_data1);
            txtData2 = itemView.findViewById(R.id.txt_data2);
            txtData3 = itemView.findViewById(R.id.txt_data3);
        }
    }
}
