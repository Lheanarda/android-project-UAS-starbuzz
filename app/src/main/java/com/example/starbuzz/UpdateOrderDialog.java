package com.example.starbuzz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateOrderDialog extends DialogFragment {
    private Integer imgMenuKode,jumlah,subtotal,harga;
    private String namaMenu,deskMenu,menuid;
    private TextView txtNamaMenu,txtDeskMenu;
    private ImageButton btnPlus,btnMinus;
    private EditText edtTotal;
    private ImageView imgMenu;
    private UpdateDialogListener mUpdateDialogListener;

    public UpdateOrderDialog(Integer imgMenuKode, Integer jumlah, String namaMenu, String deskMenu,String menuid,int harga) {
        this.imgMenuKode = imgMenuKode;
        this.jumlah = jumlah;
        this.namaMenu = namaMenu;
        this.deskMenu = deskMenu;
        this.menuid= menuid;
        this.harga = harga;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_updateorder_dialog,null);
        builder.setView(view)
                .setTitle("Update Order")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumlah = Integer.parseInt(edtTotal.getText().toString());
                        subtotal = harga*jumlah;
                        mUpdateDialogListener.UpdateOrder(jumlah,menuid,subtotal);
                    }
                });
        txtNamaMenu = view.findViewById(R.id.txtNamaMenu);
        txtDeskMenu = view.findViewById(R.id.txtDeskMenu);
        edtTotal = view.findViewById(R.id.edtTotal);
        imgMenu = view.findViewById(R.id.imgmenu);
        btnMinus = view.findViewById(R.id.btnMinus);
        btnPlus = view.findViewById(R.id.btnPlus);

        //method
        setText();
        PlusOrder();
        MinusOrder();
        return builder.create();
    }

    public void PlusOrder(){
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = Integer.parseInt(edtTotal.getText().toString());
                jumlah++;
                edtTotal.setText(jumlah+"");
            }
        });
    }

    public void MinusOrder(){
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = Integer.parseInt(edtTotal.getText().toString());
                if(jumlah>0){
                    jumlah--;
                    edtTotal.setText(jumlah+"");
                }
            }
        });
    }

    public void setText(){
        if(namaMenu != null){
            txtNamaMenu.setText(namaMenu);
            txtDeskMenu.setText(deskMenu);
            edtTotal.setText(jumlah+"");
            imgMenu.setImageResource(imgMenuKode);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mUpdateDialogListener = (UpdateDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement UpdateDialogListener");
        }

    }

    public interface UpdateDialogListener{
        void UpdateOrder(int jumlah, String menuid, int subtotal);
    }
}
