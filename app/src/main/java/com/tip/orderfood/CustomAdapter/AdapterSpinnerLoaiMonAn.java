package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterSpinnerLoaiMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    ViewHolderLoaiMonAn viewHolderLoaiMonAn;
    public AdapterSpinnerLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context = context;
        this.layout =layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
    }
    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class  ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else  {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else  {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
