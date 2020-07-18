package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText edtPhone,edttext;
    Button btnContinue,btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        myDB = new DatabaseHelper(this);
        edtPhone =(EditText) findViewById(R.id.edtPhone);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "0"+edtPhone.getText().toString();

                Cursor res = myDB.getUSERSbyPHONE(phone);
                if(res.getCount()==0){
                    Intent intent = new Intent(WelcomeActivity.this,CreateAccActivity.class);
                    intent.putExtra("PHONEKEY",phone);
                    startActivity(intent);
                    finish();
                }else{
                    while(res.moveToNext()){
                        String id = res.getString(0);
                        myDB.userLOGIN(id);
                        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });
    }
}
