package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.Support.SuaDuLieu;

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
        SuaDuLieu suaDuLieu = new SuaDuLieu();
        sTenBanAn = suaDuLieu.toiUuChuoi(sTenBanAn);
        BanAnDTO banAnDTO = new BanAnDTO(sTenBanAn,false);
        if(sTenBanAn != null && !sTenBanAn.equals("")){
            banAnDAO.themBanAn(banAnDTO);
            Intent intent = new Intent();
            intent.putExtra("ketQuaThem",true);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
