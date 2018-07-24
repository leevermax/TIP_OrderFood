package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tip.orderfood.DAO.MonAnDAO;

public class XacNhanXoaMonAnActivity extends AppCompatActivity {


    Button btnXacNhanXoaMonAn;
    String maMonAn;

    MonAnDAO monAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanxoamonan);

        btnXacNhanXoaMonAn = findViewById(R.id.btnXacNhanXoaMonAn);
        monAnDAO = new MonAnDAO(this);

        Intent iXoaMonAn = getIntent();
        maMonAn = iXoaMonAn.getStringExtra("maMonAn");

        btnXacNhanXoaMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAnDAO.xoaMonAn(maMonAn);
                finish();
            }
        });
    }
}
