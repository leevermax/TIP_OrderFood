package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterThanhToan;
import com.tip.orderfood.DTO.ThanhToanDTO;
import com.tip.orderfood.DTO.ThongKeGoiMonDTO;

import java.util.ArrayList;
import java.util.List;

public class ThongKeChiTietActivity extends AppCompatActivity {
    ListView lvThongKeChiTietGoiMon;
    TextView txtNguoiLapTK;

    List<ThanhToanDTO> thanhToanDTOS;
    AdapterThanhToan adapterThanhToan;
    DatabaseReference root;
    String maGoiMon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongkechitietgoimon);
        lvThongKeChiTietGoiMon = findViewById(R.id.lvThongKeChiTietGoiMon);
        txtNguoiLapTK = findViewById(R.id.txtNguoiLapTK);

        root = FirebaseDatabase.getInstance().getReference();
        thanhToanDTOS = new ArrayList<>();

        Intent intent = getIntent();
        maGoiMon = intent.getStringExtra("maGoiMon");

        root.child("GoiMon").child(maGoiMon).child("maNhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                root.child("Users").child(dataSnapshot.getValue().toString()).child("hoTen").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        txtNguoiLapTK.setText(dataSnapshot2.getValue().toString());
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


        adapterThanhToan = new AdapterThanhToan(ThongKeChiTietActivity.this,R.layout.custom_layout_thanhtoan,thanhToanDTOS);
        lvThongKeChiTietGoiMon.setAdapter(adapterThanhToan);

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
                                    thanhToanDTO.setSoLuong(soLuong);
                                    thanhToanDTO.setTenMonAn(dataSnapshot2.child("tenMonAn").getValue().toString());
                                    thanhToanDTO.setGiaTien(dataSnapshot2.child("giaTien").getValue(Integer.class));

                                    thanhToanDTOS.add(thanhToanDTO);
                                    adapterThanhToan.notifyDataSetChanged();

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
}
