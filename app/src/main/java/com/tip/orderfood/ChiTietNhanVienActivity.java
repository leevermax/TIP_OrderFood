package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterQuyen;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DAO.QuyenDAO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.DTO.QuyenDTO;

import java.util.ArrayList;
import java.util.List;

public class ChiTietNhanVienActivity extends AppCompatActivity implements View.OnClickListener {


    TextView txtTenNhanVienCT,txtEmailCT,txtSDTCT,txtQuyenCT,txtNgaySinhCT,txtGioiTinhCT,txtCMNDCT;
    Button btnThoatCT,btnXoaNhanVienCT,btnSuaQuyenNV;

    LinearLayout llSuaQuyen;
    Spinner spinQuyen;


    String UIDxoa,UidHT;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    DatabaseReference root;

    QuyenDAO quyenDAO;
    List<QuyenDTO> quyenDTOS;
    AdapterQuyen adapterQuyen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietnhanvien);

        addControls();
        addEvents();
    }

    private void addEvents() {

        nhanVienDAO.layNhanVien(UIDxoa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NhanVienDTO nhanVienDTO = dataSnapshot.getValue(NhanVienDTO.class);
                nhanVienDTO.setUid(dataSnapshot.getKey());
                txtTenNhanVienCT.setText(nhanVienDTO.getHoTen());
                txtEmailCT.setText(nhanVienDTO.getEmail());
                txtSDTCT.setText(nhanVienDTO.getPhone());
                txtCMNDCT.setText(nhanVienDTO.getCmnd());
                txtNgaySinhCT.setText(nhanVienDTO.getNgaySinh());
                txtGioiTinhCT.setText(nhanVienDTO.getGioiTinh());

                root.child("Quyen").child(nhanVienDTO.getMaQuyen()).child("tenQuyen").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        txtQuyenCT.setText(dataSnapshot.getValue().toString());
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

        btnThoatCT.setOnClickListener(this);
        btnXoaNhanVienCT.setOnClickListener(this);
        btnSuaQuyenNV.setOnClickListener(this);
    }

    private void addControls() {
        txtTenNhanVienCT = findViewById(R.id.txtTenNhanVienCT);
        txtEmailCT = findViewById(R.id.txtEmailCT);
        txtSDTCT = findViewById(R.id.txtSDTCT);
        txtQuyenCT = findViewById(R.id.txtQuyenCT);
        txtNgaySinhCT = findViewById(R.id.txtNgaySinhCT);
        txtGioiTinhCT = findViewById(R.id.txtGioiTinhCT);
        txtCMNDCT = findViewById(R.id.txtCMNDCT);
        btnThoatCT = findViewById(R.id.btnThoatCT);
        btnXoaNhanVienCT = findViewById(R.id.btnXoaNhanVienCT);

        llSuaQuyen = findViewById(R.id.llSuaQuyen);
        spinQuyen = findViewById(R.id.spinQuyen);
        btnSuaQuyenNV = findViewById(R.id.btnSuaQuyenNV);

        quyenDAO = new QuyenDAO(this);
        quyenDTOS = new ArrayList<>();


        nhanVienDAO = new NhanVienDAO(this);

        root = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        UIDxoa = intent.getStringExtra("UID");

        llSuaQuyen.setVisibility(View.GONE);

        if (user != null){
            UidHT = user.getUid().toString();
            nhanVienDAO.kiemTraQuyen(UidHT).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int role = Integer.parseInt(dataSnapshot.getValue().toString());
                    if(role == 1){
                        llSuaQuyen.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        adapterQuyen = new AdapterQuyen(ChiTietNhanVienActivity.this,R.layout .custom_layout_spinloaithucdon,quyenDTOS);
        spinQuyen.setAdapter(adapterQuyen);

        quyenDAO.layDanhSachQuyen().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quyenDTOS.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    QuyenDTO quyenDTO = d.getValue(QuyenDTO.class);
                    quyenDTO.setMaQuyen(d.getKey().toString());
                    quyenDTOS.add(quyenDTO);
                }
                adapterQuyen.notifyDataSetChanged();
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
            case R.id.btnXoaNhanVienCT:
                    if (user != null){
                        nhanVienDAO.kiemTraQuyen(UidHT).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int role = Integer.parseInt(dataSnapshot.getValue().toString());
                                if(role == 1){
                                    Intent iXacNhanXoa = new Intent(ChiTietNhanVienActivity.this,XacNhanXoaNhanVienActivity.class);
                                    iXacNhanXoa.putExtra("UID",UIDxoa);
                                    iXacNhanXoa.putExtra("UidHT",UidHT);
                                    startActivity(iXacNhanXoa);
                                } else {
                                    Toast.makeText(ChiTietNhanVienActivity.this, getResources().getString(R.string.khongcoquyenthuchien), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(ChiTietNhanVienActivity.this, getResources().getString(R.string.khongcoquyenthuchien), Toast.LENGTH_SHORT).show();
                    }


                break;

            case R.id.btnSuaQuyenNV:
                int vTri = spinQuyen.getSelectedItemPosition();
                String quyen = quyenDTOS.get(vTri).getMaQuyen();
                nhanVienDAO.suaQuyenNhanVien(UIDxoa,quyen);
                break;

            case  R.id.btnThoatCT:
                finish();
                break;
        }
    }


}
