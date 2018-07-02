package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class HoatDongGT {
    private String ma;
    private String thoigianbd;
    private String thoigiankt;
    private String motachitiet;
    private String binhluan;
    private int luotthich;
    private String diadiem;
    private byte[] anh;
    private String ten;

    public HoatDongGT(String ma, String thoigianbd, String thoigiankt, String motachitiet, String binhluan, String diadiem, byte[] anh, String ten) {
        this.ma = ma;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.motachitiet = motachitiet;
        this.binhluan = binhluan;
        this.diadiem = diadiem;
        this.anh = anh;
        this.ten = ten;
    }

    public HoatDongGT(String ma, int luotthich, byte[] anh, String ten) {
        this.ma = ma;
        this.luotthich = luotthich;
        this.anh = anh;
        this.ten = ten;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public HoatDongGT(String ma, String thoigianbd, String thoigiankt, String motachitiet, String binhluan, int luotthich, String diadiem, byte[] anh, String ten) {

        this.ma = ma;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.motachitiet = motachitiet;
        this.binhluan = binhluan;
        this.luotthich = luotthich;
        this.diadiem = diadiem;
        this.anh = anh;
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
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

    public String getMotachitiet() {
        return motachitiet;
    }

    public void setMotachitiet(String motachitiet) {
        this.motachitiet = motachitiet;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public int getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(int luotthich) {
        this.luotthich = luotthich;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public HoatDongGT(String ma, String thoigianbd, String thoigiankt, String motachitiet, String binhluan, int luotthich, String diadiem, byte[] anh) {

        this.ma = ma;
        this.thoigianbd = thoigianbd;
        this.thoigiankt = thoigiankt;
        this.motachitiet = motachitiet;
        this.binhluan = binhluan;
        this.luotthich = luotthich;
        this.diadiem = diadiem;
        this.anh = anh;
    }
}
