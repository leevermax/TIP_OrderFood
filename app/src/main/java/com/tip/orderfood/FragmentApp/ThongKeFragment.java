package com.tip.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterThongKeGoiMon;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.ThanhToanDTO;
import com.tip.orderfood.DTO.ThongKeGoiMonDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.Support.TinhTienDTO;
import com.tip.orderfood.ThemBanAnActivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class ThongKeFragment extends Fragment {

    ListView lvThongKeGoiMon;

    List<ThongKeGoiMonDTO> thongKeGoiMonDTOList;
    AdapterThongKeGoiMon adapterThongKeGoiMon;
    DatabaseReference root;

    FragmentManager fragmentManager;

    String Uid;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_trangthongke,container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thongke);
        setHasOptionsMenu(true);

        fragmentManager = (getActivity()).getSupportFragmentManager();

        lvThongKeGoiMon = view.findViewById(R.id.lvThongKeGoiMon);
        nhanVienDAO = new NhanVienDAO(getActivity());
        root = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        thongKeGoiMonDTOList = new ArrayList<>();
        adapterThongKeGoiMon = new AdapterThongKeGoiMon(getActivity(),R.layout.custom_layout_thongke,thongKeGoiMonDTOList);
        lvThongKeGoiMon.setAdapter(adapterThongKeGoiMon);



        root.child("GoiMon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thongKeGoiMonDTOList.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    final ThongKeGoiMonDTO thongKeGoiMonDTO = new ThongKeGoiMonDTO();
                    thongKeGoiMonDTO.setMaGoiMonTK(d.getKey());
                    thongKeGoiMonDTO.setNgayGoiMon(d.child("ngayGoi").getValue().toString());

                    root.child("ChiTietGoiMon").orderByChild("maGoiMon").equalTo(d.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            final List<TinhTienDTO> tinhTienDTOList = new ArrayList<>();

                            for (DataSnapshot d2: dataSnapshot2.getChildren()){

                                final int soLuong = d2.child("soLuong").getValue(Integer.class);
                                String maMon = d2.child("maMonAn").getValue().toString();
                                final long k = dataSnapshot2.getChildrenCount();
                                root.child("MonAn").child(maMon).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
                                        TinhTienDTO tinhTienDTO = new TinhTienDTO();
                                        long tongTien = 0;
                                        tinhTienDTO.setSoLuong(soLuong);

                                        tinhTienDTO.setGiaTien(dataSnapshot3.child("giaTien").getValue(Integer.class));

                                        tinhTienDTOList.add(tinhTienDTO);


                                        if (tinhTienDTOList.size() == k){
                                            for ( TinhTienDTO tt: tinhTienDTOList){
                                                tongTien += (tt.getSoLuong())*(tt.getGiaTien());
                                            }
                                            thongKeGoiMonDTO.setTongTienTK(tongTien);
                                            thongKeGoiMonDTOList.add(thongKeGoiMonDTO);
                                            adapterThongKeGoiMon.notifyDataSetChanged();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError2) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                    if(role == 1 || role == 2){
                        MenuItem itThemBanAn = menuOnFire.add(1,R.id.itTopMonAn,1,R.string.topmonan);
                        itThemBanAn.setIcon(R.drawable.topmonan);
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
            case R.id.itTopMonAn:
                FragmentTransaction tranTopMonAn = fragmentManager.beginTransaction();
                TopMonAnFragment topMonAnFragment = new TopMonAnFragment();
                tranTopMonAn.replace(R.id.content,topMonAnFragment).addToBackStack("thongke");
                tranTopMonAn.commit();
                break;
        }
        return true;
    }
}
