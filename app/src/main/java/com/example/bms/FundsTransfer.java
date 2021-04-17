package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FundsTransfer extends AppCompatActivity {

    Spinner list;
    EditText amount;
    Button submission;
    SharedPreferences sharedPreferences;
    //int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_transfer);

        list = findViewById(R.id.dropdown_list);
        submission = findViewById(R.id.submit);
        amount = findViewById(R.id.amount);


        sharedPreferences = getSharedPreferences("REFERS",MODE_PRIVATE);
        String mail = sharedPreferences.getString("Email",null);

        //Getting Values from Database
        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase database = databasehelper.getReadableDatabase();

        ArrayList<String> arrayList = databasehelper.getBeneficiary(mail);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
        list.setAdapter(arrayAdapter);
        String directed = list.getSelectedItem().toString();

        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = amount.getText().toString();
                if(!"".equals(value))
                {
                    int a  = Integer.parseInt(value);

                    int current_balance = data(mail);
                    int  add  = data(directed);

                    if(current_balance>=a)
                    {
                        int sum = current_balance-a;
                        Transaction(sum,mail);

                        int update_balance = add+a;
                        Transaction(update_balance,directed);

                        Toast.makeText(getApplicationContext(),"Transaction Successful ",Toast.LENGTH_SHORT).show();


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"INSUFFICIENT AMOUNT ",Toast.LENGTH_SHORT).show();
                    }

                }



            }

        });

        
    }
    public int data(String email)
    {
        int user_balance = 0;
        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = databasehelper.getReadableDatabase();
        Cursor cursor = databasehelper.getCustomers_balance(sqLiteDatabase,email);
        cursor.moveToFirst();
        if(cursor.moveToFirst())
        {
            user_balance = cursor.getInt(cursor.getColumnIndex("CUSTOMER_BALANCE"));
        }
        return user_balance;


    }
    public void Transaction(int amount, String email)
    {
        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = databasehelper.getWritableDatabase();
        databasehelper.depositAmount2(sqLiteDatabase,amount,email);

    }

}