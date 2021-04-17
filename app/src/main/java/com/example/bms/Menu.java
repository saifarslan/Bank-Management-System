package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    TextView user,balance,swap;
    String number,name;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = findViewById(R.id.logged_user);
        balance = findViewById(R.id.user_balance);

        sharedPreferences = getSharedPreferences("REFERS",MODE_PRIVATE);
        String email = sharedPreferences.getString("Email",null);

        Toast.makeText(getApplicationContext(),"Logged User "+email,Toast.LENGTH_SHORT).show();

        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase database = databasehelper.getReadableDatabase();

        Cursor c2 = databasehelper.getCustomers_Name(database,email);
        Cursor c1 = databasehelper.getCustomers_balance(database,email);

        c1.moveToFirst();
        c2.moveToFirst();

        if(c2.moveToFirst())
        {
            name = c2.getString(c2.getColumnIndex("CUSTOMER_NAME"));
            user.setText(name);
        }
        if (c1.moveToFirst()) {
            number = c1.getString(c1.getColumnIndex("CUSTOMER_BALANCE"));
            balance.setText(number);
        }


    }


    public void Funds_Transfer(View view) {
        Intent intent = new Intent(this,FundsTransfer.class);
        startActivity(intent);
    }

    public void Add_Beneficiary(View view) {
        Intent intent = new Intent(this,AddBeneficiary.class);
        startActivity(intent);
    }

    public void Bill_Payment(View view) {
        Intent intent = new Intent(this,Bill_Payment.class);
        startActivity(intent);
    }

    public void Account_Info(View view) {
        display_account_info();

    }
    public void display_account_info(){
        Intent intent = new Intent(this,AccountInfo.class);
        startActivity(intent);
    }
}