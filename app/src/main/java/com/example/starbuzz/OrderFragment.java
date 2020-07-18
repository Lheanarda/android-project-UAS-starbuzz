package com.example.starbuzz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    DatabaseHelper myDB;
    TextView emptytext;
    TextView txtOrder;
    TextView txtClickOrder;
    private RecyclerView rvHistori;
    private ArrayList<String> name,toko,totalpemesanan,tanggalpemesanan;
    private String userid,username;
    private HistoryAdapter mHistoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        myDB = new DatabaseHelper (getActivity());
        emptytext = getView().findViewById(R.id.emptyText);
        txtOrder = getView().findViewById(R.id.txtOrder);
        rvHistori = getView().findViewById(R.id.rvHistori);
        txtClickOrder = getView().findViewById(R.id.txtclickorder);
        name = new ArrayList<String>();
        toko = new ArrayList<String>();
        totalpemesanan = new ArrayList<String>();
        tanggalpemesanan = new ArrayList<String>();

        OrderClick();
        getOnlineUser();
        InputArrayList();
        ImplementAdapter();

    }

    public void getOnlineUser(){
        Cursor res = myDB.getUSERSifLOGIN();
        if(res.moveToFirst()){
            userid = res.getString(0);
            username = res.getString(1);
        }
    }

    public void OrderClick(){
        txtClickOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OutletActivity.class);
                startActivity(intent);
            }
        });
        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OutletActivity.class);
                startActivity(intent);
            }
        });
    }

    public void InputArrayList(){
        String idToko;
        String namatoko = null;
        Cursor res = myDB.getUserHistory(userid);
        while (res.moveToNext()){
            idToko = res.getString(6);
            Cursor resToko = myDB.getSTOREnamebyID(idToko);
            if(resToko.moveToFirst())  {
                namatoko = resToko.getString(1);
            }
            String total = res.getString(4);
            String tanggal = res.getString(1);
            name.add(username);
            toko.add(namatoko);
            totalpemesanan.add(total);
            tanggalpemesanan.add(tanggal);
        }

        if(res.getCount()>0){
            emptytext.setVisibility(View.GONE);
            txtOrder.setVisibility(View.GONE);
        }
    }

    public void ImplementAdapter(){
        mHistoryAdapter = new HistoryAdapter(name,toko,totalpemesanan,tanggalpemesanan,getContext());
        rvHistori.setAdapter(mHistoryAdapter);
        rvHistori.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
