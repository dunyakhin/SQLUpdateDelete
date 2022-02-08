package com.example.sqlupgradedelete;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, databaseVer);
    }
    public static final int databaseVer=1;
    public static final String DB_NAME="contactDB";
    public static final String TABLE_NAME="tableDB";
    public static final String ID="_id";
    public static final String NAME="name";
    public static final String EMAIL="email";




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + ID + " integer primary key, " +
                NAME + " text," + EMAIL + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
