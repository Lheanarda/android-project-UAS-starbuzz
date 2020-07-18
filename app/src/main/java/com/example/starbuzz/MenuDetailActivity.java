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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuDetailActivity extends AppCompatActivity {

    DatabaseHelper myDB ;
    String rName[],menuid,menunama;
    String rDeskripsi[];
    Integer[] rImages;
    ListView mListView;
    Context context;
    TextView detailColored;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        myDB = new DatabaseHelper(MenuDetailActivity.this);
        detailColored = findViewById(R.id.detailColored);
        mListView=findViewById(R.id.lvDetail);
        //GET EXTRA
        menuid = getIntent().getStringExtra("MENUID");
        menunama = getIntent().getStringExtra("MENUNAMA");
        detailColored.setText(menunama);

        //method
        InputArrayImplementAdapter();
    }

    public void InputArrayImplementAdapter(){
        Cursor res = myDB.getMenuByID(menuid);
        int index = 0;
        String mName[]= new String[res.getCount()];
        String mDeskripsi[]= new String[res.getCount()];
        Integer mImages [] = new Integer[res.getCount()];

        while(res.moveToNext()){
            Integer status = Integer.parseInt(res.getString(6));
            mName[index] = new String(res.getString(1));
            mDeskripsi[index] = new String(res.getString(3));
            mImages[index] = new Integer(Integer.parseInt(res.getString(2)));
            index++;
        }
        //create item adapter
        MenuDetailActivity.myAdapter adapter = new MenuDetailActivity.myAdapter(this,mName,mDeskripsi,mImages);
        mListView.setAdapter(adapter);
    }

    class myAdapter extends ArrayAdapter<String> {
        myAdapter(Context c, String name[], String deskripsi[], Integer imgs[]) {
            super(c, R.layout.row, R.id.txtTitle, name);
            context = c;
            rName = name;
            rDeskripsi = deskripsi;
            rImages = imgs;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowdetail,parent,false);
            ImageView images = row.findViewById(R.id.imgDetail);
            TextView myTitle = row.findViewById(R.id.txtNamaDetail);
            TextView myDescription = row.findViewById(R.id.txtDetailMenu);
            Button btnOrder = row.findViewById(R.id.btnOrder);
            final CheckBox cbFavorite = row.findViewById(R.id.cbFavorite);

            images.setImageResource(rImages[position]);
            myTitle.setText(rName[position]);
            myDescription.setText(rDeskripsi[position]);



            //ADD FAVORITES
            String menuid = getIntent().getStringExtra("MENUID");

            //ADD ORDER
            addORDER(btnOrder,menuid);
            //END ADD ORDER
            Cursor res = myDB.getMenuByID(menuid);
            if(res.getCount()>0){
                while(res.moveToNext()){
                    Integer fav = Integer.parseInt(res.getString(6));
                    if(fav==1){
                        cbFavorite.setChecked(true);
                    }else{
                        cbFavorite.setChecked(false);
                    }
                }
            }

            updateFAVORITES(cbFavorite,menuid);
            //END FAVORITES
            return row;
        }
    }

    public void updateFAVORITES(final CheckBox cbf, final String menuid){
        cbf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbf.isChecked()){
                    boolean result = myDB.menuFAVORITES(menuid);
                    if(result==true){
                        Toast.makeText(MenuDetailActivity.this,"Added To favorites",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    boolean result = myDB.menuREMOVEfavorites(menuid);
                    if(result==true){
                        Toast.makeText(MenuDetailActivity.this,"Removed From favorites",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void addORDER(Button btn, final String id){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuDetailActivity.this,OutletActivity.class);
                intent.putExtra("MENUID",id);
                startActivity(intent);
            }
        });
    }



}
