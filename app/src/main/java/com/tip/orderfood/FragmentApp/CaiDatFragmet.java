package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;
import com.tip.orderfood.XacNhanDoiEmailActiviry;
import com.tip.orderfood.XacNhanDoiMatKhauActivity;
import com.tip.orderfood.XacNhanDoiSDTActivity;

public class CaiDatFragmet extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{

    TextView txtTenNhanVienCD;
    EditText edMatKhauCD,edReMatKhauCD,edEmailCD,edSDTlCD;
    Button btnDoiMatKhau,btnDoiEmail,btnDoiSDT;

    String Uid;
    FirebaseUser user;

    NhanVienDAO nhanVienDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_caidat,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.caidat);
        txtTenNhanVienCD = view.findViewById(R.id.txtTenNhanVienCD);
        edMatKhauCD = view.findViewById(R.id.edMatKhauCD);
        edReMatKhauCD = view.findViewById(R.id.edReMatKhauCD);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);

        edEmailCD = view.findViewById(R.id.edEmailCD);
        edSDTlCD = view.findViewById(R.id.edSDTlCD);
        btnDoiEmail = view.findViewById(R.id.btnDoiEmail);
        btnDoiSDT = view.findViewById(R.id.btnDoiSDT);


        user = FirebaseAuth.getInstance().getCurrentUser();
        nhanVienDAO = new NhanVienDAO(getActivity());

        if (user != null) {
            Uid = user.getUid().toString();
            nhanVienDAO.layNhanVien(Uid).getRef().child("hoTen").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    txtTenNhanVienCD.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        btnDoiMatKhau.setVisibility(View.GONE);
        btnDoiEmail.setVisibility(View.GONE);
        btnDoiSDT.setVisibility(View.GONE);

        edMatKhauCD.setOnFocusChangeListener(this);
        edEmailCD.setOnFocusChangeListener(this);
        edSDTlCD.setOnFocusChangeListener(this);

        btnDoiMatKhau.setOnClickListener(this);
        btnDoiEmail.setOnClickListener(this);
        btnDoiSDT.setOnClickListener(this);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("trangchu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDoiMatKhau:
                String pass = edMatKhauCD.getText().toString();
                String rePass = edReMatKhauCD.getText().toString();
                if (!pass.isEmpty() && pass.equals(rePass)){
                    Intent iXacNhanDoiMatKhau = new Intent(getActivity(), XacNhanDoiMatKhauActivity.class);
                    iXacNhanDoiMatKhau.putExtra("pass",pass);
                    getActivity().startActivity(iXacNhanDoiMatKhau);

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.matkhaukhongtrung), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDoiEmail:
                String email = edEmailCD.getText().toString();
                if (!email.isEmpty()){
                    Intent iDoiEEmail = new Intent(getActivity(), XacNhanDoiEmailActiviry.class);
                    iDoiEEmail.putExtra("email",email);
                    startActivity(iDoiEEmail);
                } else {
                    Toast.makeText(getActivity(),R.string.khongduocdetrong, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDoiSDT:
                String sdt = edSDTlCD.getText().toString();
                if (!sdt.isEmpty()){
                    Intent iDoiSDT = new Intent(getActivity(), XacNhanDoiSDTActivity.class);
                    iDoiSDT.putExtra("sdt",sdt);
                    startActivity(iDoiSDT);
                } else {
                    Toast.makeText(getActivity(),R.string.khongduocdetrong, Toast.LENGTH_SHORT).show();
                }
                break;
    }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edMatKhauCD:
                if(hasFocus){
                    btnDoiMatKhau.setVisibility(View.VISIBLE);
                    btnDoiEmail.setVisibility(View.GONE);
                    btnDoiSDT.setVisibility(View.GONE);
                }
                break;
            case R.id.edEmailCD:
                if(hasFocus){
                    btnDoiEmail.setVisibility(View.VISIBLE);
                    btnDoiMatKhau.setVisibility(View.GONE);
                    btnDoiSDT.setVisibility(View.GONE);
                }
                break;
            case R.id.edSDTlCD:
                if(hasFocus){
                    btnDoiSDT.setVisibility(View.VISIBLE);
                    btnDoiMatKhau.setVisibility(View.GONE);
                    btnDoiEmail.setVisibility(View.GONE);
                }
                break;
        }
    }
}
