package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.NhanVienDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterNhanVien extends BaseAdapter {
    Context context;
    int layout;
    List<NhanVienDTO> nhanVienDTOS;
    ViewHolderNhanVien viewHolderNhanVien;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public AdapterNhanVien(Context context, int layout, List<NhanVienDTO> nhanVienDTOS) {
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOS = nhanVienDTOS;
    }

    @Override
    public int getCount() {
        return nhanVienDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderNhanVien{
        ImageView imHinhNhanVien;
        TextView txtTenNhanVien,txtViTri,txtEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderNhanVien.imHinhNhanVien = (ImageView) view.findViewById(R.id.imHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = (TextView) view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtViTri = (TextView) view.findViewById(R.id.txtViTri);

            viewHolderNhanVien.txtEmail = (TextView) view.findViewById(R.id.txtEmail);

            view.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOS.get(position);

        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getHoTen());
        viewHolderNhanVien.txtEmail.setText(nhanVienDTO.getEmail());
        root.child("Quyen").child(nhanVienDTO.getMaQuyen()).child("tenQuyen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                viewHolderNhanVien.txtViTri.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
