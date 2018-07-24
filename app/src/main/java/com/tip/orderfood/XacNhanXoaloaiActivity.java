package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DAO.MonAnDAO;

public class XacNhanXoaloaiActivity extends AppCompatActivity {

    Button btnXacNhanXoaLoai;
    String maLoai;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanxoaloaimonan);

        btnXacNhanXoaLoai = findViewById(R.id.btnXacNhanXoaLoai);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        Intent iXacNhanXoaLoai = getIntent();
        maLoai = iXacNhanXoaLoai.getStringExtra("maLoai");

        btnXacNhanXoaLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAnDAO.xoaMonAnTheoLoai(maLoai);
                loaiMonAnDAO.xoaLoai(maLoai);
                finish();
            }
        });

    }
}
