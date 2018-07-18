package com.tip.orderfood.DTO;

public class GoiMonDTO {
    String maGoiMon, maNhanVien;
    String ngayGoi, maBan;
    boolean tinhTrang;

    public String getMaGoiMon() {
        return maGoiMon;
    }

    public void setMaGoiMon(String maGoiMon) {
        this.maGoiMon = maGoiMon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }


    public boolean getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTang) {
        this.tinhTrang = tinhTang;
    }

    public String getNgayGoi() {
        return ngayGoi;
    }

    public void setNgayGoi(String ngayGoi) {
        this.ngayGoi = ngayGoi;
    }
}
