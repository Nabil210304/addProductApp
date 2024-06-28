package com.example.addproductapp.model;

public class Data {
    private String id, nama, jenis;
    private double harga;

    public Data(String id, String nama, String jenis, double harga) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
