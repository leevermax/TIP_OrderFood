package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.NhanVienDTO;

public class ChiTietNhanVienActivity extends AppCompatActivity implements View.OnClickListener {


    TextView txtTenNhanVienCT,txtEmailCT,txtSDTCT,txtQuyenCT,txtNgaySinhCT,txtGioiTinhCT,txtCMNDCT;
    Button btnThoatCT,btnXoaNhanVienCT;

    String UIDxoa,UidHT;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    DatabaseReference root;

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


        nhanVienDAO = new NhanVienDAO(this);

        root = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
           UidHT = user.getUid().toString();

        Intent intent = getIntent();
        UIDxoa = intent.getStringExtra("UID");
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

            case  R.id.btnThoatCT:
                finish();
                break;
        }
    }


}
