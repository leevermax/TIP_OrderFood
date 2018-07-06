package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    SQLiteDatabase database;
    public  LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themLoaiMonAn(String tenLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_tenLoai,tenLoai);

        long kTra = database.insert(CreateDatabase.TB_LOAIMONAN,null,contentValues);

        if (kTra !=0){
            return true;
        } else {
            return false;
        }
    }

    public List<LoaiMonAnDTO> layDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> loaiMonAnDTOS = new ArrayList<LoaiMonAnDTO>();

        String truyVan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_maLoai)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_tenLoai)));

            loaiMonAnDTOS.add(loaiMonAnDTO);
            cursor.moveToNext();
        }

        return loaiMonAnDTOS;
    }

    public String layHinhLoaiMonAn(int maloai){
        String hinhanh = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_maLoai + " = '" + maloai + "' "
                + " AND " + CreateDatabase.TB_MONAN_hinhAnh + " != '' ORDER BY " + CreateDatabase.TB_MONAN_maMon + " LIMIT 1";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_hinhAnh));
            cursor.moveToNext();
        }

        return hinhanh;
    }
}
