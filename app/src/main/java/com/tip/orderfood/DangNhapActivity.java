package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDongYDN, btnDangKyDN;
    EditText edEmailDN, edMatKhauDN;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);


        addControls();
        addEvents();
    }


    private void addEvents() {
        showButton();
        btnDangKyDN.setOnClickListener(this);
        btnDongYDN.setOnClickListener(this);
    }

    private void addControls() {
        btnDangKyDN = findViewById(R.id.btnDangKyDN);
        btnDongYDN = findViewById(R.id.btnDongYDN);
        edMatKhauDN = findViewById(R.id.edMatKhauDN);
        edEmailDN = findViewById(R.id.edEmailDN);
        nhanVienDAO = new NhanVienDAO(this);
    }
    private void showButton(){
        boolean kiemTra = nhanVienDAO.kiemTraNV();
        btnDangKyDN.setVisibility(View.GONE);
//        if(kiemTra){
//
//            btnDongYDN.setVisibility(View.VISIBLE);
//        } else {
//            btnDongYDN.setVisibility(View.GONE);
//            btnDangKyDN.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        showButton();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDangKyDN:
                btnDangKyDN();
                break;

            case R.id.btnDongYDN:
                btnDongYDN();
                break;
        }
    }

    private void btnDangKyDN() {
        Intent iDangKy = new Intent(DangNhapActivity.this,ThemThongTinActivity.class);
        startActivity(iDangKy);
    }

    private void btnDongYDN() {
        String sTenDN = edEmailDN.getText().toString();
        String sMatKhau  = edMatKhauDN.getText().toString();
        int kTra = nhanVienDAO.kiemTraDangNhap(sTenDN,sMatKhau);
        if (kTra != -1){
            Intent iTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            iTrangChu.putExtra("tendn",edEmailDN.getText().toString());
            iTrangChu.putExtra("maNhanVien",kTra);
            startActivity(iTrangChu);
        } else {
            Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
        }
    }
}
