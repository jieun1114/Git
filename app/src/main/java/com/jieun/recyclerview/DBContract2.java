package com.jieun.recyclerview;

import android.provider.BaseColumns;

public class DBContract2 implements BaseColumns {

    private DBContract2(){}

    public static final String TBL_BUDGET = "BUDGET_T" ;
    public static final String COL_PRICE = "PRICE" ;
    public static final String COL_PLACE = "PLACE" ;

    // CREATE TABLE IF NOT EXISTS MYTRIP_T (TIME TEXT, PLACE TEXT)
    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_BUDGET + " " +
            "(" + DBContract2._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_PLACE + " TEXT" + ", " + COL_PRICE + " TEXT" + ")" ;

    // DROP TABLE IF EXISTS MYTRIP_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_BUDGET ;

    // SELECT * FROM MYTRIP_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_BUDGET ;

    // INSERT OR REPLACE INTO MYTRIP_T (NO, PLACE) VALUES (x, x)
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_BUDGET + " " +
            "("  + COL_PLACE + ", " + COL_PRICE + ") VALUES " ;

    // DELETE FROM MYTRIP_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_BUDGET ;

    public static final String SQL_UPDATE = "UPDATE " + TBL_BUDGET + " SET ";
}