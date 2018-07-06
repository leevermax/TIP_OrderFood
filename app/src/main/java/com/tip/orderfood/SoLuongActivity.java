package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity  implements View.OnClickListener{
    Button btnDongYThemSoLuong;
    EditText edSoLuongMonAn;
    int maBan,maMonAn;
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
    }

    private void addEvents() {
        Intent intent = getIntent();
        maBan = intent.getIntExtra("maBan",0);
        maMonAn = intent.getIntExtra("maMonAn",0);


        btnDongYThemSoLuong.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tinhTrang = "false";
        int maGoiMon = 0;


        maGoiMon = (int) goiMonDAO.layMaGoiMonTheoMaBan(maBan,tinhTrang);

        boolean kTra = goiMonDAO.kTraMonAn(maGoiMon,maMonAn);
        if (kTra){
            int soLuonCu = goiMonDAO.laySoLuongMonAn(maGoiMon,maMonAn);
            int soLuongMoi = Integer.parseInt(edSoLuongMonAn.getText().toString());
            int tongSoLuong = soLuonCu + soLuongMoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(tongSoLuong);

            boolean kTraCN = goiMonDAO.capNhapSoLuong(chiTietGoiMonDTO);
            if (kTraCN){
                Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
            }

        } else {
            int soLuongGoi = Integer.parseInt(edSoLuongMonAn.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(soLuongGoi);


            boolean kTraCN = goiMonDAO.themCTGoiMon(chiTietGoiMonDTO);
            if (kTraCN){
                Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
            }
        }

    }
}
