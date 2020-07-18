package com.example.starbuzz;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    DatabaseHelper myDB;
    ViewPager mViewPager, mViewPagerFavorites;
    Adaptor mAdapter,mAdapterFavorites;
    List<Model> models,modelsFavorites;

    TextView txtName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        myDB = new DatabaseHelper(getActivity());
        ViewPager vpHome = (ViewPager) getView().findViewById(R.id.vpHome);
        final ImageAdapter adapter = new ImageAdapter(getActivity());
        vpHome.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(vpHome,true);
        txtName = (TextView)getView().findViewById(R.id.txtAccount);

        //Nama sesuai database
        Cursor resName = myDB.getUSERSifLOGIN();
        while(resName.moveToNext()){
            String onlineName = resName.getString(1);
            txtName.setText(onlineName);
        }

        //ViewPage Category
        models = new ArrayList<>();
        Cursor res = myDB.getAllCategory();
        while(res.moveToNext()){
            String catName = res.getString(1);
            String catDesk = res.getString(2);
            Integer catPict = Integer.parseInt(res.getString(3));

            models.add(new Model(catPict,catName,catDesk));
        }


        mAdapter=  new Adaptor(models,getActivity());
        mViewPager = (ViewPager) getView().findViewById(R.id.vpCategory);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPadding(130, 0,130,0);



        //END vpCategory

        //START VPFavorites
        modelsFavorites = new ArrayList<>();
        Cursor resFavorites = myDB.getFAVORITES();
        if(resFavorites.getCount()>0){
            while (resFavorites.moveToNext()){
                String menuNAME = resFavorites.getString(1);
                String menuDESKRIPSI = resFavorites.getString(0);
                Integer menuGAMBAR = Integer.parseInt(resFavorites.getString(2));
                modelsFavorites.add(new Model(menuGAMBAR,menuNAME,menuDESKRIPSI));
            }
        }else{

        }

//        modelsFavorites.add(new Model(R.drawable.sticker,"Sticker","desc1"));
//        modelsFavorites.add(new Model(R.drawable.brochure,"Brochure","desc2"));
//        modelsFavorites.add(new Model(R.drawable.poster,"Poster","desc3"));
//        modelsFavorites.add(new Model(R.drawable.namecard,"Namecard","desc4"));

        mAdapterFavorites=  new Adaptor(modelsFavorites,getActivity());
        mViewPagerFavorites = (ViewPager) getView().findViewById(R.id.vpFavorites);
        mViewPagerFavorites.setAdapter(mAdapterFavorites);
        mViewPagerFavorites.setPadding(130, 0,130,0);
        //END VPFavorites
    }

}


