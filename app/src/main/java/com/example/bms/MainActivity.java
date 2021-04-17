package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.bms.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    Button login,create;
    EditText user,pass;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user =  findViewById(R.id.email);
        pass =  findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        create = (Button) findViewById(R.id.create);

        final SharedPreferences prefs = MainActivity.this.getSharedPreferences("REFERS",Context.MODE_PRIVATE);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Pass = pass.getText().toString();
                User = user.getText().toString();
                String admin = "Saif";
                String pass = "123";

                if(User.equals(admin) && Pass.equals(pass))
                {
                    displayadmin();
                }
                else
                {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Email",User);
                    editor.apply();
                    validate(User,Pass);

                    /*Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                    SQLiteDatabase database = databasehelper.getWritableDatabase();
                    databasehelper.saveSession(getApplication(),User,database);*/
                }
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreateAccount();
            }
        });


    }
    public void displayadmin()
    {
        Intent intent = new Intent(this,admin.class);
        startActivity(intent);
    }
    public void displaymenu()
    {
        Intent intent = new Intent(this,Menu.class);
        startActivity(intent);
    }
    public void displayCreateAccount()
    {
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
    }
    public void validate (String user, String password) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(user.isEmpty() || user.equals(null) || password.isEmpty() || password.equals(null) ) {
            Toast.makeText(getApplicationContext(),"User Name or Password is Empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(user.matches(emailPattern))
            {

                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase database = databasehelper.getReadableDatabase();

                if(databasehelper.verifyUser(database, user, password) == true){

                    displaymenu();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email or password Invalid", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
            }

        }
    }
}