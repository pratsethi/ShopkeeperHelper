package com.shopkeeperhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PSethi on 23-Dec-16.
 */

public class DataBaseSQL extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table balDetails(name text,phone text,address text,balance integer)");
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS balDetails");
        onCreate(sqLiteDatabase);
    }

    public void insertData(String name,String phone,String address,int balance)
    {
        if(sqLiteDatabase == null)
        {
            onCreate(getWritableDatabase());
        }
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("address",address);
        values.put("balance",balance);
        sqLiteDatabase.update("balDetails",values,"",null);
    }
}
