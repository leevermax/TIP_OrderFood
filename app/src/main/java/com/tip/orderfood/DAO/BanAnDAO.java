package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase database;
    public  BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public  boolean themBanAn(String tenBanAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_tenBan,tenBanAn);
        contentValues.put(CreateDatabase.TB_BANAN_tinhTrang,"false");

        long kTra = database.insert(CreateDatabase.TB_BANAN,null,contentValues);
        if(kTra != 0){
            return  true;
        } else
            return  false;
    }

    public List<BanAnDTO> layTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String truyVan = "SELECT * FROM "+ CreateDatabase.TB_BANAN;
        Cursor cursor =database.rawQuery(truyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_maBan)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_tenBan)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return  banAnDTOList;
    }

    public String layTinhTrangBanThaoMa(int maBan){
        String tinhtrang = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_BANAN + " WHERE " + CreateDatabase.TB_BANAN_maBan + " = '" + maBan + "'";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_tinhTrang));

            cursor.moveToNext();
        }
        return tinhtrang;
    }
    public boolean capNhapTTBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_tinhTrang,tinhTrang);
        long kTra = database.update(CreateDatabase.TB_BANAN,contentValues,CreateDatabase.TB_BANAN_maBan + "='"+maBan+"'",null);
        if (kTra != 0){
            return true;
        } else {
            return false;
        }
    }
}
