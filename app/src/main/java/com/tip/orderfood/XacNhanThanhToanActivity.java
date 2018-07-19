package com.tip.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class XacNhanThanhToanActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtThongBaoThanhToan;
    Button btnXacNhanThanhToan;
    String maBan;

    DatabaseReference root;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xacnhanthanhtoan);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXacNhanThanhToan.setOnClickListener(this);
    }

    private void addControls() {
        txtThongBaoThanhToan = findViewById(R.id.txtThongBaoThanhToan);
        btnXacNhanThanhToan = findViewById(R.id.btnXacNhanThanhToan);

        root = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        maBan = intent.getStringExtra("maBan");
        root.child("Ban").child(maBan).child("tenBan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tenBan = dataSnapshot.getValue().toString();
                txtThongBaoThanhToan.setText(getResources().getString(R.string.thongbaoxacnhanthanhtoan)+" "+tenBan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        final String UID = user.getUid().toString();
        root.child("GoiMon").orderByChild("maBan").equalTo(maBan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String maGoiMon = "";

                for (DataSnapshot d: dataSnapshot.getChildren()){
                    if (d.child("tinhTrang").getValue(boolean.class) == false) {
                        maGoiMon = d.getKey();
                        break;
                    }
                }

                root.child("GoiMon").child(maGoiMon).child("tinhTrang").setValue(true);
                root.child("GoiMon").child(maGoiMon).child("maNhanVien").setValue(UID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.child("Ban").child(maBan).child("tinhTrang").setValue(false);

//        Intent veTrangChu = new Intent(XacNhanThanhToanActivity.this,TrangChuActivity.class);
//        startActivity(veTrangChu);
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();

    }
}
