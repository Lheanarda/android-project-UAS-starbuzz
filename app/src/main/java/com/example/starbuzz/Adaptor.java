package com.example.starbuzz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Adaptor extends PagerAdapter {

    DatabaseHelper myDB;

     private List<Model> models;
     private LayoutInflater layoutInflater;
     private Context context;

    public Adaptor(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        myDB = new DatabaseHelper(context);
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container,false);

        ImageView imageView;
        final TextView title,desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        container.addView(view,0) ;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = title.getText().toString();
                //CODE VPCATEGORY BUTTON
                Cursor res = myDB.getKATEGORIIDbyNAME(judul);
                if(res.getCount()>0){
                    while(res.moveToNext()){
                        String kategoriid = res.getString(0);
                        Intent intent = new Intent(context,MenuByCategoryActivity.class);
                        intent.putExtra("KATEGORIID",kategoriid);
                        intent.putExtra("KATEGORINAME",judul);
                        context.startActivity(intent);
                    }
                }else{
                    Cursor resmenu = myDB.getMenuByID(desc.getText().toString());
                    while (resmenu.moveToNext()){
                        String menuid = resmenu.getString(0);
                        Intent intent = new Intent(context,MenuDetailActivity.class);
                        intent.putExtra("MENUID",menuid);
                        intent.putExtra("MENUNAMA",judul);
                        context.startActivity(intent);
                    }
                }
                //END CODE VPCATEGORY BUTTON


            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
