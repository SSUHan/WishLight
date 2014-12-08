package com.example.thewishlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MyDBHandler {
	MyDBOpenHelper helper;
	SQLiteDatabase db;
	String tableName;

	// 초기화작업
	public MyDBHandler(Context context, String tablename) {
		// TODO Auto-generated constructor stub
		helper = new MyDBOpenHelper(context, "alarmlist.db", tablename, null, 1);
		tableName = tablename;
		
	}

	// open
	public void open() throws SQLiteException {
		try{
			
			db = helper.getWritableDatabase();
		}catch(SQLiteException ex){
			db = helper.getReadableDatabase();
		}
	}

	// close
	public void close() {
		db.close();
	}
	
	public void removeTable(){
		String sql = "drop table "+tableName;
		db.execSQL(sql);
	}

	// insert - alarm
	public void insert(int seq, int year, int month, int date, int hour, int minute) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("seq", seq);
		
		values.put("year", year);
		values.put("month", month);
		values.put("date", date);
		values.put("hour", hour);
		values.put("minute", minute);
		
		db.insert(tableName, null, values);
	}

	

	public void update() {
	};

	public void removeData(int seq){
        String sql = "delete from " + tableName + " where seq = "+seq+";";
        db.execSQL(sql);
    }
	// delete
//	public void delete(int seq) {
//		db = helper.getWritableDatabase();
//		db.delete(tableName, "seq=?", new String[] { seq });
//	}

	public Cursor select() {
		db = helper.getReadableDatabase();
		Cursor c = db.query(tableName, null, null, null, null, null, null);
		return c;
	}
	
	int seq2, year2, month2, date2, hour2, minute2;
	// Data 읽기(꺼내오기)
	public String selectData(int seq) {
		String sql = "SELECT * FROM " + tableName + " where seq = " + seq;
		Cursor s = db.rawQuery(sql, null);
		if(s.getCount()==0){
			return null;
		}
		if (s.moveToFirst()) {
			seq2 = s.getInt(s.getColumnIndex("seq"));
			year2 = s.getInt(s.getColumnIndex("year"));
			month2 = s.getInt(s.getColumnIndex("month"));
			date2 = s.getInt(s.getColumnIndex("date"));
			hour2 = s.getInt(s.getColumnIndex("hour"));
			minute2 = s.getInt(s.getColumnIndex("minute"));
		}
		String data2 = year2 + "." + month2 + "." + date2+"."+hour2+"."+minute2;

		
		return data2;
	}
}
