package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyChatOpener extends SQLiteOpenHelper {

    public static  final String TABLE_NAME = "CHATS";
    private static final String DATABASE_NAME = "Chats.db";
    private static final int VERSION_NUM = 3;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "_msg";
    public static final String COLUMN_SENT_OR_RECEIVED = "_sentOrReceived";


   public MyChatOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    //This function gets called if no database file exists.
    //Look on your device in the /data/data/package-name/database directory.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  COLUMN_SENT_OR_RECEIVED + " tinyint, "
                + COLUMN_MESSAGE  + " text);");
    }


    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create the new table:
        onCreate(db);
    }

}
