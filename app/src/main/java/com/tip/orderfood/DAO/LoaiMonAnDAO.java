package com.tip.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("loai");
    Context context;
    public  LoaiMonAnDAO(Context context){
        this.context = context;
    }

    public boolean themLoaiMonAn(LoaiMonAnDTO loaiMonAnDTO){

        root.push().setValue(loaiMonAnDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    public Query layDanhSachLoaiMonAn(){

        Query query = root;
        return query;
    }

    public Query layLoaiMonAn(String maLoai){

        Query query = root.child(maLoai);
        return query;
    }

    public void layHinhLoaiMonAn(String maLoai){

        final String ma = maLoai;
        root.child(maLoai).child("hinhAnh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    String hinh = dataSnapshot.getValue().toString();
                    if (hinh.equals("null")){
                        FirebaseDatabase.getInstance().getReference().child("MonAn").orderByChild("maLoai").equalTo(ma).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String hinhAnh ="";

                                for (DataSnapshot d: dataSnapshot.getChildren()){
                                    MonAnDTO monAnDTO = d.getValue(MonAnDTO.class);
                                    hinhAnh = monAnDTO.getHinhAnh();
                                    break;

                                }
                                if (!hinhAnh.equals("")){
                                    root.child(ma).child("hinhAnh").setValue(hinhAnh);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void suaTenLoai(String maLoai, String tenLoai){
        root.child(maLoai).child("tenLoai").setValue(tenLoai).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.suathanhcong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void xoaLoai(String maLoai){
        root.child(maLoai).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.xoathanhcong, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
