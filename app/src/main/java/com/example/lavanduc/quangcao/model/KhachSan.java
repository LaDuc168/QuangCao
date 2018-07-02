package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class KhachSan {
    private String maks;
    private String tenks;
    private int danhgia;
    private String binhluan;
    private String diadiem;
    private String dichvucungcap;
    private byte[] anh;

    public KhachSan(String tenks, int danhgia, String diadiem, String dichvucungcap, byte[] anh) {
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.diadiem = diadiem;
        this.dichvucungcap = dichvucungcap;
        this.anh = anh;
    }

    public KhachSan(String maks, String tenks, String binhluan, String diadiem, String dichvucungcap, byte[] anh) {
        this.maks = maks;
        this.tenks = tenks;
        this.binhluan = binhluan;
        this.diadiem = diadiem;
        this.dichvucungcap = dichvucungcap;
        this.anh = anh;
    }

    public KhachSan(String tenks, int danhgia, String binhluan, String diadiem, String dichvucungcap, byte[] anh) {
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.binhluan = binhluan;
        this.diadiem = diadiem;
        this.dichvucungcap = dichvucungcap;
        this.anh = anh;
    }

    public KhachSan(String maks, String tenks, int danhgia, byte[] anh) {
        this.maks = maks;
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.anh = anh;
    }

    public KhachSan(String tenks, int danhgia, String diadiem, byte[] anh) {
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.diadiem = diadiem;
        this.anh = anh;
    }

    public KhachSan(String tenks, int danhgia, byte[] anh) {
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.anh = anh;
    }

    public String getMaks() {
        return maks;
    }

    public void setMaks(String maks) {
        this.maks = maks;
    }

    public String getTenks() {
        return tenks;
    }

    public void setTenks(String tenks) {
        this.tenks = tenks;
    }

    public int getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(int danhgia) {
        this.danhgia = danhgia;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getDichvucungcap() {
        return dichvucungcap;
    }

    public void setDichvucungcap(String dichvucungcap) {
        this.dichvucungcap = dichvucungcap;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public KhachSan(String maks, String tenks, int danhgia, String binhluan, String diadiem, String dichvucungcap, byte[] anh) {

        this.maks = maks;
        this.tenks = tenks;
        this.danhgia = danhgia;
        this.binhluan = binhluan;
        this.diadiem = diadiem;
        this.dichvucungcap = dichvucungcap;
        this.anh = anh;
    }


}
