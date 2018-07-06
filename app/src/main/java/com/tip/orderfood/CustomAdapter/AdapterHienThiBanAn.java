package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.DTO.GoiMonDTO;
import com.tip.orderfood.FragmentApp.HienThiThucDonFragment;
import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<BanAnDTO> banAnDTOList;
    ViewHolderBanAn viewHolderBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);

        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }


    public class ViewHolderBanAn{

        ImageView imBanAn, imGoiMon, imThanhToan, imAnButton;
        TextView txtTenBanAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null){
            LayoutInflater  inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan,parent,false);
            viewHolderBanAn.imBanAn = view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imGoiMon = view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan = view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.imAnButton = view.findViewById(R.id.imAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);
            view.setTag(viewHolderBanAn);
        } else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        if (banAnDTOList.get(position).isDuocChon()){
            hienButton();
        } else {
            anButton();
        }
        BanAnDTO banAnDTO = banAnDTOList.get(position);

        String tinhTrang = banAnDAO.layTinhTrangBanThaoMa(banAnDTO.getMaBan());
        if (tinhTrang.equals("true")){
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        } else {
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);
        }


        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());

        viewHolderBanAn.imBanAn.setTag(position);

        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);

        return view;
    }

    private void hienButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);
    }

    private void anButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        switch (id){
            case R.id.imBanAn:
                String tenBan = viewHolderBanAn.txtTenBanAn.getText().toString();
                int viTri = (int)v.getTag();
                banAnDTOList.get(viTri).setDuocChon(true);
                hienButton();
                break;
            case R.id.imGoiMon:
                int viTri1 = (int) viewHolderBanAn.imBanAn.getTag();
                int maBan = banAnDTOList.get(viTri1).getMaBan();
                Intent layITrangChu = ((TrangChuActivity)context).getIntent();
                int maNhanVien = layITrangChu.getIntExtra("maNhanVien",0);

                String tinhTrang = banAnDAO.layTinhTrangBanThaoMa(maBan);
                if(tinhTrang.equals("false")){
					Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngaygoi = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maBan);
                    goiMonDTO.setMaNv(maNhanVien);
                    goiMonDTO.setNgayGoi(ngaygoi);
                    goiMonDTO.setTinhTrang("false");
                    long kTra = goiMonDAO.themGoiMon(goiMonDTO);
                    banAnDAO.capNhapTTBan(maBan,"true");
                    if (kTra == 0){
                        Toast.makeText(context,context.getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }

                }
                FragmentTransaction transactionThuDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maBan",maBan);
                hienThiThucDonFragment.setArguments(bundle);
                transactionThuDon.replace(R.id.content,hienThiThucDonFragment).addToBackStack("hienthibanan");
                transactionThuDon.commit();

                break;


        }

    }


}
