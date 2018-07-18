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
import com.tip.orderfood.DTO.ChiTietGoiMonDTO;
import com.tip.orderfood.DTO.GoiMonDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;

public class GoiMonDAO {

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("GoiMon");
    Context context;
    public  GoiMonDAO(Context context){
        this.context = context;

    }

    public String themGoiMon(GoiMonDTO goiMonDTO){
        String key = root.push().getKey().toString();
        root.child(key).setValue(goiMonDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.goimon, Toast.LENGTH_SHORT).show();
            }
        });
        return key;
    }



    public void themCTGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon").push().setValue(chiTietGoiMonDTO);
    }

    public Query layMaGoiMonTheoBan(String maBan){
        Query query = root.orderByChild("maBan").equalTo(maBan);
        return query;
    }
}
