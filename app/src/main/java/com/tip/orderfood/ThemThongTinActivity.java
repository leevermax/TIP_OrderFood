package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.FragmentApp.DatePickerFragment;

public class ThemThongTinActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{
    EditText edHoTenDK, edMatKhauDK, edNgaySinhDK,edCMNDDK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    String sGioiTinh;
    RadioButton rdNam,rdNu;
    String UIDnv = "";
    DatabaseReference root;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthongtindangky);

        addControls();
        addEvents();
    }

    private void addControls() {
        edHoTenDK = findViewById(R.id.edHoTenDK);
        edMatKhauDK = findViewById(R.id.edMatKhauDK);
        edNgaySinhDK = findViewById(R.id.edNgaySinhDK);
        edCMNDDK = findViewById(R.id.edCMNDDK);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        nhanVienDAO = new NhanVienDAO(this);
        root = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UIDnv = intent.getStringExtra("UIDNhanVien");
    }

    private void addEvents() {
        btnThoatDK.setOnClickListener(this);
        btnDongYDK.setOnClickListener(this);
        edNgaySinhDK.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDK:
                themNV();
                break;
            case  R.id.btnThoatDK:
                finish(); break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edNgaySinhDK:
                if(hasFocus){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(),"Ngày Sinh");
                }
                break;
        }
    }
    public void  themNV(){
        String sHoTen = edHoTenDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;

            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edCMNDDK.getText().toString());

        if(sHoTen == null || sHoTen.equals("")){
            Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
        }else if(UIDnv == null || UIDnv.equals("")){
            Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }else {

            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setHoTen(sHoTen);
            nhanVienDTO.setMatKhau(sMatKhau);
            nhanVienDTO.setCMND(sCMND);
            nhanVienDTO.setNgaySinh(sNgaySinh);
            nhanVienDTO.setGioiTinh(sGioiTinh);
            nhanVienDTO.setIdUser(UIDnv);
            long kiemtra = nhanVienDAO.themNV(nhanVienDTO);
            nhanVienDAO.addNV(nhanVienDTO);
            if(kiemtra != 0){
                Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
