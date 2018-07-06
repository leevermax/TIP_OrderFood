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

public class DangKyActivity extends AppCompatActivity
{
    EditText edEmailDK, edMatKhauDK;
    Button btnDangKyDK;
    String UIDnv = "";
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        addControls();
        addEvents();
    }

    private void addControls() {
        edMatKhauDK = findViewById(R.id.edMatKhauDK);
        edEmailDK = findViewById(R.id.edEmailDK);
        btnDangKyDK = findViewById(R.id.btnDangKyDK);

        mAuth = FirebaseAuth.getInstance();
    }
    private void addEvents() {
        btnDangKyDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();

            }
        });

    }
    private void dangKy(){
        String email = edEmailDK.getText().toString();
        String password = edMatKhauDK.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent iThemThongTin = new Intent(DangKyActivity.this,ThemThongTinActivity.class);

                            iThemThongTin.putExtra("UIDNhanVien",UIDnv);
                            startActivity(iThemThongTin);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthatbai),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}
