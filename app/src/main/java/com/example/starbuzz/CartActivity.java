package com.example.starbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CartActivity extends AppCompatActivity {

    DatabaseHelper myDB ;
    String rName[],rJumlah[],rHarga[],namaToko;
    Context context;
    ListView lvCart;
    TextView txtaddmore;

    TextView txtsubtotal,txtdelivery,txttotal;
    Button btnpayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        myDB = new DatabaseHelper(CartActivity.this);

        lvCart = findViewById(R.id.lvcart);
        txtsubtotal = findViewById(R.id.txtsubtotal);
        txtdelivery = findViewById(R.id.txtdelivery);
        txttotal = findViewById(R.id.txttotal);
        btnpayment = findViewById(R.id.btnPayment);
        txtaddmore = findViewById(R.id.txtaddmore);

        //get Extra
        namaToko= getIntent().getStringExtra("STORENAME");

        //method
        addMoreClick();
        ImplementAdapter();
        MakeOrder();
    }

    public void ImplementAdapter(){
        Cursor getlast = myDB.getLASTorder();
        while (getlast.moveToNext()){
            String pemesananid = getlast.getString(0);
            Cursor res = myDB.getPEMESANANDET(pemesananid);
            int index = 0;
            String mName[]= new String[res.getCount()];
            String mJumlah[]= new String[res.getCount()];
            String mHarga[]= new String[res.getCount()];

            while (res.moveToNext()){
                mJumlah[index] = new String(res.getString(2));
                Cursor resMenu = myDB.getMenuByID(res.getString(0));
                while(resMenu.moveToNext()){
                    mHarga[index] = new String(resMenu.getString(4));
                    mName[index] = new String(resMenu.getString(1));
                    //create item adapter
                    myAdapter adapter = new myAdapter(this,mName,mHarga,mJumlah);
                    lvCart.setAdapter(adapter);
                }
                index++;
            }
        }
    }
    public boolean checkLastCart(){
        Cursor res = myDB.getLASTDETPEMESANAN();
        if(res.moveToFirst()) return true;
        else return false;
    }
    public void MakeOrder(){
        if(checkLastCart()==true){
            Cursor readsubtotal = myDB.getSubtotal();
            if(readsubtotal.getCount()>0){
                if(readsubtotal.moveToFirst()){
                    Integer subtotal= Integer.parseInt(readsubtotal.getString(0));
                    final int all = subtotal+10000;
                    NumberFormat format = new DecimalFormat("#,###");
                    double subtotaldouble = subtotal;
                    String formattedSubtotal =  format.format(subtotal);
                    String formattedall = format.format(all);

                    txtdelivery.setText("IDR 10,000");
                    txtsubtotal.setText("IDR "+ formattedSubtotal);
                    txttotal.setText("IDR "+formattedall);

                    btnpayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cursor readlast = myDB.getLASTorder();
                            while(readlast.moveToNext()){
                                String orderid = readlast.getString(0);
                                myDB.updatePEMESANAN(orderid,10000,all);

                                Intent intent = new Intent(CartActivity.this,MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(CartActivity.this,"Order Successfully Made !",Toast.LENGTH_LONG).show();
                                finish();
                                OrderActivity.orderAct.finish();
                            }

                        }
                    });
                }
            }
        }else{
            txtsubtotal.setText("IDR 0");
            txtdelivery.setText("IDR 10,000");
            txttotal.setText("IDR 10,000");
        }


    }
    public void addMoreClick(){
        txtaddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,OrderActivity.class);
                intent.putExtra("STORENAME",namaToko);
                startActivity(intent);
                finish();
            }
        });
    }
    class myAdapter extends ArrayAdapter<String> {
        myAdapter(Context c, String name[], String harga[], String  jumlah[]) {
            super(c, R.layout.rowcart, R.id.txtTitle, name);
            context = c;
            rName = name;
            rJumlah = jumlah;
            rHarga = harga;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowcart,parent,false);
            TextView namapemesanan = row.findViewById(R.id.txtnamapesan);
            TextView jumlah = row.findViewById(R.id.txtjumlahpesan);
            TextView hargapesan = row.findViewById(R.id.txthargapesan);

            NumberFormat format = new DecimalFormat("#,###");
            double harga = Integer.parseInt(rHarga[position]);
            String formattedHarga = "IDR "+ format.format(harga);

            namapemesanan.setText(rName[position]);
            jumlah.setText(rJumlah[position]);
            hargapesan.setText(formattedHarga);
            return row;
        }
    }
}
