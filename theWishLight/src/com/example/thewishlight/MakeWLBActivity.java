package com.example.thewishlight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;





import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MakeWLBActivity extends ActionBarActivity {

	MyDBHandler handler;

	EditText editTitle;
	EditText editContent;
	EditText editShape;

	Button makeSave;

	Button finishDatePicker;
	Button popupTimePicker;

	private GregorianCalendar mCalendar; // 시작날짜
	private GregorianCalendar eCalendar; // 끝날날짜
	private int s_year, s_month, s_date, hour, minute; // 시작 날짜 와 팝업 시간
	private int e_year, e_month, e_date; // 마감 날짜
	Button day1, day2, day3, day4, day5, day6, day7;
	boolean[] daySelect;

	int isSecret = 0;

	phpUp task2;

	public AlarmManager mManager;
	
	private int duration;
	List<Integer> selectedDay;
	List<String> selectedDate;
	String startdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_wlb);

		// 알람매니저를 흭득
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// 요일버튼 객체받기
		day1 = (Button) findViewById(R.id.day1);
		day2 = (Button) findViewById(R.id.day2);
		day3 = (Button) findViewById(R.id.day3);
		day4 = (Button) findViewById(R.id.day4);
		day5 = (Button) findViewById(R.id.day5);
		day6 = (Button) findViewById(R.id.day6);
		day7 = (Button) findViewById(R.id.day7);

		daySelect = new boolean[7];
		for (boolean s : daySelect) {
			s = false;
		}

		// 현재시간 흭득
		mCalendar = new GregorianCalendar();
		eCalendar = new GregorianCalendar();
		Log.d("homework", mCalendar.getTime().toString());

		// 현재 시작 날짜 받기
		s_year = mCalendar.get(Calendar.YEAR);
		s_month = mCalendar.get(Calendar.MONTH);
		s_date = mCalendar.get(Calendar.DAY_OF_MONTH);

		e_year = s_year;
		e_month = s_month;
		e_date = s_date;

		hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		minute = mCalendar.get(Calendar.MINUTE);

		popupTimePicker = (Button) findViewById(R.id.popupTimePicker);
		popupTimePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerDialog(MakeWLBActivity.this, timeSetListener,
						hour, minute, false).show();
			}
		});

	}

	// 마감날짜 설정하기
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			e_year = year;
			e_month = monthOfYear;
			e_date = dayOfMonth;
			eCalendar.set(year, monthOfYear, dayOfMonth, hour, minute);
			Log.d("datePickerAfter", "시작날짜:" + s_year + s_month + s_date
					+ "마감날짜:" + e_year + e_month + e_date);

		}
	};

	// 팝업시간 설정하기
	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int Minute) {
			// TODO Auto-generated method stub
			Log.d("timePickerBefore", mCalendar.getTime().toString());
			hour = hourOfDay;
			minute = Minute;
			
			popupTimePicker.setText(hour+" : "+minute);
			popupTimePicker.setBackgroundColor(Color.TRANSPARENT);
			eCalendar.set(e_year, e_month, e_date, hour, minute);
			Log.d("timePickerAfter", "팝업시간설정:" + hour + minute);
			Log.d("timePicker e", "팝업시간설정:" +eCalendar.getTime().toString());
		}
	};

	public void dayClick(View v) {

	      switch (v.getId()) {
	      case R.id.day1:
	         if (daySelect[0] == false) {
	            day1.setBackgroundResource(R.drawable.mon2);
	            daySelect[0] = true;
	         } else if (daySelect[0] == true) {
	            day1.setBackgroundResource(R.drawable.mon);
	            daySelect[0] = false;
	         }
	         break;
	      case R.id.day2:
	         if (daySelect[1] == false) {
	            day2.setBackgroundResource(R.drawable.tue2);
	            daySelect[1] = true;
	         } else if (daySelect[1] == true) {
	            day2.setBackgroundResource(R.drawable.tue);
	            daySelect[1] = false;
	         }
	         break;
	      case R.id.day3:
	         if (daySelect[2] == false) {
	            day3.setBackgroundResource(R.drawable.wen2);
	            daySelect[2] = true;
	         } else if (daySelect[2] == true) {
	            day3.setBackgroundResource(R.drawable.wen);
	            daySelect[2] = false;
	         }
	         break;
	      case R.id.day4:
	         if (daySelect[3] == false) {
	            day4.setBackgroundResource(R.drawable.thr2);
	            daySelect[3] = true;
	         } else if (daySelect[3] == true) {
	            day4.setBackgroundResource(R.drawable.thr);
	            daySelect[3] = false;
	         }
	         break;
	      case R.id.day5:
	         if (daySelect[4] == false) {
	            day5.setBackgroundResource(R.drawable.fri2);
	            daySelect[4] = true;
	         } else if (daySelect[4] == true) {
	            day5.setBackgroundResource(R.drawable.fri);
	            daySelect[4] = false;
	         }
	         break;
	      case R.id.day6:
	         if (daySelect[5] == false) {
	            day6.setBackgroundResource(R.drawable.sat2);
	            daySelect[5] = true;
	         } else if (daySelect[5] == true) {
	            day6.setBackgroundResource(R.drawable.sat);
	            daySelect[5] = false;
	         }
	         break;
	      case R.id.day7:
	         if (daySelect[6] == false) {
	            day7.setBackgroundResource(R.drawable.sun2);
	            daySelect[6] = true;
	         } else if (daySelect[6] == true) {
	            day7.setBackgroundResource(R.drawable.sun);
	            daySelect[6] = false;
	         }
	         break;

	      }
	   }

	public void checkClick(View v) {
		switch (v.getId()) {

		case R.id.isSecret:
			if (isSecret == 0) {
				isSecret = 1;
			} else if (isSecret == 1) {
				isSecret = 0;
			}

		}
	}

	// 소원등 만들기 버튼을 눌렀을때
	public void saveClick(View v) {

		
		editShape = (EditText) findViewById(R.id.inputShape);
		editTitle = (EditText) findViewById(R.id.inputTitle);
		editContent = (EditText) findViewById(R.id.inputContent);

		String shape = editShape.getText().toString();
		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		// handler.insert(title, content, Integer.parseInt(shape));
		// handler.close();
		if (makeWLBID() == -1) {
			Toast.makeText(getApplicationContext(), "소원등은 5개 이상 만들 수 없습니다",
					Toast.LENGTH_LONG).show();
			return;
		}

		int rqCode = makeWLBID();
		Log.d("dayCal", String.valueOf(dayCal()));
		WLB wlb = new WLB(MySkyActivity.myID, makeWLBID(),
				Integer.valueOf(shape), title, content, hour + ":" + minute,
				s_year + "." + (s_month + 1) + "." + s_date, e_year + "."
						+ (e_month + 1) + "." + e_date, dayCal(), isSecret);
		
		
		//
		
		s_month= s_month+1;
		startdate = s_year + "." + s_month+ "." + s_date;
		Log.d("startdate",startdate);
		selectedDay = new ArrayList<Integer>();
		Log.d("s",String.valueOf(1));
		selectedDate = new ArrayList<String>();
		Log.d("s",String.valueOf(2));
		
		fillselectedDay(dayCal());
		Log.d("s",String.valueOf(3));
		try {
			fillselectedDate();
			Log.d("s",String.valueOf(4));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.d("s",String.valueOf(5));
			e.printStackTrace();
		}
		Log.d("s",String.valueOf(6));
		
		
		handler = new MyDBHandler(getApplicationContext(), ("wlb_"+String.valueOf(rqCode)));
		handler.open();
		StringTokenizer st;
		Log.d("s",String.valueOf(7));
		int seq =1;
		for(int i=0;i<selectedDate.size();i++){
			Log.d("s",String.valueOf(8));
			Log.d("selectedDate",selectedDate.get(i));
			st = new StringTokenizer(selectedDate.get(i),".");
			
			Log.d("s",String.valueOf(9));
			handler.insert(seq, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), hour, minute);
			Log.d("s",String.valueOf(10));
			//Log.d("selectedDate", "seq:"+seq+"e_year:"+st.nextToken()+"e_month:"+st.nextToken()+"e_date:"+st.nextToken());
			++seq;
		}
		Log.d("s",String.valueOf(11));
		
		Cursor c = handler.select();
		startManagingCursor(c);
		String data="";
		Log.d("s",String.valueOf(12));
		Log.d("db count", String.valueOf(c.getCount()));
		while (c.moveToNext()) {
			Log.d("s",String.valueOf(13));
			int seq1 = c.getInt(c.getColumnIndex("seq"));
			int year1 = c.getInt(c.getColumnIndex("year"));
			int month1 = c.getInt(c.getColumnIndex("month"));
			int date1 = c.getInt(c.getColumnIndex("date"));
			int hour1 = c.getInt(c.getColumnIndex("hour"));
			int minute1 = c.getInt(c.getColumnIndex("minute"));
			
			data += seq1 + " " + year1 + " " + month1 + " " + date1 + " "
					+ hour1 + " " + minute1 + "\n";
		}
		Log.d("all", data);
		
		String data2="";
		data2 = handler.selectData(1);
		Log.d("data2", data2);
		StringTokenizer st2 = new StringTokenizer(data2,".");
		e_year = Integer.parseInt(st2.nextToken());
		e_month = Integer.parseInt(st2.nextToken())-1;
		e_date = Integer.parseInt(st2.nextToken());
		Log.d("saveeCalendar", "e_year:"+e_year+"e_month:"+e_month+"e_date:"+e_date);
		eCalendar.set(e_year, e_month, e_date, hour, minute);
		
		
		
		//

		try {
			task2 = new phpUp();
			task2.execute("http://ljs93kr.cafe24.com/wlbinput.php?id="
					+ URLEncoder.encode(wlb.getId(),"utf-8") + "&wlbid=" + makeWLBID() + "&shape="
					+ wlb.getShape() + "&title=" + URLEncoder.encode(wlb.getTitle(),"utf-8") + "&content="
					+ URLEncoder.encode(wlb.getContent(),"utf-8") + "&popuptime=" +URLEncoder.encode(wlb.getPopuptime(),"utf-8")
					+ "&startdate=" + URLEncoder.encode(wlb.getStartdate(),"utf-8") + "&finishdate="
					+ URLEncoder.encode(wlb.getFinishdate(),"utf-8") + "&dayinterval=" + wlb.getDayinterval()
					+ "&secret=" + wlb.getSecret());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startAlarm(rqCode);
		handler.close();

		finish();

	}
	
	public void duringClick(View v){
		switch(v.getId()){
		case R.id.during1:
			duration = 1;
			Log.d("duringClick", String.valueOf(duration));
			break;
		case R.id.during2:
			duration = 2;
			Log.d("duringClick", String.valueOf(duration));
			break;
		case R.id.during3:
			duration = 4;
			Log.d("duringClick", String.valueOf(duration));
			break;
		case R.id.during4:
			duration = 26;
			Log.d("duringClick", String.valueOf(duration));
			break;
		case R.id.during5:
			duration = 52;
			Log.d("duringClick", String.valueOf(duration));
			break;
			
		}
	}

	private void startAlarm(int rqCode) {
		// mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
		// pendingIntent());

		mManager.set(AlarmManager.RTC_WAKEUP, eCalendar.getTimeInMillis(),
				pendingIntent(rqCode));
		Log.d("rqCode", String.valueOf(rqCode - 1));
		Toast.makeText(getApplicationContext(),
				eCalendar.getTime().toString() + "알람을 설정했습니다",
				Toast.LENGTH_SHORT).show();
		Log.d("makestartalarm", eCalendar.getTime().toString());

	}

	private PendingIntent pendingIntent(int rqCode) {
		Intent mIntent = new Intent(getApplicationContext(),
				MyAlarmService.class);

		String shape = editShape.getText().toString();
		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		mIntent.putExtra("shape", Integer.parseInt(shape));
		mIntent.putExtra("title", title);
		mIntent.putExtra("content", content);
		mIntent.putExtra("rqCode", rqCode);
		mIntent.putExtra("seq", 1);
		
		
		PendingIntent pi = PendingIntent.getService(getApplicationContext(),
				rqCode, mIntent, 0);

		return pi;
	}
	
	// dd만큼의 날짜를 더해서 리턴한다.
	public String addDate(String da, int dd) throws java.text.ParseException {

	      SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
	      Date date = format.parse(da);

	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(date);

	      calendar.add(Calendar.DATE, dd);

	      return format.format(calendar.getTime());
	   }
	
	// 날짜를 입력하면 요일을 리턴한다.
	public int calDay(String da)//  0:토 1:일 2:월 3:화 4:수 5:목 6:금 
	   {
	      int zellerMonth;
	      int zellerYear;

	      StringTokenizer st= new StringTokenizer(da,".");
	      int year = Integer.parseInt(st.nextToken());
	      int month = Integer.parseInt(st.nextToken());
	      int day = Integer.parseInt(st.nextToken());

	      if (month < 3) { // 월값이 3보다 작으면

	         zellerMonth = month + 12; // 월 + 12
	         zellerYear = year - 1; // 연 - 1
	      }

	      else {
	         zellerMonth = month;
	         zellerYear = year;
	      }

	      int computation = day + (26 * (zellerMonth + 1)) / 10 + zellerYear
	            + zellerYear / 4 + 6 * (zellerYear / 100) + zellerYear / 400;
	      int dayOfWeek = computation % 7;
	      
	      return dayOfWeek;
	   }
	
	public void fillselectedDay(int i) {
		if (i - Math.pow(2, 6) >= 0) {
			selectedDay.add(2);
			i -= Math.pow(2, 6);
		}
		if (i - Math.pow(2, 5) >= 0) {
			selectedDay.add(3);
			i -= Math.pow(2, 5);
		}
		if (i - Math.pow(2, 4) >= 0) {
			selectedDay.add(4);
			i -= Math.pow(2, 4);
		}
		if (i - Math.pow(2, 3) >= 0) {
			selectedDay.add(5);
			i -= Math.pow(2, 3);
		}
		if (i - Math.pow(2, 2) >= 0) {
			selectedDay.add(6);
			i -= Math.pow(2, 2);
		}
		if (i - Math.pow(2, 1) >= 0) {
			selectedDay.add(0);
			i -= Math.pow(2, 1);
		}
		if (i - Math.pow(2, 0) >= 0) {
			selectedDay.add(1);
			i -= Math.pow(2, 0);
		}
	}

	public void fillselectedDate() throws ParseException {

		for (int i = 0; i < duration * 7; i++) {
			for (int j = 0; j < selectedDay.size(); j++)
				if (calDay(addDate(startdate, i)) == selectedDay.get(j))
					selectedDate.add(addDate(startdate, i));
		}
	}

	private String numberTwo(int s) {
		String st = String.valueOf(s);
		if (s < 10) {
			st = "0" + st;
		}
		return st;
	}

	private int makeWLBID() {
		boolean[] makewlb = new boolean[5];
		for (int i = 0; i < makewlb.length; i++) {
			makewlb[i] = true;
		}
		for (int i = 0; i < MySkyActivity.wlbs.size(); i++) {
			if (MySkyActivity.wlbs.get(i).getWlbid() == 1) {
				makewlb[0] = false;
			}
			if (MySkyActivity.wlbs.get(i).getWlbid() == 2) {
				makewlb[1] = false;
			}
			if (MySkyActivity.wlbs.get(i).getWlbid() == 3) {
				makewlb[2] = false;
			}
			if (MySkyActivity.wlbs.get(i).getWlbid() == 4) {
				makewlb[3] = false;
			}
			if (MySkyActivity.wlbs.get(i).getWlbid() == 5) {
				makewlb[4] = false;
			}
		}
		for (int i = 0; i < 5; i++) {
			if (makewlb[i] == true) {
				return i + 1;
			}
		}
		return -1;
	}

	private int dayCal() {
		int total = 0;
		if (daySelect[0] == true) {
			total += Math.pow(2, 6);
		}
		if (daySelect[1] == true) {
			total += Math.pow(2, 5);
		}
		if (daySelect[2] == true) {
			total += Math.pow(2, 4);
		}
		if (daySelect[3] == true) {
			total += Math.pow(2, 3);
		}
		if (daySelect[4] == true) {
			total += Math.pow(2, 2);
		}
		if (daySelect[5] == true) {
			total += Math.pow(2, 1);
		}
		if (daySelect[6] == true) {
			total += Math.pow(2, 0);
		}
		return total;

	}

	// 쓰기위한 파싱
	private class phpUp extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				// 연결 url 설정
				URL url = new URL(urls[0]);
				// 커넥션 객체 생성
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 연결되었으면.
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// 연결되었음 코드가 리턴되면.
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"UTF-8"));
						for (;;) {
							// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
							String line = br.readLine();
							Log.d("line", line);
							if (line == null)
								break;
							// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
							jsonHtml.append(line + "\n");
						}
						br.close();
					}

					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return jsonHtml.toString();

		}

		protected void onPostExecute(String str) {
		}
	}

}
