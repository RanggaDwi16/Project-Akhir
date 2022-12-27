package com.example.tugasakhiruas;

public class Model {
    String id, image, nama, tanggal, addTimeStamp, updatetimeStamp;

    // Constructor
    public Model(String id, String image, String nama, String tanggal, String addTimeStamp, String updatetimeStamp) {
        this.id = id;
        this.image = image;
        this.nama = nama;
        this.tanggal = tanggal;
        this.addTimeStamp = addTimeStamp;
        this.updatetimeStamp = updatetimeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getAddTimeStamp() {
        return addTimeStamp;
    }

    public void setAddTimeStamp(String addTimeStamp) {
        this.addTimeStamp = addTimeStamp;
    }

    public String getUpdatetimeStamp() {
        return updatetimeStamp;
    }

    public void setUpdatetimeStamp(String updatetimeStamp) {
        this.updatetimeStamp = updatetimeStamp;
    }
}
