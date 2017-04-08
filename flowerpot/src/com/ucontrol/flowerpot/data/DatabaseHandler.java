package com.ucontrol.flowerpot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

    //Database Version
    private static final int DATABASE_VERSION=1;
    //Database Name
    private static final String DATABASE_NAME="flowerpot";
	 //constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_flowers_state="create table flowers_state(id INTEGER PRIMARY KEY,imagepath TEXT,name TEXT,Temperature TEXT,humidity TEXT,PH TEXT)";
		//String create_image="create table newsimage(id INTEGER PRIMARY KEY,newsid INTEGER,imagepath TEXT)";
		db.execSQL(create_flowers_state);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}



}
