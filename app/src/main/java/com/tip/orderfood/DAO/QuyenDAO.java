package com.tip.orderfood.DAO;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class QuyenDAO {
    Context context;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Quyen");
    public QuyenDAO(Context context) {
        this.context = context;
    }
    public Query layDanhSachQuyen(){
        return root;
    }
}
