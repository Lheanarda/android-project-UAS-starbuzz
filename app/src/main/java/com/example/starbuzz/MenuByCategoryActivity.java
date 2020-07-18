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

public class MenuByCategoryActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    ListView mListView;
    String rName[];
    String rDeskripsi[];
    Integer rImages[];
    Integer rHarga[];
    Context context;

    TextView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_by_category);

        myDB = new DatabaseHelper(this);
        menu = findViewById(R.id.menuCatColored);
        //GET INTENT EXTRA
        final String kategoriid = getIntent().getStringExtra("KATEGORIID") ;
        String kategorimame = getIntent().getStringExtra("KATEGORINAME");
        menu.setText(kategorimame);
        mListView = (ListView) findViewById(R.id.lvMenus);


        //INPUT DATA KE ARRAY SESUAI MENU DB
        Cursor res = myDB.getMenuByCategory(kategoriid);
        int index = 0;
        String mName[]= new String[res.getCount()];
        String mDeskripsi[]= new String[res.getCount()];
        Integer mHarga[]= new Integer[res.getCount()];
        Integer mImages [] = new Integer[res.getCount()];

        while(res.moveToNext()){
            mName[index] = new String(res.getString(1));
            mDeskripsi[index] = new String(res.getString(3));
            mHarga[index] = new Integer(Integer.parseInt(res.getString(4)));
            mImages[index] = new Integer(Integer.parseInt(res.getString(2)));
            index++;
        }

        //create item adapter
        myAdapter adapter = new myAdapter(this,mName,mDeskripsi,mImages,mHarga);
        mListView.setAdapter(adapter);

        //set item click on listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor resRead = myDB.getMenuByCategory(kategoriid);
                int i = 0;
                while(resRead.moveToNext()){
                    if(position==i){
                        Intent intent = new Intent(MenuByCategoryActivity.this,MenuDetailActivity.class);
                        intent.putExtra("MENUID",resRead.getString(0));
                        intent.putExtra("MENUNAMA",resRead.getString(1));
                        startActivity(intent);
                    }
                    i++;
                }
            }
        });
    }
    class myAdapter extends ArrayAdapter<String> {
        myAdapter (Context c, String name[], String deskripsi [], Integer imgs[], Integer hrga[]){
            super(c,R.layout.row,R.id.txtTitle,name);
            context= c;
            rName = name;
            rDeskripsi = deskripsi;
            rImages = imgs;
            rHarga = hrga;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.txtTitle);
            TextView myDescription = row.findViewById(R.id.txtSubTitle);

            images.setImageResource(rImages[position]);
            myTitle.setText(rName[position]);
            myDescription.setText("Price : IDR "+rHarga[position]);

            return row;
        }
    }
}
