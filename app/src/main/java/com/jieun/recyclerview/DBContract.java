package com.jieun.recyclerview;


import android.provider.BaseColumns;

public class DBContract implements BaseColumns {

    private DBContract(){}

    public static final String TBL_MYTRIP = "MYTRIP_T" ;
    public static final String COL_DAY = "DAY";
    public static final String COL_TIME = "TIME" ;
    public static final String COL_PLACE = "PLACE" ;


    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_MYTRIP + " " +
            "(" + DBContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DAY + " TEXT" + ", " +
            COL_TIME + " TEXT" + ", " + COL_PLACE + " TEXT" + ")" ;

    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_MYTRIP ;

    public static final String SQL_SELECT = "SELECT * FROM " + TBL_MYTRIP ;

    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_MYTRIP + " " +
            "("  + COL_DAY + ", " + COL_TIME + ", " + COL_PLACE + ") VALUES " ;

    public static final String SQL_DELETE = "DELETE FROM " + TBL_MYTRIP ;

    public static final String SQL_UPDATE = "UPDATE " + TBL_MYTRIP + " SET ";
}
