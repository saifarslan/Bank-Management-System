package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInfo extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView name,number,email,account,password;
    String identity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        //found views by id
        name = findViewById(R.id.name_text);
        email = findViewById(R.id.email_text);
        number = findViewById(R.id.phone_text);
        password = findViewById(R.id.password_text);
        account = findViewById(R.id.account_text);

        //variables to be used
        String mail,contact,pass,acc;


        sharedPreferences = getSharedPreferences("REFERS",MODE_PRIVATE);
        mail = sharedPreferences.getString("Email",null);

        //Set Email
        email.setText(mail);




        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase database = databasehelper.getReadableDatabase();

        //Set Name
        Cursor cursor = databasehelper.getCustomers_Name(database,mail);
        cursor.moveToFirst();
        if(cursor.moveToFirst())
        {
            identity = cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"));
            name.setText(identity);
        }
        //Set Password
        Cursor c1 = databasehelper.getCustomers_pass(database,identity);
        c1.moveToFirst();
        if(c1.moveToFirst())
        {
            pass = c1.getString(c1.getColumnIndex("CUSTOMER_PASSWORD"));
            password.setText(pass);
        }

        //Set Contact
        Cursor c3 = databasehelper.getCustomers_phone(database,identity);
        c3.moveToFirst();
        if(c3.moveToFirst())
        {
            contact = c3.getString(c3.getColumnIndex("CUSTOMER_PHONE"));
            number.setText(contact);
        }

        //Set Account Number
        Cursor c4 = databasehelper.getCustomers_acc(database,identity);
        c4.moveToFirst();
        if(c4.moveToFirst())
        {
            acc = c4.getString(c4.getColumnIndex("CUSTOMER_ACCOUNT"));
            account.setText(acc);
        }




    }

    public void edit_name(View view) {
    }

    public void edit_phone(View view) {
    }

    public void edit_email(View view) {
    }

    public void edit_password(View view) {
    }
}