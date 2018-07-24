package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.Support.SuaDuLieu;

public class SuaLoaiMonAnActivity extends AppCompatActivity {

    Button btnDongYThemLoaiThucDon;
    EditText edTenLoai;
    LoaiMonAnDAO loaiMonAnDAO;
    String maLoai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);


        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoai = findViewById(R.id.edThemLoaiThucDon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        Intent iSuaLoai = getIntent();
        maLoai = iSuaLoai.getStringExtra("maLoai");

        btnDongYThemLoaiThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaDuLieu suaDuLieu = new SuaDuLieu();
                String sTenLoaiThucDon = edTenLoai.getText().toString();
                sTenLoaiThucDon = suaDuLieu.toiUuChuoi(sTenLoaiThucDon);
                if (!sTenLoaiThucDon.isEmpty()){
                    loaiMonAnDAO.suaTenLoai(maLoai,sTenLoaiThucDon);
                    Toast.makeText(SuaLoaiMonAnActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SuaLoaiMonAnActivity.this,getResources().getString(R.string.khongduocdetrong), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
