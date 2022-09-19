package com.merauke.wisatamerauke.modul;

import java.util.Date;

public class EvenResponse {
    public int id;
    public int wisata_id;
    public String nama;
    public String jenis;
    public String tanggal;
    public String poster;
    public String keterangan;
    public Date created_at;
    public Date updated_at;

    public EvenResponse(int id, int wisata_id, String nama, String jenis, String tanggal, String poster, String keterangan, Date created_at, Date updated_at) {
        this.id = id;
        this.wisata_id = wisata_id;
        this.nama = nama;
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.poster = poster;
        this.keterangan = keterangan;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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
