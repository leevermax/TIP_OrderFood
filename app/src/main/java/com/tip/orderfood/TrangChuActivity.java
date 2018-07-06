package com.tip.orderfood;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.tip.orderfood.FragmentApp.HienThiBanAnFragment;
import com.tip.orderfood.FragmentApp.HienThiThucDonFragment;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.widget.Toolbar toolbar;
    TextView txtTenNhanVien_navigation;
    FragmentManager fragmentManager;
    int lever = 5; String Uid = "";
    FirebaseDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        database = FirebaseDatabase.getInstance();
        addControls();
        addEvents();
    }

    private void addControls() {

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview_trangchu);
        toolbar = findViewById(R.id.toolBar);
        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVien_navigation = view.findViewById(R.id.txtTenNhanVien_navigation);

        Menu menuNav=navigationView.getMenu();
        MenuItem nav_itemDangNhap = menuNav.findItem(R.id.iDangNhap);
        MenuItem nav_itemDaXnguat = menuNav.findItem(R.id.iDangXuat);
        if (Uid != ""){
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



        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");

        txtTenNhanVien_navigation.setText(tendn);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
        tranHienThiBanAn.commit();
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
            case R.id.iDangNhap:
                Intent iDangNhap = new Intent(TrangChuActivity.this,DangNhapActivity.class);
                startActivity(iDangNhap);
        }
        return false;
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
        tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
        tranHienThiThucDon.commit();
    }
}
