package com.tip.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.BanAnDAO;
import com.tip.orderfood.DAO.GoiMonDAO;
import com.tip.orderfood.DTO.BanAnDTO;
import com.tip.orderfood.DTO.GoiMonDTO;
import com.tip.orderfood.FragmentApp.HienThiThucDonFragment;
import com.tip.orderfood.R;
import com.tip.orderfood.ThanhToanActivity;
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
    FirebaseUser user;
    String UID;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("BAN");
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
        return position;
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

        if (banAnDTOList.get(position).isTinhTrang()){
            hienButton();
        } else {
            anButton(false);
        }
        BanAnDTO banAnDTO = banAnDTOList.get(position);


        if (banAnDTO.isTinhTrang()){
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        } else {
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);
        }


        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());

        viewHolderBanAn.imBanAn.setTag(position);

        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

    private void hienButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void anButton(boolean hieuung){
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        int viTri1 = (int) viewHolderBanAn.imBanAn.getTag();
        final String maBan = banAnDTOList.get(viTri1).getMaBan();
        switch (id) {
            case R.id.imBanAn:
                String tenBan = viewHolderBanAn.txtTenBanAn.getText().toString();
                int viTri = (int) v.getTag();
                banAnDTOList.get(viTri).setTinhTrang(true);
                hienButton();
                break;
            case R.id.imGoiMon:
//                Intent layITrangChu = ((TrangChuActivity)context).getIntent();
//                final int maNhanVien = layITrangChu.getIntExtra("maNhanVien",0);

                final String maNhanVien = " ";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                final String ngaygoi = dateFormat.format(calendar.getTime());
                banAnDAO.layTinhTrangBanTheoMa(maBan).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final boolean tinhTrang = dataSnapshot.getValue(boolean.class);

                        if (!tinhTrang) {
                            String maGoiMon = "";

                            GoiMonDTO goiMonDTO = new GoiMonDTO();
                            goiMonDTO.setMaBan(maBan);
                            goiMonDTO.setMaNhanVien(maNhanVien);
                            goiMonDTO.setNgayGoi(ngaygoi);
                            goiMonDTO.setTinhTrang(false);

                            maGoiMon = goiMonDAO.themGoiMon(goiMonDTO);
                            banAnDAO.capNhapTTBan(maBan, true);

                            FragmentTransaction transactionThuDon = fragmentManager.beginTransaction();
                            HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("maBan", maBan);
                            bundle.putString("maGoiMon", maGoiMon);
                            hienThiThucDonFragment.setArguments(bundle);
                            transactionThuDon.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                            transactionThuDon.commit();

                        } else  {
                            goiMonDAO.layMaGoiMonTheoBan(maBan).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String maGoiMon = "";
                                    for (DataSnapshot d: dataSnapshot.getChildren()){
                                        if (d.child("tinhTrang").getValue(boolean.class) == false){
                                            maGoiMon = d.getKey();
                                            break;
                                        }

                                    }

                                    FragmentTransaction transactionThuDon = fragmentManager.beginTransaction();
                                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("maBan", maBan);
                                    bundle.putString("maGoiMon", maGoiMon);
                                    hienThiThucDonFragment.setArguments(bundle);
                                    transactionThuDon.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                                    transactionThuDon.commit();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case R.id.imThanhToan:
                banAnDAO.layTinhTrangBanTheoMa(maBan).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final boolean tinhTrang = dataSnapshot.getValue(boolean.class);

                        if (tinhTrang){
                            if (user != null) {
                                UID = user.getUid().toString();
                                FirebaseDatabase.getInstance().getReference().child("Users").child(UID).child("maQuyen").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        int role = Integer.parseInt(dataSnapshot.getValue().toString());
                                        if (role == 1 || role == 3) {
                                            Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                                            iThanhToan.putExtra("maBan", maBan);
                                            context.startActivity(iThanhToan);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context, R.string.khongcoquyenthanhtoan, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else{
                                Toast.makeText(context, R.string.yeucauthanhtoan, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.bantrong, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            case R.id.imAnButton:
                anButton(true);
                break;
        }

    }


}
