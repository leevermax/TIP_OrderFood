package com.tip.orderfood.DTO;

public class BanAnDTO {
    int maBan;
    String tenBan;
    boolean duocChon;

    public boolean isDuocChon() {
        return duocChon;
    }

    public void setDuocChon(boolean duocChon) {
        this.duocChon = duocChon;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

}
