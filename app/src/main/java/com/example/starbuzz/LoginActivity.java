package com.example.starbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText edtEnterPIN;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    ImageView btnback,btndone;
    ConstraintLayout layoutLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDB = new DatabaseHelper(this);

        edtEnterPIN = findViewById(R.id.edtEnterPIN);
        edtEnterPIN.setEnabled(false);

        layoutLogin = findViewById(R.id.layoutLogin);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnback = findViewById(R.id.btnback);
        btndone = findViewById(R.id.btndone);

        //method
        click(btn1);
        click(btn2);
        click(btn3);
        click(btn4);
        click(btn5);
        click(btn6);
        click(btn7);
        click(btn8);
        click(btn9);
        click(btn0);
        clickback(btnback);
        clickdone(btndone);

    }

    public void click (final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PINtext = edtEnterPIN.getText().toString();
                String btnText = btn.getText().toString();
                PINtext = PINtext + btnText ;
                edtEnterPIN.setText(PINtext);

            }
        });
    }

    public void clickback (ImageView btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = edtEnterPIN.getText().length();
                if(length>0){
                    edtEnterPIN.getText().delete(length-1,length);
                }
            }
        });
    }

    public void clickdone (ImageView btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDB.getUSERSifLOGIN();
                if(res.getCount()==0){
                    Toast.makeText(LoginActivity.this,"ERROR, NOTHING FOUND",Toast.LENGTH_LONG).show();
                }else{
                    while(res.moveToNext()){
                        String yourPIN = res.getString(5);
                        String inputPIN = "0";
                        if(!TextUtils.isEmpty(edtEnterPIN.getText().toString())){
                             inputPIN = edtEnterPIN.getText().toString();
                        }

                        if(Integer.parseInt(yourPIN) == Integer.parseInt(inputPIN)){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            YoYo.with(Techniques.Shake)  //Animation bisa diganti
                                    .duration(500) //milli second - 1000 = 1 s
                                    .repeat(0)
                                    .playOn(layoutLogin);

                            Toast.makeText(LoginActivity.this,"Invalid PIN!",Toast.LENGTH_SHORT).show();

                        }
                    }



                }
            }
        });
    }
}
