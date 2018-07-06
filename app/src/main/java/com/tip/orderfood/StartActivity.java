package com.tip.orderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnKhachHang, btnNhanVien, btnNhaBep, btnQuanLy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startapp);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnKhachHang = findViewById(R.id.btnKhachHang);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnNhaBep = findViewById(R.id.btnNhaBep);
        btnQuanLy = findViewById(R.id.btnQuanLy);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("hgnsdbdb").setValue("Hello, World!");
    }

    private void addEvents() {
        btnKhachHang.setOnClickListener(this);
        btnNhanVien.setOnClickListener(this);
        btnNhaBep.setOnClickListener(this);
        btnQuanLy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnKhachHang:

                break;
            case R.id.btnNhanVien:

                break;
            case R.id.btnNhaBep:

                break;
            case R.id.btnQuanLy:

                break;
        }
    }
}
