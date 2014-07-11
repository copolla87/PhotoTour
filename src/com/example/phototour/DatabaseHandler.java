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
	
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "photographsManager";
    private static final String TABLE_PHOTOGRAPHS = "photographs";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    //============================================================================= change Photographs.java Double to String???===>changed(Commit??)
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LON = "longitude";
    //==============================================================================
    private static final String KEY_TMS = "timeStamp";
	
    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	
	}
    


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PHOTOGRAPHS_TABLE = "CREATE TABLE " + TABLE_PHOTOGRAPHS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_LAT + " DOUBLE" + KEY_LON + "DOUBLE"  + KEY_TMS + "String" + ")";
		db.execSQL(CREATE_PHOTOGRAPHS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOGRAPHS);
 
        // Create tables again
        onCreate(db);
		
	}
	
	//CRUD operations
	void addPhotograph(Photographs photograph){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, photograph.getName());
		values.put(KEY_LAT, photograph.getLatitude());
		values.put(KEY_LON, photograph.getLongitude());
		values.put(KEY_TMS, photograph.getTimeStamp());
		
		db.insert(TABLE_PHOTOGRAPHS, null, values);
		db.close();
	}
	
	Photographs getPhotograph(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_PHOTOGRAPHS, new String[] { KEY_ID }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Photographs photograph = new Photographs(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return photograph;
	}
	
	public List<Photographs> getPhotographs(){
		
		List<Photographs> list = new ArrayList<Photographs>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_PHOTOGRAPHS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()){
			do{
				Photographs photograph = new Photographs();
				photograph.setID(Integer.valueOf(cursor.getString(0)));
				photograph.setName(cursor.getString(1));
				photograph.setLatitude(cursor.getString(2));
				photograph.setLongitude(cursor.getString(3));
				photograph.setTimeStamp(cursor.getString(4));
				list.add(photograph);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}
	
}

