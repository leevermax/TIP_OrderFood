package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterTopMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<MonAnDTO> monAnDTOList;
    AdapterTopMonAn.ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;

    public AdapterTopMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList){
        this.context = context;
        this.layout = layout;
        this.monAnDTOList = monAnDTOList;
    }

    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            view = inflater.inflate(layout,parent,false);

            viewHolderHienThiDanhSachMonAn.txtTopTenMonAn = (TextView) view.findViewById(R.id.txtTopTenMonAn);
            viewHolderHienThiDanhSachMonAn.txtTopGiaMonAn = (TextView) view.findViewById(R.id.txtTopGiaMonAn);
            viewHolderHienThiDanhSachMonAn.txtTopLanGoi = (TextView) view.findViewById(R.id.txtTopLanGoi);

            view.setTag(viewHolderHienThiDanhSachMonAn);

        }else{
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();
        }

        MonAnDTO monAnDTO = monAnDTOList.get(position);

        viewHolderHienThiDanhSachMonAn.txtTopTenMonAn.setText(monAnDTO.getTenMonAn());
        viewHolderHienThiDanhSachMonAn.txtTopGiaMonAn.setText(String.valueOf(monAnDTO.getGiaTien()));

        viewHolderHienThiDanhSachMonAn.txtTopLanGoi.setText(String.valueOf(monAnDTO.getLanGoi()));


        Log.d("tenmonan",viewHolderHienThiDanhSachMonAn.txtTopTenMonAn.getText().toString());
        return view;
    }


    public class ViewHolderHienThiDanhSachMonAn{

        TextView txtTopTenMonAn,txtTopGiaMonAn,txtTopLanGoi;
    }

}
