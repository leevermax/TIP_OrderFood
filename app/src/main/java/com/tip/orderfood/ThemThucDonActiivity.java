package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tip.orderfood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.DTO.MonAnDTO;

import java.io.IOException;
import java.util.List;

public class ThemThucDonActiivity extends AppCompatActivity implements View.OnClickListener{

    public static int REQUEST_CODE_THEMLOAITHUCDON =113;
    public static int REQUEST_CODE_MOHINH =123;
    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;

    MonAnDAO monAnDAO;

    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    String sURLhinh;
    EditText edTenMonAn, edGiaTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        addConTrols();
        addEvents();

    }

    private void addConTrols() {
        imThemLoaiThucDon = findViewById(R.id.imThemLoaiThucDon);
        spinLoaiMonAn = findViewById(R.id.spinLoaiMonAn);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imHinhThucDon = findViewById(R.id.imHinhThucDon);

        edTenMonAn = findViewById(R.id.edThemTenMonAn);
        edGiaTien = findViewById(R.id.edThemGiaTien);

        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);
    }

    private void addEvents() {
        imThemLoaiThucDon.setOnClickListener(this);

        hienThiSpinnerLoaiMonAn();

        imHinhThucDon.setOnClickListener(this);

        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }

    private void hienThiSpinnerLoaiMonAn(){
        loaiMonAnDTOS = loaiMonAnDAO.layDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActiivity.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOS);
        spinLoaiMonAn.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActiivity.this,ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn,REQUEST_CODE_THEMLOAITHUCDON);
                break;
            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn Hình Thực Đơn"),REQUEST_CODE_MOHINH);
                break;
            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiMonAn.getSelectedItemPosition();
                int maloai = loaiMonAnDTOS.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();

                if(tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("") ){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sURLhinh);
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);

                    boolean kiemtra = monAnDAO.themMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if (resultCode == Activity.RESULT_OK){
                Intent duLieu = data;
                boolean kTra = duLieu.getBooleanExtra("kiemtraloaithucdon",false);
                if(kTra){
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    hienThiSpinnerLoaiMonAn();
                } else {
                    Toast.makeText(this,getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_CODE_MOHINH && data != null){
            if (resultCode == Activity.RESULT_OK){
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    imHinhThucDon.setImageBitmap(bitmap);
                    sURLhinh = bitmap.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                sURLhinh = data.getData().toString();
//                imHinhThucDon.setImageURI(data.getData());
            }
        }

    }

}
