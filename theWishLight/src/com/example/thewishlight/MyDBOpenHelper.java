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
		String sql = "create table " + tableName + " ( "
				+ " _id integer primary key autoincrement , "
				+ " title text , " + " content text , " + " shape integer ) ";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.d("myDBopenHelper", "onUpgrade called");
		String sql = "drop table if exist "+tableName;
		db.execSQL(sql);

	}

}
