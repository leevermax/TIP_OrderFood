package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.Support.EnDeCryption;

public class XacNhanDoiEmailActiviry extends AppCompatActivity {

    Button btnXacNhanDoiMatKhau;
    String email;
    String Uid;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    EnDeCryption enDeCryption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhansuataikhoan);

        btnXacNhanDoiMatKhau = findViewById(R.id.btnXacNhanDoiMatKhau);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();


        nhanVienDAO = new NhanVienDAO(this);
        enDeCryption = new EnDeCryption();

        Intent intent = getIntent();
        if (intent != null)
            email = intent.getStringExtra("email");



        btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhanVienDAO.layNhanVien(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String matKhau = enDeCryption.deCrypt(dataSnapshot.child("matKhau").getValue().toString());
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(dataSnapshot.child("email").getValue().toString(),matKhau);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.updateEmail(email)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            nhanVienDAO.doiEmail(Uid,email);
                                                            Toast.makeText(XacNhanDoiEmailActiviry.this, R.string.thaydoithanhcong, Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
