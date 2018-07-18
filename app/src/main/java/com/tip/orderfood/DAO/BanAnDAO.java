package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Ban");
    Context context;

    public BanAnDAO(Context context) {
        this.context = context;
    }

    public void themBanAn(BanAnDTO banAnDTO) {
        root.push().setValue(banAnDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public Query getlistban() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Ban");
        return query;
    }



    public Query layTinhTrangBanTheoMa(String maBan) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Ban").child(maBan).child("tinhTrang");
        return query;
    }

    public boolean capNhapTTBan(String maBan, boolean tinhTrang) {

        root.child(maBan).child("tinhTrang").setValue(true);
        return true;
    }
}
