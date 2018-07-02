package com.example.lavanduc.quangcao.model;

import java.io.Serializable;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class KhachHang implements Serializable {
    private String makhach;
    private String hoten;
    private String ngaysinh;
    private String diachi;
    private String maphong;
    private String maks;
    private String gioitinh;

    public KhachHang(String makhach, String hoten, String ngaysinh, String diachi, String maphong, String maks, String gioitinh) {
        this.makhach = makhach;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.maphong = maphong;
        this.maks = maks;
        this.gioitinh = gioitinh;
    }

    public String getMaks() {
        return maks;
    }

    public void setMaks(String maks) {
        this.maks = maks;
    }



    public String getMakhach() {
        return makhach;
    }

    public void setMakhach(String makhach) {
        this.makhach = makhach;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }


}
