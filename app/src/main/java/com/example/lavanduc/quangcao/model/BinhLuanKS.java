package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class BinhLuanKS {
    private String binhluan;
    private int stt;
    private String noidungks;

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getNoidungks() {
        return noidungks;
    }

    public void setNoidungks(String noidungks) {
        this.noidungks = noidungks;
    }

    public BinhLuanKS(String binhluan, int stt, String noidungks) {

        this.binhluan = binhluan;
        this.stt = stt;
        this.noidungks = noidungks;
    }
}
