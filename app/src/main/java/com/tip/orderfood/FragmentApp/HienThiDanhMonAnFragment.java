package com.tip.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterDanhSachMonAn;
import com.tip.orderfood.DAO.MonAnDAO;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.SoLuongActivity;
import com.tip.orderfood.SuaMonAnActivity;
import com.tip.orderfood.XacNhanXoaMonAnActivity;

import java.util.ArrayList;
import java.util.List;

public class HienThiDanhMonAnFragment extends android.support.v4.app.Fragment {

    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOS;
    AdapterDanhSachMonAn adapterDanhSachMonAn;
    String maBan, maGoiMon;
    NhanVienDAO nhanVienDAO;
    String Uid;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);

        gridView = view.findViewById(R.id.gvHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());
        monAnDTOS = new ArrayList<>();

        nhanVienDAO = new NhanVienDAO(getActivity());
        user = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getArguments();
        if(bundle !=  null){
            maGoiMon = bundle.getString("maGoiMon");
            final String maloai = bundle.getString("maLoai");
            maBan = bundle.getString("maBan");
            adapterDanhSachMonAn = new AdapterDanhSachMonAn(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,monAnDTOS);
            gridView.setAdapter(adapterDanhSachMonAn);
            monAnDAO.LayDanhSachMonAnTheoLoai(maloai).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        MonAnDTO monAnDTO = d.getValue(MonAnDTO.class);
                        monAnDTO.setMaMonAn(d.getKey().toString());
                        monAnDTOS.add(monAnDTO);
                    }
                    registerForContextMenu(gridView);
                    adapterDanhSachMonAn.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maBan != null ){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maBan",maBan);
                        iSoLuong.putExtra("maMonAn",monAnDTOS.get(position).getMaMonAn());
                        iSoLuong.putExtra("maGoiMon",maGoiMon);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.memu_context,menu);
        menu.setHeaderTitle(R.string.menu);
//        menu.setHeaderIcon();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String maMonAn = monAnDTOS.get(info.position).getMaMonAn();
        switch (item.getItemId()){
            case R.id.itSua:
                if (user != null){
                    Uid = user.getUid().toString();
                    nhanVienDAO.kiemTraQuyen(Uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int role = Integer.parseInt(dataSnapshot.getValue().toString());
                            if(role == 1 || role == 2){
                                Intent iSuaMonAn = new Intent(getActivity(), SuaMonAnActivity.class);
                                iSuaMonAn.putExtra("maMonAn",maMonAn);
                                startActivity(iSuaMonAn);
                            } else {
                                Toast.makeText(getContext(), R.string.khongcoquyenthuchien, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.khongcoquyenthuchien, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.itXoa:
                Intent iXoaMonAn = new Intent(getActivity(), XacNhanXoaMonAnActivity.class);
                iXoaMonAn.putExtra("maMonAn",maMonAn);
                startActivity(iXoaMonAn);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
