package com.tip.orderfood.DTO;

public class MonAnDTO {
    String maMonAn;
    int giaTien, lanGoi;
    String tenMonAn, maLoai,  hinhAnh;

    public String getMaMonAn() {
        return maMonAn;
    }

    public int getLanGoi() {
        return lanGoi;
    }

    public void setLanGoi(int lanGoi) {
        this.lanGoi = lanGoi;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
