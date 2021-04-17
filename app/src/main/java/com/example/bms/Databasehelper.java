package com.example.bms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Databasehelper  extends SQLiteOpenHelper {
        Context ctx;
        //Create Table
        private static String CREATE_CUSTOMER_TABLE = "Create Table " +
                DbContract.TBL_CUSTOMER + "(id integer primary key autoincrement, " +
                DbContract.CUSTOMER_NAME + " text, " +
                DbContract.CUSTOMER_EMAIL + " text, " +
                DbContract.CUSTOMER_PASSWORD + " text, " +
                DbContract.CUSTOMER_PHONE + " text, " +
                DbContract.CUSTOMER_ACCOUNT + " text,"+
                DbContract.CUSTOMER_BALANCE + " integer);";

    private static String CREATE_BENEFICIARY_TABLE = "Create Table " +
            DbContract.TBL_BENEFICIARY + "(id integer primary key autoincrement, " +
            DbContract.BENEFICIARY_WHOSE + " text, " +
            DbContract.BENEFICIARY_EMAIL + " text);";

    private static String CREATE_SESSION_TABLE = "Create Table " +
            DbContract.TBL_SESSION + "(id integer primary key autoincrement, " +
            DbContract.SESSION_EMAIL + " text);";

    private static String CREATE_BILL_TABLE = "Create Table " +
            DbContract.TBL_BILL + "(id integer primary key autoincrement, " +
            DbContract.BILL_IDENTITY + " text, " +
            DbContract.BILL_TYPE + " text, " +
            DbContract.BILL_AMOUNT + " text);";






    //Drop Table
        private static String DROP_CUSTOMER_Table = "Drop Table IF EXISTS " + DbContract.TBL_CUSTOMER;
        private static String DROP_BENEFICIARY_TABLE = "Drop Table IF EXISTS " + DbContract.TBL_BENEFICIARY;
        private static String DROP_SESSION_TABLE = "Drop Table IF EXISTS " +DbContract.TBL_SESSION;
        private static String DROP_BILL_TABLE = "Drop Table IF EXISTS " +DbContract.TBL_BILL;

        //Constructor
        public Databasehelper(Context ctx){
            super(ctx, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_CUSTOMER_TABLE);
            sqLiteDatabase.execSQL(CREATE_BENEFICIARY_TABLE);
            sqLiteDatabase.execSQL(CREATE_SESSION_TABLE);
            sqLiteDatabase.execSQL(CREATE_BILL_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_CUSTOMER_Table);
            db.execSQL(DROP_BENEFICIARY_TABLE);
            db.execSQL(DROP_SESSION_TABLE);
            db.execSQL(DROP_BILL_TABLE);
            onCreate(db);
        }
    public void savebill(Context ctx, String id, String type, String amount,SQLiteDatabase db){
        try{
            ContentValues cv = new ContentValues();
            cv.put(DbContract.BILL_IDENTITY, id);
            cv.put(DbContract.BILL_TYPE, type);
            cv.put(DbContract.BILL_AMOUNT, amount);

            db.insert(DbContract.TBL_BILL, null, cv);
            Toast.makeText(ctx,"Successful !", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Log.e("MY_APP", "Exception: " + e.getMessage());
            Log.e("MY_APP", "Exception: " + e.toString());
            Toast.makeText(ctx, "Cant PAY !", Toast.LENGTH_LONG).show();
        }
    }
    public void saveBeneficiary(Context ctx, String Name, String email,SQLiteDatabase db){
        try{
            ContentValues cv = new ContentValues();
            cv.put(DbContract.BENEFICIARY_WHOSE, Name);
            cv.put(DbContract.BENEFICIARY_EMAIL, email);

            db.insert(DbContract.TBL_BENEFICIARY, null, cv);
            Toast.makeText(ctx,"Successful !", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Log.e("MY_APP", "Exception: " + e.getMessage());
            Log.e("MY_APP", "Exception: " + e.toString());
            Toast.makeText(ctx, "Can't ADD", Toast.LENGTH_LONG).show();
        }
    }
    public void saveSession(Context ctx, String email ,SQLiteDatabase db){
        try{
            ContentValues cv = new ContentValues();
            cv.put(DbContract.SESSION_EMAIL, email);


            db.insert(DbContract.TBL_SESSION, null, cv);
            Toast.makeText(ctx,"Successful !", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Log.e("MY_APP", "Exception: " + e.getMessage());
            Log.e("MY_APP", "Exception: " + e.toString());
            Toast.makeText(ctx, "Can't ADD", Toast.LENGTH_LONG).show();
        }
    }

        //Write Data
        public void saveCustomer(Context ctx, String Name, String email, String password, String phone, String account,SQLiteDatabase db){
            try{
                ContentValues cv = new ContentValues();
                cv.put(DbContract.CUSTOMER_NAME, Name);
                cv.put(DbContract.CUSTOMER_EMAIL, email);
                cv.put(DbContract.CUSTOMER_PASSWORD, password);
                cv.put(DbContract.CUSTOMER_PHONE, phone);
                cv.put(DbContract.CUSTOMER_ACCOUNT, account);

                db.insert(DbContract.TBL_CUSTOMER, null, cv);
                Toast.makeText(ctx,"Sing Up Successful", Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                Log.e("MY_APP", "Exception: " + e.getMessage());
                Log.e("MY_APP", "Exception: " + e.toString());
                Toast.makeText(ctx, "Cant Sign Up", Toast.LENGTH_LONG).show();
            }
        }

        //Read Data
        public Cursor getCustomers(SQLiteDatabase db){
            String[]  projection = {
                    DbContract.CUSTOMER_NAME,
                    DbContract.CUSTOMER_EMAIL,
                    DbContract.CUSTOMER_PASSWORD,
                    DbContract.CUSTOMER_PHONE,
                    DbContract.CUSTOMER_ACCOUNT,
            };
            return (db.query(DbContract.TBL_CUSTOMER, projection, null, null,null,null,null));
        }

    //Check Email and Pass
    public boolean verifyUser(SQLiteDatabase db, String Email, String Password){
        String[]  projection = {
                DbContract.CUSTOMER_EMAIL,
                DbContract.CUSTOMER_PASSWORD,
        };
        String selection = DbContract.CUSTOMER_EMAIL + " = ?" + " AND " + DbContract.CUSTOMER_PASSWORD + " = ?";
        String[] selectionArgs = {Email, Password};

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER,  //Table to query
                projection,                                //columns to return
                selection,                                 //columns for the WHERE clause
                selectionArgs,                             //The values for the WHERE clause
                null,                              //group the rows
                null    ,                           //filter by row groups
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }else{
            return false;
        }
    }


    //Deposit
    public boolean depositAmount(SQLiteDatabase db,int balance,String account) {
        try{
            ContentValues cv = new ContentValues();
            cv.put(DbContract.CUSTOMER_BALANCE, balance);
            db.update(DbContract.TBL_CUSTOMER, cv, "CUSTOMER_ACCOUNT=?", new String[]{ account });
            return true;
        }
        catch(Exception e){
            Log.e("MY_APP", "Exception: " + e.getMessage());
            Log.e("MY_APP", "Exception: " + e.toString());
            return false;
        }
    }

    public boolean Verify_Beneficiary(SQLiteDatabase db, String Email, String Contact,String Account){
        String[]  projection = {
                DbContract.CUSTOMER_EMAIL,
                DbContract.CUSTOMER_PHONE,
                DbContract.CUSTOMER_ACCOUNT,
        };
        String selection = DbContract.CUSTOMER_EMAIL + " = ?" + " AND " + DbContract.CUSTOMER_PHONE + " = ?" + " AND " + DbContract.CUSTOMER_ACCOUNT + " = ?";
        String[] selectionArgs = {Email, Contact, Account};

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER,  //Table to query
                projection,                                //columns to return
                selection,                                 //columns for the WHERE clause
                selectionArgs,                             //The values for the WHERE clause
                null,                              //group the rows
                null    ,                           //filter by row groups
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }else{
            return false;
        }
    }
    //Delete
    public int deleteCustomer(String name, SQLiteDatabase db){
            return db.delete(DbContract.TBL_CUSTOMER, "CUSTOMER_NAME=?", new String[]{ name });
        }
    public Cursor getCustomers_Name(SQLiteDatabase db,String mail){
        String[]  projection = {
                DbContract.CUSTOMER_NAME,
        };
        String selection = DbContract.CUSTOMER_EMAIL + " = ?";
        String[] selectionArgs = { mail };

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }
    public Cursor getCustomers_balance(SQLiteDatabase db,String mail){
        String[]  projection = {
                DbContract.CUSTOMER_BALANCE,
        };
        String selection = DbContract.CUSTOMER_EMAIL + " = ?";
        String[] selectionArgs = { mail };
        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }
    public Cursor getCustomers_email(SQLiteDatabase db,String name){
        String[]  projection = {
                DbContract.CUSTOMER_EMAIL,
        };
        String selection = DbContract.CUSTOMER_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }
    public Cursor getCustomers_pass(SQLiteDatabase db,String name){
        String[]  projection = {
                DbContract.CUSTOMER_PASSWORD,
        };
        String selection = DbContract.CUSTOMER_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }
    public Cursor getCustomers_phone(SQLiteDatabase db,String name){
        String[]  projection = {
                DbContract.CUSTOMER_PHONE,
        };
        String selection = DbContract.CUSTOMER_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }
    public Cursor getCustomers_acc(SQLiteDatabase db,String name){
        String[]  projection = {
                DbContract.CUSTOMER_ACCOUNT,
        };
        String selection = DbContract.CUSTOMER_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(DbContract.TBL_CUSTOMER, projection, selection, selectionArgs, null,null,null,null);
        return cursor;
    }

    public ArrayList<String> getBeneficiary(String whose) {
            ArrayList<String> list = new ArrayList<String>();
            SQLiteDatabase db = this.getReadableDatabase();
            db.beginTransaction();
            try
            {
                Cursor cursor = db.rawQuery("Select * From " + DbContract.TBL_BENEFICIARY + " Where " + DbContract.BENEFICIARY_WHOSE + "=?", new String[] {whose});
                if(cursor.getCount()>0)
                {
                    while (cursor.moveToNext())
                    {
                        String emails = cursor.getString(cursor.getColumnIndex("BENEFICIARY_EMAIL"));
                        list.add(emails);
                    }
                }
                db.setTransactionSuccessful();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                db.endTransaction();
                db.close();
            }
            return list;

    }

    public boolean depositAmount2(SQLiteDatabase db, int balance,String mail) {
        try{
            ContentValues cv = new ContentValues();
            cv.put(DbContract.CUSTOMER_BALANCE, balance);
            db.update(DbContract.TBL_CUSTOMER, cv, "CUSTOMER_EMAIL=?", new String[]{ mail });
            return true;
        }
        catch(Exception e){
            Log.e("MY_APP", "Exception: " + e.getMessage());
            Log.e("MY_APP", "Exception: " + e.toString());
            return false;
        }
    }


}








