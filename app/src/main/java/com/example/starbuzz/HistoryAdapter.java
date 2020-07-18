package com.example.starbuzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    ArrayList<String> name,toko,total,tanggal;
    Context mContext;

    public HistoryAdapter(ArrayList<String> name, ArrayList<String> toko, ArrayList<String> total, ArrayList<String> tanggal, Context context) {
        this.name = name;
        this.toko = toko;
        this.total = total;
        this.tanggal = tanggal;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rowhistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NumberFormat format = new DecimalFormat("#,###");
        double totallharga = Integer.parseInt(total.get(position)) ;
        String formattedtotal = "IDR "+format.format(totallharga);
        holder.txtNama.setText(name.get(position));
        holder.txtToko.setText(toko.get(position));
        holder.txtTotal.setText(formattedtotal);
        holder.txtTanggal.setText(tanggal.get(position));
    }

    @Override
    public int getItemCount() {
        return total.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNama,txtToko,txtTotal,txtTanggal;
        LinearLayout rowHistory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNamaHistori);
            txtToko = itemView.findViewById(R.id.txttokohistori);
            txtTotal = itemView.findViewById(R.id.txttotalhistori);
            txtTanggal = itemView.findViewById(R.id.txtTanggalHistori);
            rowHistory = itemView.findViewById(R.id.linearRowHistory);
        }
    }
}
