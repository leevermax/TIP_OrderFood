package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tip.orderfood.DTO.ChiTietGoiMonDTO;
import com.tip.orderfood.DTO.GoiMonDTO;
import com.tip.orderfood.Database.CreateDatabase;

public class GoiMonDAO {

    SQLiteDatabase database;
    public  GoiMonDAO(Context context){

        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long themGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_maBan,goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_maNV,goiMonDTO.getMaNv());
        contentValues.put(CreateDatabase.TB_GOIMON_ngayGoi,goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_tinhTrang, goiMonDTO.getTinhTrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON,null,contentValues);

        return magoimon;

    }

    public long layMaGoiMonTheoMaBan(int maBan, String tinhTrang){
        long maGoiMon = 0;

        String truyVan = " SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_maBan + " = '" + maBan + "' AND "
                + CreateDatabase.TB_GOIMON_tinhTrang + " = '" + tinhTrang + "'";


        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maGoiMon = cursor.getLong(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_maGoiMon));

            cursor.moveToNext();
        }

        return maGoiMon;

    }

    public boolean kTraMonAn(int maGoiMon, int maMonAn){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_maMonAn
                + " = " + maMonAn + " AND " + CreateDatabase.TB_CHITIETGOIMON_maGoiMon + " = " + maGoiMon;

        Cursor cursor = database.rawQuery(truyvan,null);
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public int laySoLuongMonAn(int maGoiMon, int maMonAn){
        int soluong = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_maMonAn
                + " = " + maMonAn + " AND " + CreateDatabase.TB_CHITIETGOIMON_maGoiMon + " = " + maGoiMon;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_soLuong));

            cursor.moveToNext();
        }

        return soluong;
    }

    public  boolean capNhapSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_soLuong, chiTietGoiMonDTO.getSoLuong());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON,contentValues,CreateDatabase.TB_CHITIETGOIMON_maGoiMon + " = " + chiTietGoiMonDTO.getMaGoiMon()
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_maMonAn + " = " + chiTietGoiMonDTO.getMaMonAn(),null );

        if (kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean themCTGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_soLuong,chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_maGoiMon, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_maMonAn,chiTietGoiMonDTO.getMaMonAn());

        long kiemtra = database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);

        if (kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }
}
