package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.Support.EnDeCryption;

public class XacNhanXoaNhanVienActivity extends AppCompatActivity {

    TextView txtXacNhanXoaNhanVien;
    Button btnXacNhanXoaNhanVien;

    String UIDxoa,UidHT;

    GoiMonDAO goiMonDAO;

    DatabaseReference root;

    FirebaseUser user, userXoa;

    FirebaseAuth mAuth;
    NhanVienDAO nhanVienDAO;
    EnDeCryption enDeCryption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanxoanhanvien);

        btnXacNhanXoaNhanVien = findViewById(R.id.btnXacNhanXoaNhanVien);
        txtXacNhanXoaNhanVien = findViewById(R.id.txtXacNhanXoaNhanVien);

        nhanVienDAO = new NhanVienDAO(this);
        goiMonDAO = new GoiMonDAO(this);

        root = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        enDeCryption = new EnDeCryption();


        Intent intent = getIntent();
        UIDxoa = intent.getStringExtra("UID");
        UidHT = intent.getStringExtra("UidHT");

        nhanVienDAO.layNhanVien(UIDxoa).getRef().child("hoTen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtXacNhanXoaNhanVien.setText(getResources().getString(R.string.xoanhanvien)+" "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        btnXacNhanXoaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            goiMonDAO.xoaGoiMMonTheoNV(UIDxoa);
            xoaUserVoiUid();

            }
        });

    }



    private void xoaUserVoiUid() {
        root.child(UIDxoa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String emailXoa = dataSnapshot.child("email").getValue(String.class);
                String matKhauXoa = dataSnapshot.child("matKhau").getValue(String.class);
                matKhauXoa = enDeCryption.deCrypt(matKhauXoa);
                dangNhap(emailXoa,matKhauXoa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void dangNhap(String emailXoa, String matKhauXoa) {
        if (emailXoa != null && matKhauXoa != null){
            mAuth.signInWithEmailAndPassword(emailXoa, matKhauXoa)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userXoa = FirebaseAuth.getInstance().getCurrentUser();
                                userXoa.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    nhanVienDAO.xoaNhanVien(UIDxoa);

                                                    root.child(UidHT).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            String matKhauHT,emailHT;
                                                            matKhauHT = dataSnapshot.child("matKhau").getValue(String.class);

                                                            matKhauHT = enDeCryption.deCrypt(matKhauHT);
                                                            Log.d("matKhau",matKhauHT);
                                                            emailHT = dataSnapshot.child("email").getValue(String.class);
                                                            dangNhapLai(emailHT,matKhauHT);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(XacNhanXoaNhanVienActivity.this,getResources().getString(R.string.loidangnhaplai), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
    private void dangNhapLai(String emailHT, String matKhauHT) {
        if (emailHT != null && matKhauHT != null){
            mAuth.signInWithEmailAndPassword(emailHT, matKhauHT)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(XacNhanXoaNhanVienActivity.this, getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                                Intent iTrangChu = new Intent(XacNhanXoaNhanVienActivity.this,TrangChuActivity.class);
                                iTrangChu.putExtra("keyThemNV",1);
                                startActivity(iTrangChu);
                            } else {
                                Toast.makeText(XacNhanXoaNhanVienActivity.this,getResources().getString(R.string.loidangnhaplai), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

}
