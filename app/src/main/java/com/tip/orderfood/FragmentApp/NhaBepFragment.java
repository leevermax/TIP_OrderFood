package com.tip.orderfood.FragmentApp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tip.orderfood.R;
import com.tip.orderfood.TrangChuActivity;

public class NhaBepFragment extends Fragment {

    ListView lvMonAnBep;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_nhabep,container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.danhsachmondagoi);

        lvMonAnBep = view.findViewById(R.id.lvMonAnBep);




        return view;
    }

}
