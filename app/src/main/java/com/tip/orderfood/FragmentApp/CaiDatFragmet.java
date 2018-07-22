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
import com.tip.orderfood.XacNhanDoiMatKhauActivity;

public class CaiDatFragmet extends Fragment {

    TextView txtTenNhanVienCD;
    EditText edMatKhauCD,edReMatKhauCD;
    Button btnDoiMatKhau;

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


        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edMatKhauCD.getText().toString();
                String rePass = edReMatKhauCD.getText().toString();
                if (!pass.isEmpty() && pass.equals(rePass)){
                    Intent iXacNhanDoiMatKhau = new Intent(getActivity(), XacNhanDoiMatKhauActivity.class);
                    iXacNhanDoiMatKhau.putExtra("pass",pass);
                    getActivity().startActivity(iXacNhanDoiMatKhau);

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.matkhaukhongtrung), Toast.LENGTH_SHORT).show();
                }
            }
        });


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
}
