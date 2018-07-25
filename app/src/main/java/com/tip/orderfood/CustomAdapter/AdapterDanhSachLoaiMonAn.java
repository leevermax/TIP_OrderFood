package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tip.orderfood.DAO.LoaiMonAnDAO;
import com.tip.orderfood.DTO.LoaiMonAnDTO;
import com.tip.orderfood.DTO.MonAnDTO;
import com.tip.orderfood.R;

import java.util.List;

public class AdapterDanhSachLoaiMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;

    public AdapterDanhSachLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
        loaiMonAnDAO = new LoaiMonAnDAO(context);

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

    public class ViewHolderHienThiLoaiThucDon{
        ImageView imHinhLoaiThucDon;
        TextView txtTenLoaiThucDon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolderHienThiLoaiThucDon.imHinhLoaiThucDon = (ImageView) view.findViewById(R.id.imHienThiMonAn);
            viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon = (TextView) view.findViewById(R.id.txtTenLoaiThucDon);

            view.setTag(viewHolderHienThiLoaiThucDon);
        }else{
            viewHolderHienThiLoaiThucDon = (ViewHolderHienThiLoaiThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);

        viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());

        final String maLoai = loaiMonAnDTO.getMaLoai();
        loaiMonAnDAO.layHinhLoaiMonAn(maLoai);

        Picasso.get().load(loaiMonAnDTO.getHinhAnh()).into(viewHolderHienThiLoaiThucDon.imHinhLoaiThucDon);





//        Picasso.get().load("http://media.phunutoday.vn/files/upload_images/2016/05/25/cach-lam-ga-hap-hanh-ngon-1-phunutoday_vn.jpg").into(viewHolderHienThiLoaiThucDon.imHinhLoaiThucDon);

        return view;
    }
}
