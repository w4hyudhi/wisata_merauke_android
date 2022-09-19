package com.merauke.wisatamerauke.network;

import com.merauke.wisatamerauke.modul.RootResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("api/wisata/index")
    Call<RootResponse> getListWisata(

    );
}
