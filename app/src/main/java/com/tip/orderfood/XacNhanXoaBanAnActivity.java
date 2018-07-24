package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DTO.ChiTietGoiMonDTO;

public class XacNhanXoaBanAnActivity extends AppCompatActivity {

    Button btnXacNhanXoaBanAn;

    String maBan;

    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanxoaban);

        btnXacNhanXoaBanAn = findViewById(R.id.btnXacNhanXoaBanAn);

        Intent iXacNhanXoa = getIntent();
        maBan = iXacNhanXoa.getStringExtra("maBan");

        banAnDAO = new BanAnDAO(this);
        goiMonDAO = new GoiMonDAO(this);

        btnXacNhanXoaBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goiMonDAO.xoaGoiMonTheoBan(maBan);
                banAnDAO.xoaBanAn(maBan);
                finish();
            }
        });



    }
}
