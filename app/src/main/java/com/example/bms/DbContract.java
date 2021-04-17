package com.example.bms;

public class DbContract {
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "CUSTOMER_DB.db";

    //Table
    public static  final String TBL_CUSTOMER = "TBL_CUSTOMER";
    //Column
    public static final  String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final  String CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    public static final  String CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD";
    public static final  String CUSTOMER_PHONE = "CUSTOMER_PHONE";
    public static final  String CUSTOMER_ACCOUNT = "CUSTOMER_ACCOUNT";
    public static final  String CUSTOMER_BALANCE = "CUSTOMER_BALANCE";


    //Another Table
    public static final String TBL_BENEFICIARY = "TBL_BENEFICIARY";
    //Column
    public static final String BENEFICIARY_WHOSE = "BENEFICIARY_WHOSE";
    public static final String BENEFICIARY_EMAIL = "BENEFICIARY_EMAIL";

    //Another Table
    public static final String TBL_SESSION = "TBL_SESSION";
    //Column
    public static final String SESSION_EMAIL = "SESSION_EMAIL";

    public static final String TBL_BILL = "TBL_BILL";
    //Column
    public static final String BILL_IDENTITY = "BILL_ID";
    public static final String BILL_TYPE = "BILL_TYPE";
    public static final String BILL_AMOUNT = "BILL_AMOUNT";






}
