package com.tip.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterHienThiBanAn;
import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.ThemBanAnActivity;
import com.tip.orderfood.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int RESQUEST_CODE_THEM= 111;
    GridView gvHienThiBanAn;
    List<BanAnDTO> banAnDTOList;
    BanAnDAO banAnDAO;
    NhanVienDAO nhanVienDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;
    String Uid;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,container,false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trangchu);



        setHasOptionsMenu(true);

        gvHienThiBanAn = view.findViewById(R.id.gvHienBanAn);
        banAnDAO = new BanAnDAO(getActivity());
        nhanVienDAO = new NhanVienDAO(getActivity());
        user = FirebaseAuth.getInstance().getCurrentUser();


        banAnDTOList = new ArrayList<>();


        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(),R.layout.custom_layout_hienthibanan,banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        banAnDAO.getlistban().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                banAnDTOList.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    BanAnDTO banAnDTO = d.getValue(BanAnDTO.class);
                    banAnDTO.setMaBan(d.getKey().toString());
                    banAnDTOList.add(banAnDTO);
                }


                registerForContextMenu(gvHienThiBanAn);
                adapterHienThiBanAn.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                    if(role == 1 || role == 3){
                        MenuItem itThemBanAn = menuOnFire.add(1,R.id.itThemBanAn,1,R.string.thembanan);
                        itThemBanAn.setIcon(R.drawable.thembanan);
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
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,RESQUEST_CODE_THEM);

                break;
        }
        return true;
    }

    private void hienThiDanhSachBanAn(){


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.memu_context,menu);
        menu.setHeaderTitle(R.string.menu);
//        menu.setHeaderIcon();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int vTri = gvHienThiBanAn.getSelectedItemPosition();
        String maBan = banAnDTOList.get(vTri).getMaBan();
        switch (item.getItemId()){
            case R.id.itSua:

                break;
            case R.id.itXoa:

                break;
        }
        return super.onContextItemSelected(item);
    }
}
