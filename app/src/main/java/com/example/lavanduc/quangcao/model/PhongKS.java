package com.example.lavanduc.quangcao.model;

import java.io.Serializable;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class PhongKS implements Serializable{
    private String maks;
    private String maphong;
    private String thoigianbd;
    private String thoigiankt;
    private String tinhtrang;
    private int uudai;
    private int gia;
    private String motachitiet;
    private byte[]anh;
    private String maloai;

    public PhongKS(String maks, String maphong, String thoigianbd, String thoigiankt, String tinhtrang, int uudai, int gia, String motachitiet, String maloai) {
        this.maks = maks;
        this.maphong = maphong;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.tinhtrang = tinhtrang;
        this.uudai = uudai;
        this.gia = gia;
        this.motachitiet = motachitiet;
        this.maloai = maloai;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public PhongKS(String maks, String maphong, String thoigianbd, String thoigiankt, String tinhtrang, int uudai, int gia, String motachitiet, byte[] anh, String maloai) {
        this.maks = maks;
        this.maphong = maphong;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.tinhtrang = tinhtrang;
        this.uudai = uudai;
        this.gia = gia;
        this.motachitiet = motachitiet;
        this.anh = anh;
        this.maloai = maloai;
    }

    public PhongKS(String maks, String maphong, String tinhtrang) {
        this.maks = maks;
        this.maphong = maphong;
        this.tinhtrang = tinhtrang;
    }

    public PhongKS(String maks, String maphong, String thoigianbd, String thoigiankt, String tinhtrang, int uudai, int gia, String motachitiet) {
        this.maks = maks;
        this.maphong = maphong;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.tinhtrang = tinhtrang;
        this.uudai = uudai;
        this.gia = gia;
        this.motachitiet = motachitiet;
    }

    public String getMaks() {
        return maks;
    }

    public void setMaks(String maks) {
        this.maks = maks;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public String getThoigianbd() {
        return thoigianbd;
    }

    public void setThoigianbd(String thoigianbd) {
        this.thoigianbd = thoigianbd;
    }

    public String getThoigiankt() {
        return thoigiankt;
    }

    public void setThoigiankt(String thoigiankt) {
        this.thoigiankt = thoigiankt;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getUudai() {
        return uudai;
    }

    public void setUudai(int uudai) {
        this.uudai = uudai;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMotachitiet() {
        return motachitiet;
    }

    public void setMotachitiet(String motachitiet) {
        this.motachitiet = motachitiet;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public PhongKS(String maks, String maphong, String thoigianbd, String thoigiankt, String tinhtrang, int uudai, int gia, String motachitiet, byte[] anh) {

        this.maks = maks;
        this.maphong = maphong;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.tinhtrang = tinhtrang;
        this.uudai = uudai;
        this.gia = gia;
        this.motachitiet = motachitiet;
        this.anh = anh;
    }
}
