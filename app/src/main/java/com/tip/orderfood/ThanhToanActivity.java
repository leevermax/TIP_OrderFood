package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterThanhToan;
import com.tip.orderfood.DTO.ThanhToanDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener{
    public static int RESQUEST_CODE_THANH_TOAN= 111;
    ListView lvThanhToan;
    Button btnThoatThanhToan, btnThanhToan;
    TextView txtTongTien;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterThanhToan adapterThanhToan;
    DatabaseReference root;
    String maBan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);
        addControls();
        addEvents();
    }



    private void addEvents() {

        btnThanhToan.setOnClickListener(this);
        btnThoatThanhToan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnThanhToan:
                Intent iThanhToan = new Intent(ThanhToanActivity.this,XacNhanThanhToanActivity.class);
                iThanhToan.putExtra("maBan", maBan);
                startActivityForResult(iThanhToan,RESQUEST_CODE_THANH_TOAN);

                break;
            case R.id.btnThoatThanhToan:
                finish();
                break;
        }
    }

    private void addControls() {
        root = FirebaseDatabase.getInstance().getReference();

        lvThanhToan = findViewById(R.id.lvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoatThanhToan = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        thanhToanDTOS = new ArrayList<>();

        Intent intent = getIntent();
        maBan = intent.getStringExtra("maBan");

        if (maBan != null){
            adapterThanhToan = new AdapterThanhToan(ThanhToanActivity.this,R.layout.custom_layout_thanhtoan,thanhToanDTOS);
            lvThanhToan.setAdapter(adapterThanhToan);
            root.child("GoiMon").orderByChild("maBan").equalTo(maBan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String maGoiMon = "";

                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        if (d.child("tinhTrang").getValue(boolean.class) == false) {
                            maGoiMon = d.getKey();
                            break;
                        }
                    }
                    root.child("ChiTietGoiMon").orderByChild("maGoiMon").equalTo(maGoiMon).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            thanhToanDTOS.clear();
                            for (DataSnapshot d: dataSnapshot.getChildren()){

                                final int soLuong = d.child("soLuong").getValue(Integer.class);
                                String maMon = d.child("maMonAn").getValue().toString();
                                final long k = dataSnapshot.getChildrenCount();
                                root.child("MonAn").child(maMon).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot2) {
                                        ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
                                        long tongTien = 0;
                                        thanhToanDTO.setSoLuong(soLuong);
                                        thanhToanDTO.setTenMonAn(dataSnapshot2.child("tenMonAn").getValue().toString());
                                        thanhToanDTO.setGiaTien(dataSnapshot2.child("giaTien").getValue(Integer.class));

                                        thanhToanDTOS.add(thanhToanDTO);
                                        adapterThanhToan.notifyDataSetChanged();

                                        if (thanhToanDTOS.size() == k){
                                            for ( ThanhToanDTO tt: thanhToanDTOS){
                                                tongTien += (tt.getSoLuong())*(tt.getGiaTien());
                                            }

                                        }
                                        txtTongTien.setText(tongTien+"");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError2) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_THANH_TOAN){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthanhcong), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
