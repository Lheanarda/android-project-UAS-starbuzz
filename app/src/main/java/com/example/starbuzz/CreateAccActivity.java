package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText edtname,edtemail,edtpin,edtpinconfirm;
    Button btnregister;
    private String phone;
    private int pin2 = 0,pinconf2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
        myDB = new DatabaseHelper(this);

        phone = getIntent().getStringExtra("PHONEKEY");
        edtname = findViewById(R.id.edtname);
        edtemail = findViewById(R.id.edtEmail);
        edtpin = findViewById(R.id.edtPIN);
        edtpinconfirm =findViewById(R.id.edtPIN2);
        btnregister = findViewById(R.id.btnRegister);

        //method
        btnRegisterClick();

    }

    public void btnRegisterClick(){
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String email = edtemail.getText().toString();
                String pin = edtpin.getText().toString().trim();
                String pinconf = edtpinconfirm.getText().toString().trim();
                if(!TextUtils.isEmpty(pin) && !TextUtils.isEmpty(pinconf)){
                    pin2 = Integer.parseInt(pin);
                    pinconf2 = Integer.parseInt(pinconf);
                }
                if(TextUtils.isEmpty(name)) edtname.setError("Enter Your Name");
                else if (TextUtils.isEmpty(email)) edtemail.setError("Enter Your Email");
                else if(TextUtils.isEmpty(pin) || pin.length()!=6) edtpin.setError("Enter Your 6 Digit PIN");
                else if(TextUtils.isEmpty(pinconf)) edtpinconfirm.setError("Enter Yor PIN Confirmation");
                else if(pin2 != pinconf2) edtpinconfirm.setError("PIN Validation Incorrect !");
                else{
                    //INSERT
                    boolean isInserted = myDB.insertUSERS(name,null,email,phone,pin,0,1);
                    if(isInserted==true){
                        Intent intent = new Intent(CreateAccActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CreateAccActivity.this,"failed",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}
