package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteAccount extends AppCompatActivity {

    EditText name;
    Button submission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        name = findViewById(R.id.name_field);
        submission = findViewById(R.id.submit_area);

        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase db = databasehelper.getWritableDatabase();
                int i = databasehelper.deleteCustomer(Name,db);
                if(i>0)
                {
                    Toast.makeText(getApplicationContext(),"DATA SUCCESSFULLY DELETED",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"DATA NOT DELETED",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}