package com.tip.orderfood.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemThongTinActivity;
import com.tip.orderfood.TrangChuActivity;

public class NhanVienDAO {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Context context;
    public  NhanVienDAO(Context context){
        this.context = context;
    }



    public void addNV(String uid,NhanVienDTO user){

        root.child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Query kiemTraQuyen(String UID) {
        Query query = root.child(UID).child("maQuyen");
        return query;
    }

    public void xoaNhanVien(String UID) {
        root.child(UID).removeValue();
    }

    public Query layNhanVien(String UID) {
        Query query = root.child(UID);
        return query;
    }

    public Query getNameUser(String UID) {
        Query query = root.child(UID).child("hoTen");
        return query;
    }
    public Query layDanhSachNhanhVien(){
        Query query = root;
        return query;
    }
    public void doiMatKhau(String Uid,String enPass){
        root.child(Uid).child("matKhau").setValue(enPass);
    }
}
