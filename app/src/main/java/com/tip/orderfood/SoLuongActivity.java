package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity  implements View.OnClickListener{
    Button btnDongYThemSoLuong;
    EditText edSoLuongMonAn;
    MonAnDAO monAnDAO;
    String maBan;
    String maMonAn;
    String maGoiMon;
    GoiMonDAO goiMonDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        addConTrols();
        addEvents();
    }

    private void addConTrols() {
        btnDongYThemSoLuong = findViewById(R.id.btnDongYThemSoLuong);
        edSoLuongMonAn = findViewById(R.id.edSoLuongMonAn);

        goiMonDAO = new GoiMonDAO(this);

        monAnDAO = new MonAnDAO(this);
    }

    private void addEvents() {
        Intent intent = getIntent();
        maBan = intent.getStringExtra("maBan");
        maMonAn = intent.getStringExtra("maMonAn");
        maGoiMon = intent.getStringExtra("maGoiMon");
        btnDongYThemSoLuong.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String soLuong = edSoLuongMonAn.getText().toString();
        if (!soLuong.isEmpty()){
            int soLuongGoi = Integer.parseInt(soLuong);
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(soLuongGoi);
            chiTietGoiMonDTO.setHoanThanh(false);
            chiTietGoiMonDTO.setPhucVu(false);

            goiMonDAO.themCTGoiMon(chiTietGoiMonDTO);

            monAnDAO.tangDiemMonAn(maMonAn,soLuongGoi);
            Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.khongduocdetrong), Toast.LENGTH_SHORT).show();
        }

    }
}
