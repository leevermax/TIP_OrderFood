package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.ThemThongTinActivity;

public class NhanVienDAO {
    SQLiteDatabase database;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("user");
    public  NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    public long themNV(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_tenDN,nhanVienDTO.getHoTen());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND,nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_gioiTinh,nhanVienDTO.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_matKhau,nhanVienDTO.getMatKhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_ngaySinh,nhanVienDTO.getNgaySinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_maQuyen, nhanVienDTO.getMaQuyen());

        long kiemtra = database.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);
        return kiemtra;
    }
    public boolean kiemTraNV(){
        String truyVBan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyVBan, null);
        if (cursor.getCount() != 0){
            return true;
        }
        else return  false;
    }

    public int kiemTraDangNhap(String tenDN, String matKhau){
        String truyVan = "SELECT * FROM "+ CreateDatabase.TB_NHANVIEN +"WHERE "+ CreateDatabase.TB_NHANVIEN_tenDN +"= '"+tenDN+"'AND"+CreateDatabase.TB_NHANVIEN_matKhau+" = '"+matKhau+"'";
        String truyVBan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyVBan, null);
        int maNV = -1;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_maNV));
            cursor.moveToNext();
        }
        return maNV;
    }
    public void addNV(NhanVienDTO user){
        root.push().setValue(user);
    }
}
