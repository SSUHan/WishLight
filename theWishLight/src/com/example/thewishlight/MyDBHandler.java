package com.example.thewishlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDBHandler {
	MyDBOpenHelper helper;
	SQLiteDatabase db;

	// 초기화작업
	public MyDBHandler(Context context, String tablename) {
		// TODO Auto-generated constructor stub
		helper = new MyDBOpenHelper(context, "ljs93kr.db", tablename, null, 1);
	}

	// open
	public static MyDBHandler open(Context context, String tablename) {
		return new MyDBHandler(context, tablename);
	}

	// close
	public void close() {
		db.close();
	}

	// insert - wlb
	public void insert(String title, String contents, int shape) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("title", title);
		values.put("content", contents);
		values.put("shape", shape);
		db.insert("wlb", null, values);
	}

	

	public void update() {
	};

	// delete
	public void delete(String title) {
		db = helper.getWritableDatabase();
		db.delete("wlb", "title=?", new String[] { title });
	}

	public Cursor select() {
		db = helper.getReadableDatabase();
		Cursor c = db.query("wlb", null, null, null, null, null, null);
		return c;
	}
}
