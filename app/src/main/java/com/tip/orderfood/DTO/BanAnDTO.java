package com.tip.orderfood.DTO;

public class BanAnDTO {
    String tenBan,maBan;
    boolean tinhTrang;

    public BanAnDTO() {
    }

    public BanAnDTO(String tenBan, boolean tinhTrang) {
        this.tenBan = tenBan;
        this.maBan = maBan;
        this.tinhTrang = tinhTrang;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
