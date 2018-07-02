package com.example.lavanduc.quangcao.model;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class DichVuCC {
    private String dichvucc;
    private int stt;
    private String tendichvu;

    public DichVuCC(String dichvucc, int stt, String tendichvu) {
        this.dichvucc = dichvucc;
        this.stt = stt;
        this.tendichvu = tendichvu;
    }

    public String getDichvucc() {
        return dichvucc;
    }

    public void setDichvucc(String dichvucc) {
        this.dichvucc = dichvucc;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTendichvu() {
        return tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        this.tendichvu = tendichvu;
    }
}
