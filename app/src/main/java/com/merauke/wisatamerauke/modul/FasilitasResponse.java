package com.merauke.wisatamerauke.modul;

import java.util.Date;

public class FasilitasResponse {
    public int id;
    public int wisata_id;
    public String nama;
    public String jenis;
    public String tahun;
    public String foto;
    public Date created_at;
    public Date updated_at;

    public FasilitasResponse(int id, int wisata_id, String nama, String jenis, String tahun, String foto, Date created_at, Date updated_at) {
        this.id = id;
        this.wisata_id = wisata_id;
        this.nama = nama;
        this.jenis = jenis;
        this.tahun = tahun;
        this.foto = foto;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWisata_id() {
        return wisata_id;
    }

    public void setWisata_id(int wisata_id) {
        this.wisata_id = wisata_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}
