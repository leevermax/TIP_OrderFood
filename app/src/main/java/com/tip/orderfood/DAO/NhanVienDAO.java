package com.tip.orderfood.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.Database.CreateDatabase;
import com.tip.orderfood.R;

public class NhanVienDAO {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
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

    public Query getNameUser(String UID) {
        Query query = root.child(UID).child("hoTen");
        return query;
    }
    public Query layDanhSachNhanhVien(){
        Query query = root;
        return query;
    }
}
