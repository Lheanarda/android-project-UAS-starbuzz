package com.example.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    private EditText txtprofile,txtAddress,txtVersion;
    private TextView profileText, addressText, versionText,txtName,txtTotalOrder;
    private Button btnLogout;
    DatabaseHelper myDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account,container,false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        myDB = new DatabaseHelper(getActivity());
        txtprofile = (EditText) getView().findViewById(R.id.txtProfile);
        txtAddress = (EditText) getView().findViewById(R.id.txtAddress);
        txtVersion = (EditText) getView().findViewById(R.id.txtVersion);
        profileText = (TextView) getView().findViewById(R.id.profileText);
        addressText = (TextView)getView().findViewById(R.id.AddressText);
        versionText = (TextView) getView().findViewById(R.id.versionText);
        btnLogout = (Button) getView().findViewById(R.id.btnLogout);
        txtName = (TextView) getView().findViewById(R.id.txtAccount);
        txtTotalOrder = getView().findViewById(R.id.txtTotalOrderN);

        //method
        ReadTotalOrder();
        Disable();
        Click();
        LOGOUT();

    }
    public void ReadTotalOrder(){
        Cursor resName = myDB.getUSERSifLOGIN();
        while(resName.moveToNext()){
            String YourName = resName.getString(1);
            //TOTAL ORDER
            String userid = resName.getString(0);
            Cursor resOrder = myDB.getUSERTOTALORDER(userid);
            if(resOrder.getCount()>0){
                while(resOrder.moveToNext()){
                    String totalorder = resOrder.getString(0);
                    txtTotalOrder.setText(totalorder);
                }
            }

            txtName.setText(YourName);
        }
    }
    public void Disable(){
        txtprofile.setEnabled(false);
        txtAddress.setEnabled(false);
        txtVersion.setEnabled(false);
        txtprofile.setClickable(true);
    }
    public void Click(){
        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),IdentityActivity.class);
                startActivity(intent);
            }
        });

        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        versionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    public void LOGOUT(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getUSERSifLOGIN();
                if(res.getCount()==0){
                    Toast.makeText(getActivity(),"ERROR, NOTHING FOUND",Toast.LENGTH_LONG).show();
                }else{
                    while(res.moveToNext()){
                        String id = res.getString(0);
                        myDB.userLOGOUT(id);
                        Intent intent = new Intent(getActivity(),WelcomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                }
            }
        });
    }
}
