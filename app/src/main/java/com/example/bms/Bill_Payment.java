package com.example.bms;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Bill_Payment extends AppCompatActivity {

    Spinner type;
    EditText id,amount;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill__payment);

        type = (Spinner) findViewById(R.id.bill_type);

        id = (EditText) findViewById(R.id.bill_id);
        amount = (EditText) findViewById(R.id.bill_amount);

        submit = (Button) findViewById(R.id.Pay);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Electricity");
        arrayList.add("Gas");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter);
        String directed = type.getSelectedItem().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identity = id.getText().toString();
                String Amount = amount.getText().toString();
                //Toast.makeText(getApplicationContext(),""+identity+" "+Amount,Toast.LENGTH_SHORT).show();

                Databasehelper databasehelper = new Databasehelper(getApplicationContext());
                SQLiteDatabase database = databasehelper.getWritableDatabase();
                databasehelper.savebill(getApplicationContext(),identity,directed,Amount,database);
            }
        });

        
    }
}