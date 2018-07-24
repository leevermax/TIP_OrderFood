package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.Support.SuaDuLieu;

public class SuaBanAnActivity extends AppCompatActivity {

    EditText edSuaTenBanAn;
    Button btnDongYSuaBanAn;

    String maBan;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);


        edSuaTenBanAn = findViewById(R.id.edSuaTenBanAn);
        btnDongYSuaBanAn = findViewById(R.id.btnDongYSuaBanAn);



        banAnDAO = new BanAnDAO(this);
        Intent iSuaBanAn = getIntent();
        maBan = iSuaBanAn.getStringExtra("maBan");



        btnDongYSuaBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenBan = edSuaTenBanAn.getText().toString();
                SuaDuLieu suaDuLieu = new SuaDuLieu();
                tenBan = suaDuLieu.toiUuChuoi(tenBan);
                if(!tenBan.isEmpty()){
                    banAnDAO.suaTenBan(maBan,tenBan);
                    finish();
                }else {
                    Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.nhaptenban), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
