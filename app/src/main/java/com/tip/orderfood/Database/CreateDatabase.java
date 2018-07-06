package com.tip.orderfood.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {
    public static String TB_NHANVIEN = "NHANVIEN";
    public static String TB_MONAN = "MONAN";
    public static String TB_LOAIMONAN = "LOAIMONAN";
    public static String TB_BANAN = "BANAN";
    public static String TB_GOIMON = "GOIMON";
    public static String TB_CHITIETGOIMON = "CHITIETGOIMON";
    public static String TB_QUYEN = "QUYEN";

    public static String TB_NHANVIEN_maNV = "maNV";
    public static String TB_NHANVIEN_maQuyen = "maQuyen";
    public static String TB_NHANVIEN_tenDN = "tenDN";
    public static String TB_NHANVIEN_matKhau = "matKhau";
    public static String TB_NHANVIEN_gioiTinh = "gioiTinh";
    public static String TB_NHANVIEN_ngaySinh = "ngaySinh";
    public static String TB_NHANVIEN_CMND  = "CMND";

    public static String TB_MONAN_maMon = "maMon";
    public static String TB_MONAN_tenMon = "tenMon";
    public static String TB_MONAN_giaTien = "giaTien";
    public static String TB_MONAN_maLoai = "maLoai";
    public static String TB_MONAN_hinhAnh = "hinhAnh";

    public static String TB_LOAIMONAN_maLoai = "maLoai";
    public static String TB_LOAIMONAN_tenLoai = "tenLoai";

    public static String TB_QUYEN_maQuyen = "maQuyen";
    public static String TB_QUYEN_tenQuyen = "tenQuyen";

    public static String TB_BANAN_maBan = "maBan";
    public static String TB_BANAN_tenBan = "tenBan";
    public static String TB_BANAN_tinhTrang = "tinhTrang";

    public static String TB_GOIMON_maGoiMon = "maGoiMon";
    public static String TB_GOIMON_maNV = "maNV";
    public static String TB_GOIMON_ngayGoi = "ngayGoi";
    public static String TB_GOIMON_tinhTrang = "tinhTrang";
    public static String TB_GOIMON_maBan = "maBan";

    public static String TB_CHITIETGOIMON_maGoiMon = "maGoiMon";
    public static String TB_CHITIETGOIMON_maMonAn = "maMonAn";
    public static String TB_CHITIETGOIMON_soLuong = "soLuong";

    public CreateDatabase(Context context) {
        super(context, "OrderFool", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbNHANVIEN = " CREATE TABLE " + TB_NHANVIEN + "( " + TB_NHANVIEN_maNV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NHANVIEN_tenDN + " TEXT, " + TB_NHANVIEN_matKhau + " TEXT, " + TB_NHANVIEN_gioiTinh + " TEXT, "
                + TB_NHANVIEN_ngaySinh + " TEXT, " + TB_NHANVIEN_CMND + " INTEGER , " + TB_NHANVIEN_maQuyen + " INTEGER )";

        String tbBANAN = "CREATE TABLE " + TB_BANAN + " ( " + TB_BANAN_maBan + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_BANAN_tenBan + " TEXT, " + TB_BANAN_tinhTrang + " TEXT )";

        String tbMONAN = "CREATE TABLE " + TB_MONAN + " ( " + TB_MONAN_maMon + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_MONAN_tenMon + " TEXT, " + TB_MONAN_maLoai + " INTEGER, " + TB_MONAN_giaTien + " TEXT, "
                + TB_MONAN_hinhAnh + " TEXT ) ";

        String tbLOAIMON = "CREATE TABLE " + TB_LOAIMONAN + " ( " + TB_LOAIMONAN_maLoai + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_LOAIMONAN_tenLoai + " TEXT )";

        String tbQUYEN = "CREATE TABLE " + TB_QUYEN + " ( " + TB_QUYEN_maQuyen + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_QUYEN_tenQuyen + " TEXT )";

        String tbGOIMON = "CREATE TABLE " + TB_GOIMON + " ( " + TB_GOIMON_maGoiMon + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_GOIMON_maBan + " INTEGER, " + TB_GOIMON_maNV + " INTEGER, " + TB_GOIMON_ngayGoi + " TEXT, "
                + TB_GOIMON_tinhTrang + " TEXT )";

        String tbCHITIETGOIMON = "CREATE TABLE " + TB_CHITIETGOIMON + " ( " + TB_CHITIETGOIMON_maGoiMon + " INTEGER, "
                + TB_CHITIETGOIMON_maMonAn + " INTEGER, " + TB_CHITIETGOIMON_soLuong + " INTEGER, "
                + " PRIMARY KEY ( " + TB_CHITIETGOIMON_maGoiMon + "," + TB_CHITIETGOIMON_maMonAn + "))";

        db.execSQL(tbNHANVIEN);
        db.execSQL(tbBANAN);
        db.execSQL(tbMONAN);
        db.execSQL(tbLOAIMON);
        db.execSQL(tbGOIMON);
        db.execSQL(tbCHITIETGOIMON);
        db.execSQL(tbQUYEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
