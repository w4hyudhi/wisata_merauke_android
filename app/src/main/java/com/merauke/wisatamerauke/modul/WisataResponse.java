package com.merauke.wisatamerauke.modul;

import java.util.ArrayList;
import java.util.Date;

public class WisataResponse {
    public int id;
    public String nama;
    public String alamat;
    public String foto;
    public String keterangan;
    public String jadwal;
    public String harga;
    public double latitude;
    public double longitude;
    public Date created_at;
    public Date updated_at;
    public ArrayList<FasilitasResponse> fasilitas;
    public ArrayList<UsahaResponse> usaha;
    public ArrayList<EvenResponse> even;

    public WisataResponse(int id, String nama, String alamat, String foto, String keterangan, String jadwal, String harga, double latitude, double longitude, Date created_at, Date updated_at, ArrayList<FasilitasResponse> fasilitas, ArrayList<UsahaResponse> usaha,ArrayList<EvenResponse> even) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.foto = foto;
        this.keterangan = keterangan;
        this.jadwal = jadwal;
        this.harga = harga;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.fasilitas = fasilitas;
        this.usaha = usaha;
        this.even = even;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<FasilitasResponse> getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(ArrayList<FasilitasResponse> fasilitas) {
        this.fasilitas = fasilitas;
    }

    public ArrayList<UsahaResponse> getUsaha() {
        return usaha;
    }

    public void setUsaha(ArrayList<UsahaResponse> usaha) {
        this.usaha = usaha;
    }


    public ArrayList<EvenResponse> getEven() {
        return even;
    }

    public void setEven(ArrayList<EvenResponse> even) {
        this.even = even;
    }
}
