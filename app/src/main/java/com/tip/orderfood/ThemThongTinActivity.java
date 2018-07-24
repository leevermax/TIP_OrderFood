package com.tip.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterQuyen;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DAO.QuyenDAO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.DTO.QuyenDTO;
import com.tip.orderfood.FragmentApp.DatePickerFragment;
import com.tip.orderfood.Support.SuaDuLieu;

import java.util.ArrayList;
import java.util.List;

public class ThemThongTinActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{
    EditText edHoTenDK, edNgaySinhDK,edCMNDDK,edSoDienThoaiDK;
    Button btnDongYDK;
    RadioGroup rgGioiTinh;
    Spinner spinQuyen;

    QuyenDAO quyenDAO;
    List<QuyenDTO> quyenDTOS;
    AdapterQuyen adapterQuyen;
    String sGioiTinh;
    RadioButton rdNam,rdNu;
    String UIDnv,email,matKhau,UidHT;
    NhanVienDAO nhanVienDAO;

    DatabaseReference root;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthongtindangky);

        addControls();
        addEvents();
    }

    private void addControls() {
        edHoTenDK = findViewById(R.id.edHoTenDK);
        edNgaySinhDK = findViewById(R.id.edNgaySinhDK);
        edSoDienThoaiDK = findViewById(R.id.edSoDienThoaiDK);
        edCMNDDK = findViewById(R.id.edCMNDDK);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        spinQuyen = findViewById(R.id.spinQuyen);

        quyenDAO = new QuyenDAO(this);
        quyenDTOS = new ArrayList<>();

        nhanVienDAO = new NhanVienDAO(this);



        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("Users");

        Intent intent = getIntent();
        UIDnv = intent.getStringExtra("idnv");
        email = intent.getStringExtra("email");
        matKhau = intent.getStringExtra("matKhau");
        UidHT = intent.getStringExtra("UidHT");
    }

    private void addEvents() {

        adapterQuyen = new AdapterQuyen(ThemThongTinActivity.this,R.layout .custom_layout_spinloaithucdon,quyenDTOS);
        spinQuyen.setAdapter(adapterQuyen);

        quyenDAO.layDanhSachQuyen().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quyenDTOS.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    QuyenDTO quyenDTO = d.getValue(QuyenDTO.class);
                    quyenDTO.setMaQuyen(d.getKey().toString());
                    quyenDTOS.add(quyenDTO);
                }
                adapterQuyen.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnDongYDK.setOnClickListener(this);
        edNgaySinhDK.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDK:
                themNV();
                dangNhapLaiVoiUID();
                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edNgaySinhDK:
                if(hasFocus){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(),"Ngày Sinh");
                }
                break;
        }
    }
    public void  themNV(){
        String sHoTen = edHoTenDK.getText().toString();
        SuaDuLieu suaDuLieu = new SuaDuLieu();
        sHoTen = suaDuLieu.toiUuChuoi(sHoTen);


        int vTri = spinQuyen.getSelectedItemPosition();
        String quyen = quyenDTOS.get(vTri).getMaQuyen();



        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;

            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sPhone =  edSoDienThoaiDK.getText().toString();
        String sNgaySinh = edNgaySinhDK.getText().toString();
        String sCMND = edCMNDDK.getText().toString();

        if(sHoTen == null || sHoTen.equals("")){
            Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
        }else {

            NhanVienDTO nhanVienDTO = new NhanVienDTO(quyen,email,matKhau,sCMND,sHoTen,sGioiTinh,sNgaySinh,sPhone);

            nhanVienDAO.addNV(UIDnv,nhanVienDTO);
        }


    }
    private void dangNhapLaiVoiUID(){
        root.child(UidHT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String matKhauHT,emailHT;
                matKhauHT = dataSnapshot.child("matKhau").getValue(String.class);
                emailHT = dataSnapshot.child("email").getValue(String.class);
                dangNhapLai(emailHT,matKhauHT);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void dangNhapLai(String emailHT, String matKhauHT){
        mAuth.signInWithEmailAndPassword(emailHT, matKhauHT)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent iTrangChu = new Intent(ThemThongTinActivity.this,TrangChuActivity.class);
                            iTrangChu.putExtra("keyThemNV",1);
                            startActivity(iTrangChu);
                        } else {
                            Toast.makeText(ThemThongTinActivity.this,getResources().getString(R.string.loidangnhaplai), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
