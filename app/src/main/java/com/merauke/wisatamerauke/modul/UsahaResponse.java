package com.merauke.wisatamerauke.modul;

import java.util.Date;

public class UsahaResponse {
    public int id;
    public int wisata_id;
    public int pedagang_id;
    public String nama;
    public String jenis;
    public String keterangan;
    public String foto;
    public String tanggal;
    public Date created_at;
    public Date updated_at;

    public UsahaResponse(int id, int wisata_id, int pedagang_id, String nama, String jenis, String keterangan, String foto, String tanggal, Date created_at, Date updated_at) {
        this.id = id;
        this.wisata_id = wisata_id;
        this.pedagang_id = pedagang_id;
        this.nama = nama;
        this.jenis = jenis;
        this.keterangan = keterangan;
        this.foto = foto;
        this.tanggal = tanggal;
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

    public int getPedagang_id() {
        return pedagang_id;
    }

    public void setPedagang_id(int pedagang_id) {
        this.pedagang_id = pedagang_id;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
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
