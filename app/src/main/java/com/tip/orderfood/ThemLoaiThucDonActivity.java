package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.Support.SuaDuLieu;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnDongYThemLoaiThucDon;
    EditText edTenLoai;
    LoaiMonAnDAO loaiMonAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);

        addConTrols();
        addEvents();
    }

    private void addConTrols() {
        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoai =findViewById(R.id.edThemLoaiThucDon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
    }

    private void addEvents() {
        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        SuaDuLieu suaDuLieu = new SuaDuLieu();
        String sTenLoaiThucDon = edTenLoai.getText().toString();
        String hinhAnh = "null";
        sTenLoaiThucDon = suaDuLieu.toiUuChuoi(sTenLoaiThucDon);
        if(sTenLoaiThucDon == null || sTenLoaiThucDon.equals("")){
            Toast.makeText(this,getResources().getString(R.string.vuilongnhapdulieu),Toast.LENGTH_SHORT).show();
        }else{
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setTenLoai(sTenLoaiThucDon);
            loaiMonAnDTO.setHinhAnh(hinhAnh);
            boolean kiemtra = loaiMonAnDAO.themLoaiMonAn(loaiMonAnDTO);
            Intent iDuLieu = new Intent();
            iDuLieu.putExtra("kiemtraloaithucdon",kiemtra);
            setResult(Activity.RESULT_OK,iDuLieu);
            finish();
        }
    }
}
