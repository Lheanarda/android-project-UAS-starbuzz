package com.example.starbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements UpdateOrderDialog.UpdateDialogListener {

    private DatabaseHelper myDB;
    private ImageButton btnOrder;
    private TextView ordercol;
    private String idtoko,namatoko,idOrderLastInput;
    private RecyclerView rvMenuList;
    private ArrayList<String> namaMenu,hargaMenu,idMenu,deskMenu;
    private ArrayList<Integer> gambarMenu,totalAdd;
    private OrderAdapter mOrderAdapter;
    public static Activity orderAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderAct = this;
        //db
        myDB = new DatabaseHelper(OrderActivity.this);

        //ui
        btnOrder = findViewById(R.id.btnOrder);
        namatoko = getIntent().getStringExtra("STORENAME") ;
        idtoko = getIntent().getStringExtra("STOREID");
        ordercol = findViewById(R.id.orderColored);
        rvMenuList = findViewById(R.id.RvListMenu);

        //array list
        idMenu=new ArrayList<String>();
        namaMenu = new ArrayList<String>();
        hargaMenu = new ArrayList<String>();
        deskMenu = new ArrayList<String>();
        totalAdd = new ArrayList<Integer>();
        gambarMenu = new ArrayList<Integer>();

        //method
        ordercol.setText("OUTLET "+namatoko);
        ReadLastInputOrder();
        InputItemToArrayList();
        ImplementAdapter();
        btnOrderClick();
    }

    public void btnOrderClick(){
        //BUTTON VIEW CART
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,CartActivity.class);
                intent.putExtra("STOREID",idtoko);
                intent.putExtra("STORENAME",namatoko);
                startActivity(intent);
            }
        });
        //END BTN VIEW CART
    }

    public void ReadLastInputOrder(){
        Cursor res = myDB.getLASTorder();
        if(res.moveToFirst()){
            idOrderLastInput = res.getString(0);
        }
    }

    public int ReadTotalMenuAddToCart(String menuid){
        Cursor res = myDB.getDETPEMESANANByIDS(idOrderLastInput,menuid);
        if(res.moveToFirst()){
            int totalAdd = Integer.parseInt(res.getString(2));
            return totalAdd;
        }else return 0;
    }

    public boolean ReadCart(String menuid){
        Cursor res = myDB.getDETPEMESANANByIDS(idOrderLastInput,menuid);
        if(res.moveToFirst()) {
            return true;
        }else return false;
    }

    public void ClearArrayList(){
        idMenu.clear();
        namaMenu.clear();
        hargaMenu.clear();
        deskMenu.clear();
        totalAdd.clear();
        gambarMenu.clear();
    }

    public void InputItemToArrayList(){
        Cursor res =myDB.getALLmenu();
        Log.i("check","ENTER");
        if(res.getCount()>0){
            while (res.moveToNext()){
                String tempMenuId = res.getString(0);
                String tempMenuNama = res.getString(1);
                String tempMenuHarga = res.getString(4);
                String tempDeskMenu = res.getString(3);
                Integer tempKodeGambarMenu = Integer.parseInt(res.getString(2));
                int tempTotalAdd = ReadTotalMenuAddToCart(tempMenuId);
                Log.i("check",tempMenuNama);
                deskMenu.add(tempDeskMenu);
                idMenu.add(tempMenuId);
                namaMenu.add(tempMenuNama);
                hargaMenu.add(tempMenuHarga);
                totalAdd.add(tempTotalAdd);
                gambarMenu.add(tempKodeGambarMenu);

            }
        }
    }

    public void ImplementAdapter(){
        mOrderAdapter = new OrderAdapter(OrderActivity.this,idMenu,namaMenu,deskMenu,hargaMenu,gambarMenu,totalAdd);
        rvMenuList.setAdapter(mOrderAdapter);
        rvMenuList.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
    }


    @Override
    public void UpdateOrder(int jumlah, String menuid, int subtotal) {
        myDB = new DatabaseHelper(OrderActivity.this);
        if(jumlah==0){
            if(ReadCart(menuid)==true) {
                Integer isDelete = myDB.deleteDETPEMESANANbyIDS(idOrderLastInput,menuid);
                if(isDelete==0) Toast.makeText(this,"Data not deleted",Toast.LENGTH_SHORT);
                else {
                    Toast.makeText(this,"Order Updated",Toast.LENGTH_SHORT).show();
                    ClearArrayList();
                    InputItemToArrayList();
                    ImplementAdapter();
                }
            }
        }else{
            if(ReadCart(menuid)==true) myDB.updateDETPEMESANAN(idOrderLastInput,menuid,jumlah,subtotal);
            else myDB.insertDETPEMESANAN(menuid,idOrderLastInput,jumlah,subtotal);

            Toast.makeText(this,"Order Updated",Toast.LENGTH_SHORT).show();
            ClearArrayList();
            InputItemToArrayList();
            ImplementAdapter();
        }
    }
}
