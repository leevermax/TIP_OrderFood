package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterTopMonAn;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;
import com.tip.orderfood.XacNhanResetLanGoiMonActivity;
import com.tip.orderfood.XacNhanThanhToanActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopMonAnFragment extends Fragment{

    ListView lvTopMonAn;
    AdapterTopMonAn adapterTopMonAn;
    List<MonAnDTO> monAnDTOList;
    MonAnDAO monAnDAO;


    String Uid;
    FirebaseUser user;
    NhanVienDAO nhanVienDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_topmonan,container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.topmonan);
        setHasOptionsMenu(true);


        lvTopMonAn = view.findViewById(R.id.lvTopMonAn);


        nhanVienDAO = new NhanVienDAO(getActivity());

        user = FirebaseAuth.getInstance().getCurrentUser();

        monAnDTOList = new ArrayList<>();
        monAnDAO = new MonAnDAO(getActivity());

        adapterTopMonAn = new AdapterTopMonAn(getActivity(),R.layout.custom_layout_topmonan,monAnDTOList);
        lvTopMonAn.setAdapter(adapterTopMonAn);

        monAnDAO.LayDanhSachMonAn().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                monAnDTOList.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    MonAnDTO monAnDTO = d.getValue(MonAnDTO.class);
                    monAnDTO.setMaMonAn(d.getKey().toString());
                    monAnDTOList.add(monAnDTO);
                }
                Collections.sort(monAnDTOList);
                adapterTopMonAn.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("thongke", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                        MenuItem itThemBanAn = menuOnFire.add(1,R.id.itResetGoiMon,1,R.string.resetgoimon);
                        itThemBanAn.setIcon(R.drawable.resetlangoi);
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
            case R.id.itResetGoiMon:
                Intent iResetGoiMon = new Intent(getContext(), XacNhanResetLanGoiMonActivity.class);
                startActivity(iResetGoiMon);

                break;
        }
        return true;
    }
}
