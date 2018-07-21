package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.DTO.ThongKeGoiMonDTO;
import com.tip.orderfood.R;
import com.tip.orderfood.ThongKeChiTietActivity;

import java.util.List;

public class AdapterThongKeGoiMon extends BaseAdapter {

    Context context;
    int layout;
    List<ThongKeGoiMonDTO> thongKeGoiMonDTOList;

    ViewHolder viewHolder;

    public AdapterThongKeGoiMon(Context context, int layout, List<ThongKeGoiMonDTO> thongKeGoiMonDTOList) {
        this.context = context;
        this.layout = layout;
        this.thongKeGoiMonDTOList = thongKeGoiMonDTOList;
    }

    @Override
    public int getCount() {
        return thongKeGoiMonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thongKeGoiMonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class  ViewHolder{
        TextView txtMaGoiThongKe, txtNgayGoiThongKe, txtTongTienThongKe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.custom_layout_thongke,parent,false);
            viewHolder.txtMaGoiThongKe = view.findViewById(R.id.txtMaGoiThongKe);
            viewHolder.txtNgayGoiThongKe = view.findViewById(R.id.txtNgayGoiThongKe);
            viewHolder.txtTongTienThongKe = view.findViewById(R.id.txtTongTienThongKe);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final ThongKeGoiMonDTO thongKeGoiMonDTO = thongKeGoiMonDTOList.get(position);


        viewHolder.txtMaGoiThongKe.setText(thongKeGoiMonDTO.getMaGoiMonTK());
        viewHolder.txtNgayGoiThongKe.setText(thongKeGoiMonDTO.getNgayGoiMon());
        viewHolder.txtTongTienThongKe.setText(String.valueOf(thongKeGoiMonDTO.getTongTienTK()) );


        viewHolder.txtMaGoiThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThongKeChiTiet = new Intent(context, ThongKeChiTietActivity.class);
                iThongKeChiTiet.putExtra("maGoiMon",thongKeGoiMonDTO.getMaGoiMonTK());
                context.startActivity(iThongKeChiTiet);
            }
        });



        return view;
    }
}
