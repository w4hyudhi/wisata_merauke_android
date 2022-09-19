package com.merauke.wisatamerauke.page.wisata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.merauke.wisatamerauke.modul.RootResponse;
import com.merauke.wisatamerauke.modul.WisataResponse;
import com.merauke.wisatamerauke.network.ApiClient;
import com.merauke.wisatamerauke.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataViewModel extends ViewModel {
    private ApiInterface apiInterface;
    private MutableLiveData<String> mText;
    private static final String TAG = WisataViewModel.class.getSimpleName();
    private MutableLiveData<List<WisataResponse>> resultData;

    public WisataViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
    public LiveData<List<WisataResponse>> getWisata() {
        //if the list is null
        if (resultData == null) {
            resultData = new MutableLiveData<List<WisataResponse>>();

            loadWisata();
        }
        return resultData;
    }

    private void loadWisata(){

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<RootResponse> call = api.getListWisata();
        call.enqueue(new Callback<RootResponse>() {
            @Override
            public void onResponse(Call<RootResponse> call, Response<RootResponse> response) {
                Log.d(TAG, "onResponse: berhasil" + response.body());
                resultData.setValue(response.body().getData_wisata());
            }

            @Override
            public void onFailure(Call<RootResponse> call, Throwable t) {
                Log.d(TAG, "onResponse: gagal" + t);
            }
        });
    }
    public LiveData<String> getText() {
        return mText;
    }

}
