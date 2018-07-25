package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tip.orderfood.DAO.NhanVienDAO;

public class XacNhanDoiSDTActivity extends AppCompatActivity {


    Button btnXacNhanDoiMatKhau;
    String sdt;
    String Uid;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhansuataikhoan);

        btnXacNhanDoiMatKhau = findViewById(R.id.btnXacNhanDoiMatKhau);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();


        nhanVienDAO = new NhanVienDAO(this);

        Intent intent = getIntent();
        if (intent != null)
            sdt = intent.getStringExtra("sdt");

        btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhanVienDAO.doiSDT(Uid,sdt);
                finish();
            }
        });

    }
}
