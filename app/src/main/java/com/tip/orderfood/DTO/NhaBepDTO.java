package com.tip.orderfood.DTO;

public class NhaBepDTO {
    String maCT,tenMonAn, tenBan;
    int soLuong;
    boolean phucVu, hoanThanh;

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isPhucVu() {
        return phucVu;
    }

    public void setPhucVu(boolean phucVu) {
        this.phucVu = phucVu;
    }

    public boolean isHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(boolean hoanThanh) {
        this.hoanThanh = hoanThanh;
    }
}
