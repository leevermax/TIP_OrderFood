package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity
{
    EditText edEmailDK, edMatKhauDK, edReMatKhauDK;
    Button btnDangKyDK;

    FirebaseAuth mAuth;
    DatabaseReference root;

    String email;
    String UIDnv,password;

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
        edReMatKhauDK = findViewById(R.id.edReMatKhauDK);
        btnDangKyDK = findViewById(R.id.btnDangKyDK);

        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("USERS");
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
        boolean ins = true;
        email = edEmailDK.getText().toString().trim();
        password = edMatKhauDK.getText().toString();
        String rePasswork = edReMatKhauDK.getText().toString();

        if (email != null && !email.equals("")){
            if (!password.equals(rePasswork)){
                Toast.makeText(DangKyActivity.this,getResources().getString(R.string.matkhaukhongtrung), Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    tiepTucDangKy();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthatbai),
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

            }
        } else {
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.emailempty),
                    Toast.LENGTH_SHORT).show();

        }

    }

    private void tiepTucDangKy(){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UIDnv = user.getUid().toString();

            Intent iThemThongTin = new Intent(DangKyActivity.this,ThemThongTinActivity.class);
            iThemThongTin.putExtra("matKhau",password);
            iThemThongTin.putExtra("idnv",UIDnv);
            iThemThongTin.putExtra("email",email);
            startActivity(iThemThongTin);


    }


}
