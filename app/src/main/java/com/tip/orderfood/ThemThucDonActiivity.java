package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.tip.orderfood.CustomAdapter.AdapterSpinnerLoaiMonAn;
import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.Support.SuaDuLieu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemThucDonActiivity extends AppCompatActivity implements View.OnClickListener{

    public static int REQUEST_CODE_THEMLOAITHUCDON =113;
    public static int REQUEST_CODE_MOHINH =123;
    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    AdapterSpinnerLoaiMonAn adapterSpinnerLoaiMonAn;

    MonAnDAO monAnDAO;

    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    EditText edTenMonAn, edGiaTien;

    FirebaseStorage storage;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    Uri filePath;
    DatabaseReference root;

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

        loaiMonAnDTOS = new ArrayList<>();
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imHinhThucDon = findViewById(R.id.imHinhThucDon);

        edTenMonAn = findViewById(R.id.edThemTenMonAn);
        edGiaTien = findViewById(R.id.edThemGiaTien);

        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);

        root = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();

        mStorageRef = storage.getReference("ImageThucDon");



    }

    private void addEvents() {
        imThemLoaiThucDon.setOnClickListener(this);

        hienThiSpinnerLoaiMonAn();

        imHinhThucDon.setOnClickListener(this);

        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }

    private void hienThiSpinnerLoaiMonAn(){

        adapterSpinnerLoaiMonAn = new AdapterSpinnerLoaiMonAn(ThemThucDonActiivity.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOS);
        spinLoaiMonAn.setAdapter(adapterSpinnerLoaiMonAn);

        loaiMonAnDAO.layDanhSachLoaiMonAn().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loaiMonAnDTOS.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    LoaiMonAnDTO loaiMonAnDTO = d.getValue(LoaiMonAnDTO.class);
                    loaiMonAnDTO.setMaLoai(d.getKey().toString());
                    loaiMonAnDTOS.add(loaiMonAnDTO);
                }
                adapterSpinnerLoaiMonAn.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                String maloai = loaiMonAnDTOS.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                SuaDuLieu suaDuLieu = new SuaDuLieu();
                tenmonan = suaDuLieu.toiUuChuoi(tenmonan);
                String gia = edGiaTien.getText().toString();
                if (gia.isEmpty()){
                    gia = "0";
                }
                int giatien = Integer.parseInt(gia);
                if(!tenmonan.isEmpty()){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh("");
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);
                    monAnDTO.setLanGoi(0);

                    final String key = monAnDAO.themMonAn(monAnDTO);
                    uploadImage(key);
                    if(key != null){
                        Toast.makeText(this,getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                        finish();
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
                filePath = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    imHinhThucDon.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private void uploadImage(final String key) {

        if (filePath != null) {
            Calendar calendar = Calendar.getInstance();
            StorageReference fileReference = mStorageRef.child("image" + calendar.getTimeInMillis() + ".png");

            mUploadTask = fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String imgURL=taskSnapshot.getDownloadUrl().toString();


                            root.child("MonAn").child(key).child("hinhAnh").setValue(imgURL);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThemThucDonActiivity.this, R.string.loi + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
//           Toast.makeText(ThemThucDonActiivity.this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

}
