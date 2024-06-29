package com.zeedoys.hidupsehat;

public class Transaksi {
    private String nama;
    private String total;
    // Constructors, getters, and setters

    public Transaksi() {
    }

    public Transaksi(String nama, String total) {
        this.nama = nama;
        this.total = total;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
