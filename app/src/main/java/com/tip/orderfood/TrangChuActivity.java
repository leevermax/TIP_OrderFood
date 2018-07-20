package com.tip.orderfood;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tip.orderfood.DAO.NhanVienDAO;
import com.tip.orderfood.FragmentApp.HienThiBanAnFragment;
import com.tip.orderfood.FragmentApp.HienThiNhanVienFragment;
import com.tip.orderfood.FragmentApp.HienThiThucDonFragment;
import com.tip.orderfood.FragmentApp.NhaBepFragment;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.widget.Toolbar toolbar;
    TextView txtTenNhanVien_navigation;
    FragmentManager fragmentManager;
    String Uid;
    String emailHT,matKhauHT;
    NhanVienDAO nhanVienDAO;
    Menu menuNav;
    FirebaseDatabase database;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        addControls();
        addEvents();
    }

    private void addControls() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview_trangchu);
        toolbar = findViewById(R.id.toolBar);
        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVien_navigation = view.findViewById(R.id.txtTenNhanVien_navigation);

        nhanVienDAO = new NhanVienDAO(this);



        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        menuNav = navigationView.getMenu();
        MenuItem nav_itemDangNhap = menuNav.findItem(R.id.iDangNhap);
        MenuItem nav_itemDaXnguat = menuNav.findItem(R.id.iDangXuat);

        if (user != null){
            nav_itemDangNhap.setVisible(false);
            nav_itemDaXnguat.setVisible(true);
        } else {
            nav_itemDangNhap.setVisible(true);
            nav_itemDaXnguat.setVisible(false);
        }
    }

    private void addEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.mo,R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();


        if (user != null){
            Uid = user.getUid().toString();
            nhanVienDAO.getNameUser(Uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null){
                        txtTenNhanVien_navigation.setText(dataSnapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            txtTenNhanVien_navigation.setText(getResources().getString(R.string.khachhang));
        }

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        emailHT = intent.getStringExtra("emailHT");
        matKhauHT = intent.getStringExtra("matKhauHT");
        int k = intent.getIntExtra("keyThemNV",0);

        fragmentManager = getSupportFragmentManager();
        if (k != 0){


            HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
            Bundle bundle = new Bundle();
            bundle.putString("emailHT",emailHT);
            bundle.putString("matKhauHT",matKhauHT);
            hienThiNhanVienFragment.setArguments(bundle);

            FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
            tranNhanVien.replace(R.id.content,hienThiNhanVienFragment).addToBackStack("trangchu");
            tranNhanVien.commit();

            MenuItem nav_itemNhanVien = menuNav.findItem(R.id.itNhanVien);
            nav_itemNhanVien.isChecked();
        }else {
            FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
            HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
            tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
            tranHienThiBanAn.commit();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                showBanAn();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itThucDon:
                showThucDon();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itNhaBep:
                showBep();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itNhanVien:
                showNhanVien();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.iDangNhap:
                Intent iDangNhap = new Intent(TrangChuActivity.this,DangNhapActivity.class);
                startActivity(iDangNhap);
                break;
            case R.id.iDangXuat:
                FirebaseAuth.getInstance().signOut();
                drawerLayout.closeDrawers();
                Intent iTrangChu = new Intent(TrangChuActivity.this,TrangChuActivity.class);
                startActivity(iTrangChu);
                break;

        }
        return false;
    }

    private void showBep() {
        FragmentTransaction tranNhaBep = fragmentManager.beginTransaction();
        NhaBepFragment  nhaBepFragment = new NhaBepFragment();
        tranNhaBep.replace(R.id.content,nhaBepFragment);
        tranNhaBep.commit();
    }

    private void showNhanVien() {
        HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
        Bundle bundle = new Bundle();
        bundle.putString("emailHT",emailHT);
        bundle.putString("matKhauHT",matKhauHT);
        hienThiNhanVienFragment.setArguments(bundle);

        FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
        tranNhanVien.replace(R.id.content,hienThiNhanVienFragment).addToBackStack("trangchu");
        tranNhanVien.commit();
    }

    private void showBanAn() {
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
        tranHienThiBanAn.commit();
    }

    private void showThucDon() {
        FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
        HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
        tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment).addToBackStack("trangchu");
        tranHienThiThucDon.commit();
    }
}
