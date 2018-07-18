package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemThucDonActiivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class HienThiThucDonFragment extends android.support.v4.app.Fragment {

    GridView gvView;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon;
    NhanVienDAO nhanVienDAO;
    String maBan, maGoiMon;
    String Uid;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gvView = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDTOS = new ArrayList<>();
        nhanVienDAO = new NhanVienDAO(getActivity());
        user = FirebaseAuth.getInstance().getCurrentUser();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());

        adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_layout_hienloaimonan,loaiMonAnDTOS);
        gvView.setAdapter(adapterHienThiLoaiMonAnThucDon);
        loaiMonAnDAO.layDanhSachLoaiMonAn().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loaiMonAnDTOS.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    LoaiMonAnDTO loaiMonAnDTO = d.getValue(LoaiMonAnDTO.class);
                    loaiMonAnDTO.setMaLoai(d.getKey().toString());
                    loaiMonAnDTOS.add(loaiMonAnDTO);
                }
                adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        Bundle bDuLieuThucDon = getArguments();


        if (bDuLieuThucDon != null){
            maBan = bDuLieuThucDon.getString("maBan");
            maGoiMon = bDuLieuThucDon.getString("maGoiMon");
        }

        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maLoai = loaiMonAnDTOS.get(position).getMaLoai();

                HienThiDanhMonAnFragment hienThiDanhMonAnFragment = new HienThiDanhMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putString("maLoai",maLoai);
                bundle.putString("maBan",maBan);
                bundle.putString("maGoiMon",maGoiMon);
                hienThiDanhMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content,hienThiDanhMonAnFragment).addToBackStack("hienthiloai");
                transaction.commit();
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
                        MenuItem itThemBanAn = menuOnFire.add(1,R.id.itThemThucDon,1,R.string.themthucdon);
                        itThemBanAn.setIcon(R.drawable.logodangnhap);
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
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActiivity.class);
                startActivity(iThemThucDon);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
