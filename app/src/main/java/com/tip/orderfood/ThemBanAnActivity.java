package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tip.orderfood.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edThemTenBanAn;
    Button btnDongYThemBanAn;
    BanAnDAO banAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        addControls();
        addEvent();
    }

    private void addControls() {
        edThemTenBanAn = findViewById(R.id.edThemTenBanAn);
        btnDongYThemBanAn = findViewById(R.id.btnDongYThemBanAn);
        banAnDAO = new BanAnDAO(this);
    }

    private void addEvent() {
        btnDongYThemBanAn.setOnClickListener(this   );
    }

    @Override
    public void onClick(View v) {
        String sTenBanAn = edThemTenBanAn.getText().toString();
        if(sTenBanAn != null || sTenBanAn.equals("")){
            boolean kTra = banAnDAO.themBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketQuaThem",kTra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
