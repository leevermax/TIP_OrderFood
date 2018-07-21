package com.tip.orderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ChiTietThongKeGoiMon extends AppCompatActivity {
    ListView lvThongKeChiTietGoiMon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongkechitietgoimon);

        lvThongKeChiTietGoiMon = findViewById(R.id.lvThongKeChiTietGoiMon);


    }


}
