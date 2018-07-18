package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.DTO.QuyenDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterQuyen extends BaseAdapter {
    Context context;
    int layout;
    List<QuyenDTO> quyenDTOS;
    AdapterQuyen.ViewHolder viewHolder;
    public AdapterQuyen(Context context, int layout, List<QuyenDTO> quyenDTOS){
        this.context = context;
        this.layout =layout;
        this.quyenDTOS = quyenDTOS;
    }
    @Override
    public int getCount() {
        return quyenDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return quyenDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class  ViewHolder{
        TextView txtTenQuyen;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolder = new AdapterQuyen.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txtTenQuyen = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolder);
        } else  {
            viewHolder = (ViewHolder) view.getTag();
        }

        QuyenDTO quyenDTO = quyenDTOS.get(position);
        viewHolder.txtTenQuyen.setText(quyenDTO.getTenQuyen());
        viewHolder.txtTenQuyen.setTag(quyenDTO.getMaQuyen());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolder = new AdapterQuyen.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txtTenQuyen = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolder);
        } else  {
            viewHolder = (ViewHolder) view.getTag();
        }

        QuyenDTO quyenDTO = quyenDTOS.get(position);
        viewHolder.txtTenQuyen.setText(quyenDTO.getTenQuyen());
        viewHolder.txtTenQuyen.setTag(quyenDTO.getMaQuyen());


        return view;
    }
}
