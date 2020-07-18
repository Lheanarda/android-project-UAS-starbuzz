package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class IdentityActivity extends AppCompatActivity {

    private EditText edtemail,edtphone,edtname;
    private Button btnUpdate;
    private ImageView imgBack;

    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        myDB = new DatabaseHelper(this);
        edtemail = findViewById(R.id.edtEmail);
        edtname = findViewById(R.id.edtName);
        edtphone = findViewById(R.id.edtPhone);
        btnUpdate = findViewById(R.id.btnUpdateProfile);
        imgBack = findViewById(R.id.imgBack);

        edtemail.setEnabled(false);
        edtphone.setEnabled(false);

        setUserInfo();
        imgBackClick();


    }

    public void updateUSERS(final String id){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate =myDB.updateUSERS(id,edtname.getText().toString());
                if(isUpdate==true) Toast.makeText(IdentityActivity.this,"Success",Toast.LENGTH_SHORT).show();
                else Toast.makeText(IdentityActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void imgBackClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdentityActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setUserInfo(){
        Cursor res = myDB.getUSERSifLOGIN();
        if(res.getCount()==0){
            Toast.makeText(IdentityActivity.this,"ERROR, NOTHING FOUND",Toast.LENGTH_LONG).show();
        }else{
            while(res.moveToNext()){
                String id = res.getString(0);
                String yourName = res.getString(1);
                String email = res.getString(3);
                String phone = res.getString(4);

                edtphone.setText(phone);
                edtemail.setText(email);
                edtname.setText(yourName);

                updateUSERS(id);
            }
        }
    }
}
