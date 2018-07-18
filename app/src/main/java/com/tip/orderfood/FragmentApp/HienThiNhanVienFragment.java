package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterNhanVien;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.DangKyActivity;
import com.tip.orderfood.DangNhapActivity;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemThucDonActiivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class HienThiNhanVienFragment extends Fragment
{

    ListView lvViewNhanVien;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOS;
    AdapterNhanVien adapterNhanVien;

    String emailHT,matKhauHT;

    String Uid;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);
        lvViewNhanVien = view.findViewById(R.id.lvViewNhanVien);
        nhanVienDTOS = new ArrayList<>();
        nhanVienDAO = new NhanVienDAO(getActivity());

        user = FirebaseAuth.getInstance().getCurrentUser();

        adapterNhanVien = new AdapterNhanVien(getActivity(),R.layout.custom_layout_hienthinhanvien,nhanVienDTOS);
        lvViewNhanVien.setAdapter(adapterNhanVien);

        nhanVienDAO.layDanhSachNhanhVien().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    NhanVienDTO nhanVienDTO = d.getValue(NhanVienDTO.class);
                    nhanVienDTO.setUid(d.getKey());
                    nhanVienDTOS.add(nhanVienDTO);
                }
                adapterNhanVien.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle bundle = getArguments();
        if(bundle !=  null){
            emailHT = bundle.getString("emailHT");
            matKhauHT = bundle.getString("matKhauHT");
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final Menu menuOnFire =  menu;
        if (user != null){
            Uid = user.getUid().toString();
            nhanVienDAO.kiemTraQuyen(Uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int role = Integer.parseInt(dataSnapshot.getValue().toString());
                    if(role == 1){
                        MenuItem itThemBanAn = menuOnFire.add(1,R.id.itThemNhanVien,1,R.string.themnhanvien);
                        itThemBanAn.setIcon(R.drawable.themnhanvien);
                        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent iThemNhanVien = new Intent(getActivity(), DangKyActivity.class);
                iThemNhanVien.putExtra("emailHT",emailHT);
                iThemNhanVien.putExtra("matKhauHT",matKhauHT);
                startActivity(iThemNhanVien);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
