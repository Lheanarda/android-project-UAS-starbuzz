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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class OutletActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    ListView lvOutlet;
    Context context;

    String rTempat[];
    String rLokasi[];
    String rbuka[];
    String rtutup[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);

        myDB = new DatabaseHelper(this);
        lvOutlet = findViewById(R.id.lvOutlet);

        InputArraySetAdapter();
        listViewClick();


    }
    public void listViewClick(){
        lvOutlet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor resRead = myDB.getOUTLET();
                int i = 0;
                while(resRead.moveToNext()){
                    if(position==i){
                        Intent intent = new Intent(OutletActivity.this,OrderActivity.class);
                        Date c = Calendar.getInstance().getTime();
                        Cursor resuser = myDB.getUSERSifLOGIN();
                        while (resuser.moveToNext()){
                            String userid = resuser.getString(0);
                            myDB.insertPEMESANAN
                                    (c,0,0,0,null,Integer.parseInt(resRead.getString(0)),
                                            Integer.parseInt(userid));
                        }
                        String menuid =  getIntent().getStringExtra("MENUID");
                        intent.putExtra("MENUID",menuid);
                        intent.putExtra("STOREID",resRead.getString(0));
                        intent.putExtra("STORENAME",resRead.getString(1));
                        startActivity(intent);
                    }
                    i++;
                }
            }
        });
    }
    public void InputArraySetAdapter(){
        //INPUT DATA KE ARRAY SESUAI DATABASE
        Cursor res = myDB.getOUTLET();
        int index = 0;
        String mTempat[] = new String[res.getCount()];
        String mLokasi[] = new String[res.getCount()];
        String mBuka[] = new String[res.getCount()];
        String mTutup[] = new String[res.getCount()];
        while(res.moveToNext()){
            mTempat[index] = new String(res.getString(1));
            mBuka[index]=  new String(res.getString(2));
            mTutup[index] =  new String(res.getString(3));
            mLokasi[index] = new String(res.getString(4));
            index++;
        }
        //create item adapter
        myAdapter adapter = new myAdapter(OutletActivity.this,mTempat,mLokasi,mBuka,mTutup);
        lvOutlet.setAdapter(adapter);
    }
    class myAdapter extends ArrayAdapter<String> {
        myAdapter (Context c, String tempat[], String lokasi [],  String buka [],  String tutup []){
            super(c,R.layout.rowoutlet,R.id.txttempat,tempat);
            context= c;
            rTempat = tempat;
            rLokasi = lokasi;
            rbuka = buka;
            rtutup = tutup;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowoutlet,parent,false);
            TextView tempat = row.findViewById(R.id.txttempat);
            TextView alamat = row.findViewById(R.id.txtlokasi);
            TextView buka = row.findViewById(R.id.txtbuka);
            TextView tutup = row.findViewById(R.id.txttutup);

            tempat.setText(rTempat[position]);
            alamat.setText(rLokasi[position]);
            buka.setText(rbuka[position]);
            tutup.setText(rtutup[position]);
            return row;
        }
    }
}
