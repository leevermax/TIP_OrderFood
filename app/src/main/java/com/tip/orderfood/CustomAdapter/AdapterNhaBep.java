package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.custom_layuotnhabep,viewGroup,false);
            viewHolder.txtTenBanAnBep = view.findViewById(R.id.txtTenMonAnBep);
            viewHolder.txtSoLuongBep = view.findViewById(R.id.txtSoLuongBep);
            viewHolder.txtTenMonAnBep = view.findViewById(R.id.txtTenMonAnBep);
            viewHolder.cbHoanThanhBep = view.findViewById(R.id.cbHoanThanhBep);
            viewHolder.cbPhucVuBep = view.findViewById(R.id.cbPhucVuBep);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final NhaBepDTO nhaBepDTO = nhaBepDTOS.get(i);
        if (nhaBepDTO.isHoanThanh()){
            viewHolder.cbHoanThanhBep.isChecked();

        }
        viewHolder.txtTenMonAnBep.setText(nhaBepDTO.getTenMonAn());
        viewHolder.txtTenBanAnBep.setText(nhaBepDTO.getTenBan());
        viewHolder.txtSoLuongBep.setText(nhaBepDTO.getSoLuong());


        viewHolder.cbHoanThanhBep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    root.child(nhaBepDTO.getMaCT()).child("hoanThanh").setValue(true);
                } else {
                    root.child(nhaBepDTO.getMaCT()).child("hoanThanh").setValue(false);
                }
            }
        });

        viewHolder.cbPhucVuBep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Intent intent = new Intent(context, XacNhanPhucVuActivity.class);
                    intent.putExtra("maCT",nhaBepDTO.getMaCT());
                    intent.putExtra("tenBan", nhaBepDTO.getTenBan());
                    context.startActivity(intent);
                    root.child(nhaBepDTO.getMaCT()).child("hoanThanh").setValue(true);
                }
            }
        });

        return view;
    }
}
