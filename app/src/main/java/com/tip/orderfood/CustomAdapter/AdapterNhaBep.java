package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.DTO.ChiTietGoiMonDTO;
import com.tip.orderfood.DTO.NhaBepDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;
import com.tip.orderfood.XacNhanPhucVuActivity;

import java.util.List;

public class AdapterNhaBep extends BaseAdapter {

    Context context;
    int layout;
    List<NhaBepDTO> nhaBepDTOS;
    FragmentManager fragmentManager;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("ChiTietGoiMon");
    ViewHolder viewHolder;

    String Uid;
    FirebaseUser user;

    public AdapterNhaBep(Context context, int layout, List<NhaBepDTO> nhaBepDTOS) {
        this.context = context;
        this.layout = layout;
        this.nhaBepDTOS = nhaBepDTOS;
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return nhaBepDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return nhaBepDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class  ViewHolder{
        TextView txtTenMonAnBep, txtSoLuongBep, txtTenBanAnBep;
        CheckBox cbHoanThanhBep, cbPhucVuBep;
    }
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.custom_layuotnhabep,viewGroup,false);
            viewHolder.txtTenBanAnBep = view.findViewById(R.id.txtTenBanAnBep);
            viewHolder.txtSoLuongBep = view.findViewById(R.id.txtSoLuongBep);
            viewHolder.txtTenMonAnBep = view.findViewById(R.id.txtTenMonAnBep);
            viewHolder.cbHoanThanhBep = view.findViewById(R.id.cbHoanThanhBep);
            viewHolder.cbPhucVuBep = view.findViewById(R.id.cbPhucVuBep);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final NhaBepDTO nhaBepDTO = nhaBepDTOS.get(i);

        if (nhaBepDTOS.get(i).isHoanThanh()){
            viewHolder.cbHoanThanhBep.setChecked(true);
        } else {
            viewHolder.cbHoanThanhBep.setChecked(false);
        }
        if(!nhaBepDTOS.get(i).isPhucVu()){
            viewHolder.cbPhucVuBep.setChecked(false);
        }
        viewHolder.txtTenMonAnBep.setText(nhaBepDTO.getTenMonAn());
        viewHolder.txtTenBanAnBep.setText(nhaBepDTO.getTenBan());
        viewHolder.txtSoLuongBep.setText(String.valueOf(nhaBepDTO.getSoLuong()) );


        user = FirebaseAuth.getInstance().getCurrentUser();
        final NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        if (user != null) {
            Uid = user.getUid().toString();


            viewHolder.cbHoanThanhBep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nhanVienDAO.kiemTraQuyen(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int role = Integer.parseInt(dataSnapshot.getValue().toString());
                            if (role == 1 || role == 2) {
                    if ( nhaBepDTOS.get(i).isHoanThanh() == false) {
                        root.child(nhaBepDTOS.get(i).getMaCT()).child("hoanThanh").setValue(true);
                    }
                    else if ( nhaBepDTO.isHoanThanh() == true){
                        root.child(nhaBepDTOS.get(i).getMaCT()).child("hoanThanh").setValue(false);
                    }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

            viewHolder.cbPhucVuBep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    final boolean k = b;
                    nhanVienDAO.kiemTraQuyen(Uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int role = Integer.parseInt(dataSnapshot.getValue().toString());
                            if (role == 1 || role == 3) {

                                if (k) {
                                    Intent intent = new Intent(context, XacNhanPhucVuActivity.class);
                                    intent.putExtra("maCT", nhaBepDTO.getMaCT());
                                    intent.putExtra("tenBan", nhaBepDTO.getTenBan());
                                    context.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        return view;
    }
}
