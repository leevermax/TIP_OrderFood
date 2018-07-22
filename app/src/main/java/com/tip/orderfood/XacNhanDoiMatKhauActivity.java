package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class XacNhanDoiMatKhauActivity extends AppCompatActivity {

    Button btnXacNhanDoiMatKhau;
    String pass;
    String Uid;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhandoimatkhau);

        btnXacNhanDoiMatKhau = findViewById(R.id.btnXacNhanDoiMatKhau);

        user = FirebaseAuth.getInstance().getCurrentUser();


        Intent intent = getIntent();
        if (intent != null)
            pass = intent.getStringExtra("pass");

        btnXacNhanDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.updatePassword(pass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(XacNhanDoiMatKhauActivity.this, getResources().getString(R.string.doimatkhauthanhcong), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });


    }
}
