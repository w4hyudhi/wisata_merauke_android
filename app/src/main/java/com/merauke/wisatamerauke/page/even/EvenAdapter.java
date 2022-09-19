package com.merauke.wisatamerauke.page.even;


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
import com.merauke.wisatamerauke.BuildConfig;
import com.merauke.wisatamerauke.R;
import com.merauke.wisatamerauke.modul.EvenResponse;
import com.merauke.wisatamerauke.page.ViewActivity;

import com.merauke.wisatamerauke.page.wisata.WisataViewModel;

import java.util.ArrayList;
import java.util.List;

public class EvenAdapter extends RecyclerView.Adapter<EvenAdapter.EvenViewHolder> {

    private final Activity activity;
    private final List<EvenResponse> EvenList = new ArrayList<>();
    private static final String TAG = WisataViewModel.class.getSimpleName();

    public EvenAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setEvenList(List<EvenResponse> listData){
        if (listData != null){
            this.EvenList.clear();
            this.EvenList.addAll(listData);



        } else{
            return;
        }

    }

    @NonNull
    @Override
    public EvenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fasilitas, parent, false);
        return new EvenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvenViewHolder holder, int position) {
        Log.d(TAG, "onResponse: data" + EvenList.size());

        final EvenResponse data = EvenList.get(position);
        holder.txtData1.setText(data.getNama());
        holder.txtData2.setText(data.getJenis());
        holder.txtData3.setText(data.getTanggal());
        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.API_URL+ data.getPoster())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24))
                .into(holder.imgData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ViewActivity.class);

                intent.putExtra(ViewActivity.EXTRA_FOTO, data.getPoster());
                intent.putExtra(ViewActivity.EXTRA_NAMA, data.getNama());
                intent.putExtra(ViewActivity.EXTRA_JENIS, data.getTanggal());
                intent.putExtra(ViewActivity.EXTRA_KETERANGAN, data.getKeterangan());
                intent.putExtra(ViewActivity.EXTRA_BAR, "Detail Even");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return EvenList.size();
    }
    public class EvenViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgData;
        final TextView txtData1, txtData2, txtData3;
        public EvenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgData = itemView.findViewById(R.id.img_view);
            txtData1 = itemView.findViewById(R.id.txt_data1);
            txtData2 = itemView.findViewById(R.id.txt_data2);
            txtData3 = itemView.findViewById(R.id.txt_data3);
        }
    }
}
