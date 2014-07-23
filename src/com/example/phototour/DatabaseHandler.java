package com.example.phototour;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.phototour.Photographs;

public class DatabaseHandler extends SQLiteOpenHelper{
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "photographsManager.db";
    public static final String TABLE_PHOTOGRAPHS = "photographs";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    //============================================================================= change Photographs.java Double to String???===>changed(Commit??)
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LON = "longitude";
    //==============================================================================
    public static final String KEY_TMS = "timeStamp";
	
    private static final String CREATE_PHOTOGRAPHS_TABLE = "CREATE TABLE " + TABLE_PHOTOGRAPHS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
            + KEY_LAT + " TEXT," + KEY_LON + " TEXT,"  + KEY_TMS + " TEXT" + ") ";
    
    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PHOTOGRAPHS_TABLE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOGRAPHS);
        // Create tables again
        onCreate(db);	
	}	
}

