package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin extends AppCompatActivity {

    EditText balance,account;
    Button submit,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        balance = findViewById(R.id.balance);
        account = findViewById(R.id.acc_no);
        submit = findViewById(R.id.deposit);
        delete = findViewById(R.id.delete);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = account.getText().toString();
                String amount = balance.getText().toString();
                int var = Integer.parseInt(amount);

                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase db = databasehelper.getWritableDatabase();

                    if(databasehelper.depositAmount(db,var,number) == true)
                    {

                        Toast.makeText(getApplicationContext(),"Amount Deposited", Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                        Toast.makeText(getApplicationContext(), "Cant Deposit Amount", Toast.LENGTH_LONG).show();
                    }



            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DisplayDelete();
            }
        });

    }
    public void DisplayDelete()
    {
        Intent intent = new Intent(this,DeleteAccount.class);
        startActivity(intent);
    }
}