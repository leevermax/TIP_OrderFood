package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tip.orderfood.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.SoLuongActivity;

import java.util.List;

public class HienThiDanhMonAnFragment extends android.support.v4.app.Fragment {

    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOS;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maBan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);

        gridView = view.findViewById(R.id.gvHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if(bundle !=  null){
            int maloai = bundle.getInt("maLoai");
            maBan = bundle.getInt("maBan");
            monAnDTOS = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);
            adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,monAnDTOS);
            gridView.setAdapter(adapterHienThiDanhSachMonAn);
            adapterHienThiDanhSachMonAn.notifyDataSetChanged();


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maBan !=0 ){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maBan",maBan);
                        iSoLuong.putExtra("maMonAn",monAnDTOS.get(position).getMaMonAn());
                        startActivity(iSoLuong);
                    }

                }
            });

        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        return view;
    }

}
