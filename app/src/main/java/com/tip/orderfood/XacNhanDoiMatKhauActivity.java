package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.Support.EnDeCryption;

public class XacNhanDoiMatKhauActivity extends AppCompatActivity {

    Button btnXacNhanDoiMatKhau;
    String pass;
    String Uid;
    FirebaseUser user;
    DatabaseReference root;
    EnDeCryption enDeCryption;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhansuataikhoan);

        btnXacNhanDoiMatKhau = findViewById(R.id.btnXacNhanDoiMatKhau);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
        root = root = FirebaseDatabase.getInstance().getReference().child("Users");

        enDeCryption = new EnDeCryption();
        nhanVienDAO = new NhanVienDAO(this);

        Intent intent = getIntent();
        if (intent != null)
            pass = intent.getStringExtra("pass");
        final String enPass = enDeCryption.enCrypt(pass);

        btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updatePassword(pass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                nhanVienDAO.doiMatKhau(Uid,enPass);
                                Toast.makeText(XacNhanDoiMatKhauActivity.this, getResources().getString(R.string.doimatkhauthanhcong), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
            }

        });


    }
}
