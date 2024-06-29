package com.zeedoys.hidupsehat;

import java.io.Serializable;

public class DataClass implements Serializable {

    private String dataimage;
    private String datanama;
    private String datadeskripsi;
    private String key;


    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getDataimage() {

        return dataimage;
    }
    public String getDatanama() {

        return datanama;
    }

    public String getDatadeskripsi() {

        return datadeskripsi;
    }

    public String getDataharga() {

        return dataharga;
    }

    private String dataharga;

    public DataClass(String dataimage, String datanama, String datadeskripsi, String dataharga) {
        this.dataimage = dataimage;
        this.datanama = datanama;
        this.datadeskripsi = datadeskripsi;
        this.dataharga = dataharga;
    }
    public DataClass(){
    }
}