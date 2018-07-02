package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class BinhLuanGT {
    private String binhluan;
    private int stt;
    private String noidunggt;

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

    public String getNoidunggt() {
        return noidunggt;
    }

    public void setNoidunggt(String noidunggt) {
        this.noidunggt = noidunggt;
    }

    public BinhLuanGT(String binhluan, int stt, String noidunggt) {

        this.binhluan = binhluan;
        this.stt = stt;
        this.noidunggt = noidunggt;
    }
}
