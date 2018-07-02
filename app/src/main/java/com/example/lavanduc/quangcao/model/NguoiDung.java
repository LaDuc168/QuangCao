package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class NguoiDung {
    private String manguoidung;
    private String hoten;
    private String gioitinh;
    private String phone;
    private String email;
    private String tentk;
    private String matkhau;
    private String quyen;

    public String getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(String manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public NguoiDung(String manguoidung, String hoten, String gioitinh, String phone, String email, String tentk, String matkhau, String quyen) {

        this.manguoidung = manguoidung;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.phone = phone;
        this.email = email;
        this.tentk = tentk;
        this.matkhau = matkhau;
        this.quyen = quyen;
    }
}
