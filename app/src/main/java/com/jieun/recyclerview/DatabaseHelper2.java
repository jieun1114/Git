package com.jieun.recyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "budget1.db" ;

    public DatabaseHelper2(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DBContract2.SQL_CREATE_TBL);
    }

    public void onUpgrade(SQLiteDatabase db,int oldversion, int newversion){
        db.execSQL(DBContract2.SQL_DROP_TBL);
        onCreate(db);
    }

}