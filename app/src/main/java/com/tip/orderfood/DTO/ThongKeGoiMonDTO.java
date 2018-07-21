package com.tip.orderfood.DTO;

public class ThongKeGoiMonDTO {

    String maGoiMonTK, ngayGoiMon;
    long tongTienTK;

    public String getMaGoiMonTK() {
        return maGoiMonTK;
    }

    public void setMaGoiMonTK(String maGoiMonTK) {
        this.maGoiMonTK = maGoiMonTK;
    }

    public String getNgayGoiMon() {
        return ngayGoiMon;
    }

    public void setNgayGoiMon(String ngayGoiMon) {
        this.ngayGoiMon = ngayGoiMon;
    }

    public long getTongTienTK() {
        return tongTienTK;
    }

    public void setTongTienTK(long tongTienTK) {
        this.tongTienTK = tongTienTK;
    }
}
