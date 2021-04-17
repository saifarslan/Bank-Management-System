package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBeneficiary extends AppCompatActivity {

    EditText email,contact,account;
    Button addbenificary;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);

        email = findViewById(R.id.email);
        contact = findViewById(R.id.phone);
        account = findViewById(R.id.acc_no);

        addbenificary = findViewById(R.id.addbeneficiary);

        sharedPreferences = getSharedPreferences("REFERS",MODE_PRIVATE);
        String Whose = sharedPreferences.getString("Email",null);

        addbenificary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String phone = contact.getText().toString();
                String number = account.getText().toString();

                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase database = databasehelper.getReadableDatabase();

                if(databasehelper.Verify_Beneficiary(database,mail,phone,number) == true)
                {
                    Databasehelper databasehelper1 = new Databasehelper(getApplicationContext());
                    SQLiteDatabase sqLiteDatabase = databasehelper1.getWritableDatabase();
                    databasehelper1.saveBeneficiary(getApplicationContext(),Whose,mail,sqLiteDatabase);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"NO SUCH BENEFICIARY EXISTS",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void save(String value1,String value2)
    {
        Databasehelper databasehelper = new Databasehelper(getApplicationContext());
        SQLiteDatabase db = databasehelper.getWritableDatabase();
        databasehelper.saveBeneficiary(getApplicationContext(),value1,value2,db);
    }
}