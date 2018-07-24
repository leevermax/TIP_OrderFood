package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuBanAnActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtXoaBanAn, txtSuaBanAn;
    String maBan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_banan);

        txtSuaBanAn = findViewById(R.id.txtSuaBanAn);
        txtXoaBanAn = findViewById(R.id.txtXoaBanAn);

        Intent iMenu = getIntent();
        maBan = iMenu.getStringExtra("maBan");

        txtSuaBanAn.setOnClickListener(this);
        txtXoaBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.txtSuaBanAn:
                Intent iSuaBanAn = new Intent(MenuBanAnActivity.this, SuaBanAnActivity.class);
                iSuaBanAn.putExtra("maBan",maBan);
                startActivity(iSuaBanAn);
                finish();
                break;
            case R.id.txtXoaBanAn:
                Intent iXacNhanXoa = new Intent(MenuBanAnActivity.this,XacNhanXoaBanAnActivity.class);
                iXacNhanXoa.putExtra("maBan",maBan);
                startActivity(iXacNhanXoa);
                finish();
                break;
        }
    }
}
