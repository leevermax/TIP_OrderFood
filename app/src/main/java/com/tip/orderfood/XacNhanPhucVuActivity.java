package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class XacNhanPhucVuActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnXacNhanPhucVu;
    TextView txtXacNhanPhucVu;
    String tenBan;
    String maCT;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanphucvu);

        Intent intent = getIntent();
        tenBan = intent.getStringExtra("tenBan");
        maCT = intent.getStringExtra("maCT");

        txtXacNhanPhucVu = findViewById(R.id.txtXacNhanPhucVu);
        btnXacNhanPhucVu = findViewById(R.id.btnXacNhanPhucVu);

        txtXacNhanPhucVu.setText(getResources().getString(R.string.xacnhanphucvu) + " " + tenBan);
        btnXacNhanPhucVu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        root.child(maCT).child("phucVu").setValue(true);

        finish();
    }
}
