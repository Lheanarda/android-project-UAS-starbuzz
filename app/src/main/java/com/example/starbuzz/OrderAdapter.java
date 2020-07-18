package com.example.starbuzz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context mContext;
     ArrayList<String> namaMenu,hargaMenu,idMenu,deskMenu;
     ArrayList<Integer> gambarMenu,totalAdd;

    public OrderAdapter(Context context, ArrayList<String> idMenu, ArrayList<String> namaMenu
            ,ArrayList<String> deskMenu, ArrayList<String> hargaMenu, ArrayList<Integer> gambarMenu, ArrayList<Integer> totalAdd) {
        mContext = context;
        this.namaMenu = namaMenu;
        this.deskMenu = deskMenu;
        this.hargaMenu = hargaMenu;
        this.idMenu = idMenu;
        this.gambarMenu = gambarMenu;
        this.totalAdd = totalAdd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_order_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        NumberFormat format = new DecimalFormat("#,###");
        double price = Integer.parseInt(hargaMenu.get(position));
        String formattedPrice =  format.format(price);

        holder.txtNamaMenu.setText(namaMenu.get(position));
        holder.imgMenu.setImageResource(gambarMenu.get(position));
        holder.txtHarga.setText("IDR "+formattedPrice);
        if(totalAdd.get(position)==0) holder.txtTotalAdd.setText("");
        else holder.txtTotalAdd.setText(totalAdd.get(position)+"x");

        holder.layoutrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateOrderDialog updateOrderDialog = new UpdateOrderDialog(gambarMenu.get(position)
                        ,totalAdd.get(position),namaMenu.get(position),deskMenu.get(position),idMenu.get(position)
                        ,Integer.parseInt(hargaMenu.get(position)));
                updateOrderDialog.show(((FragmentActivity)mContext).getSupportFragmentManager(),"Update Dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return namaMenu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamaMenu,txtTotalAdd,txtHarga;
        ImageView imgMenu;
        LinearLayout layoutrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            txtTotalAdd = itemView.findViewById(R.id.tvTotalAdd);
            txtHarga = itemView.findViewById(R.id.tvHarga);
            imgMenu = itemView.findViewById(R.id.imageMenu);
            layoutrow = itemView.findViewById(R.id.layoutroworder);
        }
    }

}
