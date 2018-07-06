package com.tip.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.tip.orderfood.CustomAdapter.AdapterHienThiBanAn;
import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemBanAnActivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int RESQUEST_CODE_THEM= 111;
    GridView gvHienThiBanAn;
    List<BanAnDTO> banAnDTOList;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trangchu);
        setHasOptionsMenu(true);

        gvHienThiBanAn = view.findViewById(R.id.gvHienBanAn);
        banAnDAO = new BanAnDAO(getActivity());
        hienThiDanhSachBanAn();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        int k=1;
        if (k != 1){
            MenuItem itThemBanAn = menu.add(1,R.id.itThemBanAn,1,R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,RESQUEST_CODE_THEM);

                break;
        }
        return true;
    }

    private void hienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.layTatCaBanAn();

        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(),R.layout.custom_layout_hienthibanan,banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_THEM){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kTra = intent.getBooleanExtra("ketQuaThem",false);
                if (kTra){
                    hienThiDanhSachBanAn();
                    Toast.makeText(getActivity(),getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
