package com.merauke.wisatamerauke.modul;

import java.util.ArrayList;

public class RootResponse {
    public String message;
    public ArrayList<WisataResponse> data_wisata;

    public RootResponse(String message, ArrayList<WisataResponse> data_wisata) {
        this.message = message;
        this.data_wisata = data_wisata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<WisataResponse> getData_wisata() {
        return data_wisata;
    }

    public void setData_wisata(ArrayList<WisataResponse> data_wisata) {
        this.data_wisata = data_wisata;
    }
}
