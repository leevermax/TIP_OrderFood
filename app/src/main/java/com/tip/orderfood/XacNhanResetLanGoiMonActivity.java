package com.tip.orderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tip.orderfood.DAO.MonAnDAO;

public class XacNhanResetLanGoiMonActivity extends AppCompatActivity {

    Button btnXacNhanReset;
    MonAnDAO monAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanresetlangoimon);

        btnXacNhanReset = findViewById(R.id.btnXacNhanReset);

        monAnDAO = new MonAnDAO(this);

        btnXacNhanReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAnDAO.resetLanGoi();
                Toast.makeText(XacNhanResetLanGoiMonActivity.this, getResources().getString(R.string.resetgoimonthanhcong), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
