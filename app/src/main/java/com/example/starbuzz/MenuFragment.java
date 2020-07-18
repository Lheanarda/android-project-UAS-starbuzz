package com.example.starbuzz;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    DatabaseHelper myDB;

    ListView mListView;
    String rTitle[];
    String rDescription[];
    Integer rImages[];

    Context context;
    //SWITCH TO DATABASE


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        myDB = new DatabaseHelper(getActivity());
        mListView = (ListView) getView().findViewById(R.id.lvMenuCategory);



        //method
        InputArraySetAdapter();
        listViewClick();


    }

    public void InputArraySetAdapter(){
        //INPUT DATA KE ARRAY SESUAI DATABASE
        Cursor res = myDB.getAllCategory();
        int index = 0;
        String mTitle[]= new String[res.getCount()];
        String mDescription[]= new String[res.getCount()];
        Integer images [] = new Integer[res.getCount()];
        while(res.moveToNext()){
            mTitle[index] = new String(res.getString(1));
            mDescription[index] = new String(res.getString(2));
            images[index] = new Integer(Integer.parseInt(res.getString(4)));
            index++;
        }



        //create item adapter
        myAdapter adapter = new myAdapter(getActivity(),mTitle,mDescription,images);
        mListView.setAdapter(adapter);
    }

    public void listViewClick(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor resRead = myDB.getAllCategory();
                int i = 0;
                while(resRead.moveToNext()){
                    if(position==i){
                        Intent intent = new Intent(getActivity(),MenuByCategoryActivity.class);
                        intent.putExtra("KATEGORIID",resRead.getString(0));
                        intent.putExtra("KATEGORINAME",resRead.getString(1));
                        startActivity(intent);
                    }
                    i++;
                }
            }
        });
    }

    class myAdapter extends ArrayAdapter<String>{
        myAdapter (Context c, String title[], String description [], Integer imgs[]){
            super(c,R.layout.row,R.id.txtTitle,title);
            context= c;
            rTitle = title;
            rDescription = description;
            rImages = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.txtTitle);
            TextView myDescription = row.findViewById(R.id.txtSubTitle);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }


}
