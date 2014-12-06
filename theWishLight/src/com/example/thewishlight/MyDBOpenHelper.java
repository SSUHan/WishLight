package com.example.thewishlight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpenHelper extends SQLiteOpenHelper {

	String tableName;

	public MyDBOpenHelper(Context context, String dbname, String tablename,
			CursorFactory factory, int version) {
		super(context, dbname, factory, version);
		tableName = tablename;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("myDBopenHelper", "onCreate called");
		String sql = "create table if not exists " + tableName + " ( "
				+ " seq integer  , "+" year integer  , "
				+ " month integer , " + " date integer , " + " hour integer , "+" minute integer ); ";
		Log.d("helper sql", sql);
		db.execSQL(sql);

	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table if not exists " + tableName + " ( "
				+ " seq integer  , "+" year integer  , "
				+ " month integer , " + " date integer , " + " hour integer , "+" minute integer ); ";
		Log.d("openhelper sql", sql);
		db.execSQL(sql);
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("myDBopenHelper", "onUpgrade called");
		String sql = "drop table if exist "+tableName;
		db.execSQL(sql);

	}

}
