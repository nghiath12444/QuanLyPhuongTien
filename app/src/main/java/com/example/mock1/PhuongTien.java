package com.example.mock1;

public class PhuongTien {
    int id;
    String ten, loai;
    float tien;

    public PhuongTien() {
    }

    public PhuongTien(int id,String ten, String loai, float tien) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.tien = tien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }
}
