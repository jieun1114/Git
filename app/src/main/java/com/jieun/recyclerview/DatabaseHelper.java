package com.jieun.recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "mytrip.db" ;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DBContract.SQL_CREATE_TBL);
    }

    public void onUpgrade(SQLiteDatabase db,int oldversion, int newversion){
        db.execSQL(DBContract.SQL_DROP_TBL);
        onCreate(db);
    }

}
