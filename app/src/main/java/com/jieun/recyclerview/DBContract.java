package com.jieun.recyclerview;

import android.provider.BaseColumns;

public class DBContract implements BaseColumns {

    private DBContract(){}

    public static final String TBL_MYTRIP = "MYTRIP_T" ;
    public static final String COL_TIME = "TIME" ;
    public static final String COL_PLACE = "PLACE" ;

    // CREATE TABLE IF NOT EXISTS MYTRIP_T (TIME TEXT, PLACE TEXT)
    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_MYTRIP + " " +
            "(" + DBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TIME + " TEXT" + ", " + COL_PLACE + " TEXT" + ")" ;

    // DROP TABLE IF EXISTS MYTRIP_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_MYTRIP ;

    // SELECT * FROM MYTRIP_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_MYTRIP ;

    // INSERT OR REPLACE INTO MYTRIP_T (NO, PLACE) VALUES (x, x)
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_MYTRIP + " " +
            "(" + COL_TIME + ", " + COL_PLACE + ") VALUES " ;

    // DELETE FROM MYTRIP_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_MYTRIP ;
}
