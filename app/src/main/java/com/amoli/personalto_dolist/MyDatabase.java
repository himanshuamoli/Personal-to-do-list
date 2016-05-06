package com.amoli.personalto_dolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Himanshu on 5/4/2016.
 */
public class MyDatabase extends SQLiteOpenHelper{

    public MyDatabase(Context context) {
        super(context, "myfile", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table List(id Integer Primary Key autoincrement,title text,desc text,cat text,place text,date text,time text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String title,String desc,String cat,String place,String date,String time )
    {   SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Insert into List(title,desc,cat,place,date,time) Values('"+title+"','"+desc+"','"+cat+"','"+place+"','"+date+"','"+time+"')");
    }

    public Cursor query(String cat)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("Select * from List where cat='"+cat+"'",null);

    }
}
