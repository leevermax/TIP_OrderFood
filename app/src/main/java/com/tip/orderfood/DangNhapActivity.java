package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tip.orderfood.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDongYDN, btnDangKyDN;
    EditText edEmailDN, edMatKhauDN;
    NhanVienDAO nhanVienDAO;
    FirebaseAuth mAuth;

    String UID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);


        addControls();
        addEvents();
    }


    private void addEvents() {
        showButton();
        btnDangKyDN.setOnClickListener(this);
        btnDongYDN.setOnClickListener(this);
    }

    private void addControls() {
        btnDangKyDN = findViewById(R.id.btnDangKyDN);
        btnDongYDN = findViewById(R.id.btnDongYDN);
        edMatKhauDN = findViewById(R.id.edMatKhauDN);
        edEmailDN = findViewById(R.id.edEmailDN);
        nhanVienDAO = new NhanVienDAO(this);

        mAuth = FirebaseAuth.getInstance();
    }
    private void showButton(){

        btnDangKyDN.setVisibility(View.GONE);
//        if(kiemTra){
//
//            btnDongYDN.setVisibility(View.VISIBLE);
//        } else {
//            btnDongYDN.setVisibility(View.GONE);
//            btnDangKyDN.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        showButton();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDangKyDN:
                btnDangKyDN();
                break;

            case R.id.btnDongYDN:
                btnDongYDN();
                break;
        }
    }

    private void btnDangKyDN() {
        Intent iDangKy = new Intent(DangNhapActivity.this,ThemThongTinActivity.class);
        startActivity(iDangKy);
    }

    private void btnDongYDN() {
        final String sTenDN = edEmailDN.getText().toString();
        final String sMatKhau  = edMatKhauDN.getText().toString();

        mAuth.signInWithEmailAndPassword(sTenDN, sMatKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent iTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
                            iTrangChu.putExtra("emailHT",sTenDN);
                            iTrangChu.putExtra("matKhauHT",sMatKhau);
                            startActivity(iTrangChu);
                            overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                        } else {

                            Toast.makeText(DangNhapActivity.this, getResources().getString(R.string.dangnhapthatbai),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
