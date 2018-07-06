package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tip.orderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemThucDonActiivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends android.support.v4.app.Fragment {

    GridView gvView;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maBan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gvView = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        loaiMonAnDTOS = loaiMonAnDAO.layDanhSachLoaiMonAn();

        AdapterHienThiLoaiMonAnThucDon adapter = new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_layout_hienloaimonan,loaiMonAnDTOS);
        gvView.setAdapter(adapter);
        adapter.notifyDataSetInvalidated();

        Bundle bDuLieuThucDon = getArguments();


        if (bDuLieuThucDon != null){
            maBan = bDuLieuThucDon.getInt("maBan");
        }

        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maLoai = loaiMonAnDTOS.get(position).getMaLoai();

                HienThiDanhMonAnFragment hienThiDanhMonAnFragment = new HienThiDanhMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("maLoai",maLoai);
                bundle.putInt("maBan",maBan);
                hienThiDanhMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content,hienThiDanhMonAnFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }

        });




        return view;
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1,R.id.itThemThucDon,1,R.string.themthucdon);
        itThemBanAn.setIcon(R.drawable.logodangnhap);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
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
