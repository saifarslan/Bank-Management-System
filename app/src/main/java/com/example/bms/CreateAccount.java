package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class CreateAccount extends AppCompatActivity {
     EditText mail,pass,name,phone;
     Button singup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.contact);
        singup = (Button) findViewById(R.id.sign_up);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mail.getText().toString();
                String Pass = pass.getText().toString();
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                String account = AccountNumber();



                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase database = databasehelper.getWritableDatabase();
                databasehelper.saveCustomer(getApplicationContext(),Name,Email,Pass,Phone,account,database);

                //Toast.makeText(getApplicationContext()," "+account,Toast.LENGTH_SHORT).show();
            }
        });


    }
    public String AccountNumber ()
    {
        Random rand = new Random();
        String card = "BM";
        for (int i = 0; i < 14; i++)
        {
            int n = rand.nextInt(10) + 0;
            card += Integer.toString(n);
        }
        return card;
    }
/*    public boolean Validate (String Name,String Email,String Phone)
    {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String phonePattern = "^[+]?[0-9]{10,13}$";
        if (Name.isEmpty() || Name.equals(null) || Email.equals(null) || Email.isEmpty() || Phone.isEmpty() || Phone.equals(null))
        {
            Toast.makeText(getApplicationContext(),"Cant Create Account One Or more Field Empty or null !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(Email.matches(emailPattern))
            {
                if(Phone.matches(phonePattern))
                {
                    return true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            }
        }
    }*/



}