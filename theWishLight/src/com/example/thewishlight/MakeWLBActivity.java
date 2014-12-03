package com.example.thewishlight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

	private GregorianCalendar mCalendar;
	private int s_year, s_month, s_day, hour, minute; // 시작 날짜 와 팝업 시간
	private int e_year, e_month, e_day; // 마감 날짜
	Button day1, day2, day3, day4, day5, day6, day7;
	boolean[] daySelect;

	int isSecret = 0;

	phpUp task2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_wlb);

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
		Log.d("homework", mCalendar.getTime().toString());

		// 현재 시작 날짜 받기
		s_year = mCalendar.get(Calendar.YEAR);
		s_month = mCalendar.get(Calendar.MONTH);
		s_day = mCalendar.get(Calendar.DAY_OF_MONTH);
		
		e_year = s_year;
		e_month = s_month;
		e_day = s_day;

		hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		minute = mCalendar.get(Calendar.MINUTE);

		finishDatePicker = (Button) findViewById(R.id.finishDatePicker);
		finishDatePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(MakeWLBActivity.this, dateSetListener,
						s_year, s_month, s_day).show();
			}
		});

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

	public void dayClick(View v) {

		switch (v.getId()) {
		case R.id.day1:
			if (daySelect[0] == false) {
				day1.setBackgroundColor(Color.RED);
				daySelect[0] = true;
			} else if (daySelect[0] == true) {
				day1.setBackgroundColor(Color.GRAY);
				daySelect[0] = false;
			}
			break;
		case R.id.day2:
			if (daySelect[1] == false) {
				day2.setBackgroundColor(Color.RED);
				daySelect[1] = true;
			} else if (daySelect[1] == true) {
				day2.setBackgroundColor(Color.GRAY);
				daySelect[1] = false;
			}
			break;
		case R.id.day3:
			if (daySelect[2] == false) {
				day3.setBackgroundColor(Color.RED);
				daySelect[2] = true;
			} else if (daySelect[2] == true) {
				day3.setBackgroundColor(Color.GRAY);
				daySelect[2] = false;
			}
			break;
		case R.id.day4:
			if (daySelect[3] == false) {
				day4.setBackgroundColor(Color.RED);
				daySelect[3] = true;
			} else if (daySelect[3] == true) {
				day4.setBackgroundColor(Color.GRAY);
				daySelect[3] = false;
			}
			break;
		case R.id.day5:
			if (daySelect[4] == false) {
				day5.setBackgroundColor(Color.RED);
				daySelect[4] = true;
			} else if (daySelect[4] == true) {
				day5.setBackgroundColor(Color.GRAY);
				daySelect[4] = false;
			}
			break;
		case R.id.day6:
			if (daySelect[5] == false) {
				day6.setBackgroundColor(Color.RED);
				daySelect[5] = true;
			} else if (daySelect[5] == true) {
				day6.setBackgroundColor(Color.GRAY);
				daySelect[5] = false;
			}
			break;
		case R.id.day7:
			if (daySelect[6] == false) {
				day7.setBackgroundColor(Color.RED);
				daySelect[6] = true;
			} else if (daySelect[6] == true) {
				day7.setBackgroundColor(Color.GRAY);
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

		
		//handler = MyDBHandler.open(getApplicationContext(), "wlb");
		editShape = (EditText) findViewById(R.id.inputShape);
		editTitle = (EditText) findViewById(R.id.inputTitle);
		editContent = (EditText) findViewById(R.id.inputContent);

		String shape = editShape.getText().toString();
		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		//handler.insert(title, content, Integer.parseInt(shape));
		//handler.close();
		
		if(makeWLBID()== -1){
			Toast.makeText(getApplicationContext(), "소원등은 5개 이상 만들 수 없습니다",Toast.LENGTH_LONG).show();
			return;
		}
		Log.d("dayCal", String.valueOf(dayCal()));
		WLB wlb = new WLB(MySkyActivity.myID,makeWLBID(),Integer.valueOf(shape), title, content, hour + ":"
				+ minute, s_year + "." + (s_month + 1) + "." + s_day, e_year
				+ "." + (e_month + 1) + "." + e_day, 
				dayCal(), isSecret);

		task2 = new phpUp();

		task2.execute("http://ljs93kr.cafe24.com/wlbinput.php?id="
				+ wlb.getId()+"&wlbid="+makeWLBID() + "&shape=" + wlb.getShape() + "&title="
				+ wlb.getTitle() + "&content=" + wlb.getContent()
				+ "&popuptime=" + wlb.getPopuptime() + "&startdate="
				+ wlb.getStartdate() + "&finishdate=" + wlb.getFinishdate()
				+ "&dayinterval=" + wlb.getDayinterval() + "&secret="
				+ wlb.getSecret());

		finish();

	}

	private String numberTwo(int s) {
		String st = String.valueOf(s);
		if (s < 10) {
			st = "0" + st;
		}
		return st;
	}
	
	private int makeWLBID(){
		boolean[] makewlb = new boolean[5];
		for(int i=0;i<makewlb.length;i++){
			makewlb[i]= true;
		}
		for(int i=0;i<MySkyActivity.wlbs.size();i++){
			if(MySkyActivity.wlbs.get(i).getWlbid() == 1){
				makewlb[0]=false;
			}if(MySkyActivity.wlbs.get(i).getWlbid() == 2){
				makewlb[1]=false;
			}if(MySkyActivity.wlbs.get(i).getWlbid() == 3){
				makewlb[2]=false;
			}if(MySkyActivity.wlbs.get(i).getWlbid() == 4){
				makewlb[3]=false;
			}if(MySkyActivity.wlbs.get(i).getWlbid() == 5){
				makewlb[4]=false;
			}
		}
		for(int i=0;i<5;i++){
			if(makewlb[i]==true){
				return i+1;
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

	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			e_year = year;
			e_month = monthOfYear;
			e_day = dayOfMonth;
			// mCalendar.set(year, monthOfYear, dayOfMonth, hour, minute);
			Log.d("datePickerAfter", "시작날짜:" + s_year + s_month + s_day
					+ "마감날짜:" + e_year + e_month + e_day);

		}
	};

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int Minute) {
			// TODO Auto-generated method stub
			Log.d("timePickerBefore", mCalendar.getTime().toString());
			hour = hourOfDay;
			minute = Minute;

			Log.d("timePickerAfter", "팝업시간설정:" + hour + minute);
		}
	};

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
