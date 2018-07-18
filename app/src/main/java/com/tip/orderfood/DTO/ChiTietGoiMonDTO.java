package com.tip.orderfood.DTO;

public class ChiTietGoiMonDTO {
    String maMonAn,maGoiMon;
    int soLuong;
    boolean hoanThanh,phucVu;

    public boolean isHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(boolean hoanThanh) {
        this.hoanThanh = hoanThanh;
    }

    public boolean isPhucVu() {
        return phucVu;
    }

    public void setPhucVu(boolean phucVu) {
        this.phucVu = phucVu;
    }

    public String getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getMaGoiMon() {
        return maGoiMon;
    }

    public void setMaGoiMon(String maGoiMon) {
        this.maGoiMon = maGoiMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
