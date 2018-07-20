package com.tip.orderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class XacNhanPhucVuActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnXacNhanPhucVu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanphucvu);

        btnXacNhanPhucVu = findViewById(R.id.btnXacNhanPhucVu);
        btnXacNhanPhucVu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
