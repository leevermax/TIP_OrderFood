package com.tip.orderfood.FragmentApp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.CustomAdapter.AdapterNhaBep;
import com.tip.orderfood.DTO.NhaBepDTO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class NhaBepFragment extends Fragment {

    ListView lvMonAnBep;
    AdapterNhaBep adapterNhaBep;
    List<NhaBepDTO> nhaBepDTOS;

    DatabaseReference root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_nhabep,container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.danhsachmondagoi);

        lvMonAnBep = view.findViewById(R.id.lvMonAnBep);

        root = FirebaseDatabase.getInstance().getReference();
        nhaBepDTOS = new ArrayList<>();

        adapterNhaBep = new AdapterNhaBep(getActivity(),R.layout.layout_nhabep,nhaBepDTOS);
        lvMonAnBep.setAdapter(adapterNhaBep);

        root.child("ChiTietGoiMon").orderByChild("phucVu").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nhaBepDTOS.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    final NhaBepDTO nhaBepDTO = new NhaBepDTO();
                    nhaBepDTO.setMaCT(d.getKey().toString());
                    nhaBepDTO.setSoLuong(d.child("soLuong").getValue(Integer.class));
                    nhaBepDTO.setHoanThanh(d.child("hoanThanh").getValue(boolean.class));
                    nhaBepDTO.setPhucVu(d.child("hoanThanh").getValue(boolean.class));
                    final String maMonAn = d.child("maMonAn").getValue().toString();
                    String maGoiMon = d.child("maGoiMon").getValue().toString();
                    Log.d("magoimon",maGoiMon);
                    root.child("GoiMon").child(maGoiMon).child("maBan").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            root.child("Ban").child(dataSnapshot2.getValue().toString()).child("tenBan").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot3) {
                                    nhaBepDTO.setTenBan(dataSnapshot3.getValue().toString());
                                    root.child("MonAn").child(maMonAn).child("tenMonAn").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot4) {
                                            nhaBepDTO.setTenMonAn(dataSnapshot4.getValue().toString());
                                            nhaBepDTOS.add(nhaBepDTO);

                                            adapterNhaBep.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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

}
