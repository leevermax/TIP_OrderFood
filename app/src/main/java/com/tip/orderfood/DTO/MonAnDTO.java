package com.tip.orderfood.DTO;

import android.support.annotation.NonNull;

public class MonAnDTO implements Comparable<MonAnDTO> {
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

    @Override
    public int compareTo(@NonNull MonAnDTO o) {
        if (lanGoi == o.lanGoi)
            return 0;
        else if (lanGoi > o.lanGoi)
            return -1;
        else
            return 1;
    }
}
