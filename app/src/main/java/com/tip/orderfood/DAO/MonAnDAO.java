package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    Context context;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("MonAn");
    public  MonAnDAO(Context context){
        this.context = context;
    }

    public String themMonAn(MonAnDTO monAnDTO){
        String key = root.push().getKey();
        root.child(key).setValue(monAnDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
            }
        });
        return key;

    }
    public void suaMonAn(String maMonAn,MonAnDTO monAnDTO){

        root.child(maMonAn).setValue(monAnDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.suathanhcong, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Query LayDanhSachMonAnTheoLoai(String maLoai){
        Query query = root.orderByChild("maLoai").equalTo(maLoai);

        return query;

    }

    public Query LayDanhSachMonAn(){
        Query query = root;

        return query;

    }
    public Query layMonAnTheoMa(String maMonAn){
        Query query = root.child(maMonAn);

        return query;

    }
    public void resetLanGoi(){
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    root.child(d.getKey()).child("lanGoi").setValue(0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void tangDiemMonAn(String maMonAn, int soLuong){
        final  int sl = soLuong;
        final String m = maMonAn;
        root.child(maMonAn).child("lanGoi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = dataSnapshot.getValue(Integer.class) + sl;
                root.child(m).child("lanGoi").setValue(count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void xoaMonAnTheoLoai(String maLoai){
        root.orderByChild("maLoai").equalTo(maLoai).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot d: dataSnapshot.getChildren()){
                    String maMonAn = d.getKey();
                    FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon").orderByChild("maMonAn").equalTo(maMonAn).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot d2 : dataSnapshot2.getChildren()){
                                String maCT = d2.getKey();
                                FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon").child(maCT).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    root.child(maMonAn).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void xoaMonAn(String maMonAn){
        FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon").orderByChild("maMonAn").equalTo(maMonAn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                for (DataSnapshot d2 : dataSnapshot2.getChildren()){
                    String maCT = d2.getKey();
                    FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon").child(maCT).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.child(maMonAn).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.xoathanhcong, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
