package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tip.orderfood.DTO.ThanhToanDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterThanhToan extends BaseAdapter {
    Context context;
    int layout;
    List<ThanhToanDTO> thanhToanDTOS;

    ViewHolderThanhToan viewHolderThanhToan;


    public AdapterThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOs){
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOS = thanhToanDTOs;
    }

    @Override
    public int getCount() {
        return thanhToanDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderThanhToan{
        TextView txtTenMonAn,txtSoLuong,txtGiaTien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderThanhToan.txtTenMonAn = (TextView) view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtGiaTien = (TextView) view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuong = (TextView) view.findViewById(R.id.txtSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }else{
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOS.get(position);

        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));

        return view;
    }
}
