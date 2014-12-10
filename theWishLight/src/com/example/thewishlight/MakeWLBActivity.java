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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

	private GregorianCalendar mCalendar; // ���۳�¥
	private GregorianCalendar eCalendar; // ������¥
	private int s_year, s_month, s_date, hour, minute; // ���� ��¥ �� �˾� �ð�
	private int e_year, e_month, e_date; // ���� ��¥
	Button day1, day2, day3, day4, day5, day6, day7;
	boolean[] daySelect;
	

	int isSecret = 0;

	phpUp task2;

	public AlarmManager mManager;

	private int duration;
	List<Integer> selectedDay;
	List<String> selectedDate;

	// ����ִ� ǳ��� (������ ���� ������ �߰��ȴ�)
	private int lockShapeCount = 3;

	// ������ true, ���������� false
	boolean[] shapePermission = new boolean[lockShapeCount];

	Button shape6, shape7, shape8;
	
	boolean[] duringSelect = new boolean[5]; // �Ⱓ���� 
	Button during1,during2,during3,during4,during5; // �Ⱓ ������ư

	String startdate;

	private int shape = 0; // ǳ������� ����
	ImageView selectedShape;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_wlb);

		// �˶��Ŵ����� ŉ��
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		// ���Ϲ�ư ��ü�ޱ�
		day1 = (Button) findViewById(R.id.day1);
		day2 = (Button) findViewById(R.id.day2);
		day3 = (Button) findViewById(R.id.day3);
		day4 = (Button) findViewById(R.id.day4);
		day5 = (Button) findViewById(R.id.day5);
		day6 = (Button) findViewById(R.id.day6);
		day7 = (Button) findViewById(R.id.day7);

		// �Ⱓ ��ư ��ü �ޱ�
		during1 = (Button)findViewById(R.id.during1);
		during2 = (Button)findViewById(R.id.during2);
		during3 = (Button)findViewById(R.id.during3);
		during4 = (Button)findViewById(R.id.during4);
		during5 = (Button)findViewById(R.id.during5);
		
		// ������ ǳ���� �����ִ� �̹�����
		selectedShape = (ImageView) findViewById(R.id.selectedShape);

		// ǳ��� �۹̼� �ޱ�
		int shapepermission = MySkyActivity.myInfo.getShapepermission();

		Log.d("shapepermission", String.valueOf(shapepermission));

		// shapePermission ����Ʈ�� 6�� ��������� 6�����ְ�, 7���������� 7�����ְ�, 8�־��������� 8�������ִ�.
		decodeShapePermission(shapepermission);

		shape6 = (Button) findViewById(R.id.shape6);
		shape7 = (Button) findViewById(R.id.shape7);
		shape8 = (Button) findViewById(R.id.shape8);
		
		if (shapePermission[0] == true) {
			shape6.setBackgroundResource(MySkyActivity.determineShape(6));
		}
		if (shapePermission[1] == true) {
			shape7.setBackgroundResource(MySkyActivity.determineShape(7));
		}
		if (shapePermission[2] == true) {
			 shape8.setBackgroundResource(MySkyActivity.determineShape(8));
		}
		// ���ϼ����� ���� �ʱ�ȭ
		daySelect = new boolean[7];
		for (boolean s : daySelect) {
			s = false;
		}

		// ����ð� ŉ��
		mCalendar = new GregorianCalendar();
		eCalendar = new GregorianCalendar();
		Log.d("homework", mCalendar.getTime().toString());

		// ���� ���� ��¥ �ޱ�
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

	// �˾��ð� �����ϱ�
	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int Minute) {
			// TODO Auto-generated method stub
			Log.d("timePickerBefore", mCalendar.getTime().toString());
			hour = hourOfDay;
			minute = Minute;

			popupTimePicker.setText(numberTwo(hour)+ " : " +numberTwo(minute));
			popupTimePicker.setBackgroundColor(Color.TRANSPARENT);
			eCalendar.set(e_year, e_month, e_date, hour, minute);
			Log.d("timePickerAfter", "�˾��ð�����:" + hour + minute);
			Log.d("timePicker e", "�˾��ð�����:" + eCalendar.getTime().toString());
		}
	};

	
	// ���ϼ��� ��ư
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

	// ���� ����� ���� ��ư
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

	// ǳ�� ��� ���� ��ư
	public void shapeClick(View v) {
		switch (v.getId()) {
		case R.id.shape1:

			int i1 = 1;
			LayoutInflater inflater1 = (LayoutInflater) getApplicationContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
			View layout1 = inflater1.inflate(R.layout.wlbinfo_dialog,
					(ViewGroup) findViewById(R.id.wlbinfo));
			TextView wlbView = (TextView) layout1.findViewById(R.id.infoView);
			AlertDialog.Builder aDialog1 = new AlertDialog.Builder(
					MakeWLBActivity.this);

			
			wlbView.setText("1�� ǳ���̴� ");
			aDialog1.setTitle("1�� ǳ��"); // Ÿ��Ʋ�� ����
			aDialog1.setView(layout1); // dialog.xml ������ ��� ����

			// �׳� �ݱ��ư�� ���� �κ�
			aDialog1.setNegativeButton("�ݱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// �׳� ����ϱ��ư�� ���� �κ�
			aDialog1.setPositiveButton("����ϱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							shape = 1;
							selectedShape.setBackgroundResource(MySkyActivity
									.determineShape(shape));
						}
					});
			aDialog1.setIcon(MySkyActivity.determineShape(i1));
			// �˾�â ����
			AlertDialog ad1 = aDialog1.create();
			ad1.show();// ������!
			break;
		case R.id.shape2:
			int i2 = 2;
			LayoutInflater inflater2 = (LayoutInflater) getApplicationContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
			View layout2 = inflater2.inflate(R.layout.wlbinfo_dialog,
					(ViewGroup) findViewById(R.id.wlbinfo));
			TextView wlbView2 = (TextView) layout2.findViewById(R.id.infoView);
			AlertDialog.Builder aDialog2 = new AlertDialog.Builder(
					MakeWLBActivity.this);

			
			wlbView2.setText("2�� ǳ���̴� ");
			aDialog2.setTitle("2�� ǳ��"); // Ÿ��Ʋ�� ����
			aDialog2.setView(layout2); // dialog.xml ������ ��� ����

			// �׳� �ݱ��ư�� ���� �κ�
			aDialog2.setNegativeButton("�ݱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// �׳� ����ϱ��ư�� ���� �κ�
			aDialog2.setPositiveButton("����ϱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							shape = 2;
							selectedShape.setBackgroundResource(MySkyActivity
									.determineShape(shape));
						}
					});
			aDialog2.setIcon(MySkyActivity.determineShape(i2));
			// �˾�â ����
			AlertDialog ad2 = aDialog2.create();
			ad2.show();// ������!

			break;
		case R.id.shape3:
			
			int i3 = 3;
			LayoutInflater inflater3 = (LayoutInflater) getApplicationContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
			View layout3 = inflater3.inflate(R.layout.wlbinfo_dialog,
					(ViewGroup) findViewById(R.id.wlbinfo));
			TextView wlbView3 = (TextView) layout3.findViewById(R.id.infoView);
			AlertDialog.Builder aDialog3 = new AlertDialog.Builder(
					MakeWLBActivity.this);

			wlbView3.setText("3�� ǳ���̴� ");
			aDialog3.setTitle("3�� ǳ��"); // Ÿ��Ʋ�� ����
			aDialog3.setView(layout3); // dialog.xml ������ ��� ����

			// �׳� �ݱ��ư�� ���� �κ�
			aDialog3.setNegativeButton("�ݱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// �׳� ����ϱ��ư�� ���� �κ�
			aDialog3.setPositiveButton("����ϱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							shape = 3;
							selectedShape.setBackgroundResource(MySkyActivity
									.determineShape(shape));
						}
					});
			aDialog3.setIcon(MySkyActivity.determineShape(3));
			// �˾�â ����
			AlertDialog ad3 = aDialog3.create();
			ad3.show();// ������!
			break;
		case R.id.shape4:

			int i4 = 4;
			LayoutInflater inflater4 = (LayoutInflater) getApplicationContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
			View layout4 = inflater4.inflate(R.layout.wlbinfo_dialog,
					(ViewGroup) findViewById(R.id.wlbinfo));
			TextView wlbView4 = (TextView) layout4.findViewById(R.id.infoView);
			AlertDialog.Builder aDialog4 = new AlertDialog.Builder(
					MakeWLBActivity.this);

			
			wlbView4.setText("4�� ǳ���̴� ");
			aDialog4.setTitle("4�� ǳ��"); // Ÿ��Ʋ�� ����
			aDialog4.setView(layout4); // dialog.xml ������ ��� ����

			// �׳� �ݱ��ư�� ���� �κ�
			aDialog4.setNegativeButton("�ݱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// �׳� ����ϱ��ư�� ���� �κ�
			aDialog4.setPositiveButton("����ϱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							shape = 4;
							selectedShape.setBackgroundResource(MySkyActivity
									.determineShape(shape));
						}
					});
			aDialog4.setIcon(MySkyActivity.determineShape(i4));
			// �˾�â ����
			AlertDialog ad4 = aDialog4.create();
			ad4.show();// ������!
			break;
		case R.id.shape5:

			int i5 = 5;
			LayoutInflater inflater5 = (LayoutInflater) getApplicationContext()
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
			View layout5 = inflater5.inflate(R.layout.wlbinfo_dialog,
					(ViewGroup) findViewById(R.id.wlbinfo));
			TextView wlbView5 = (TextView) layout5.findViewById(R.id.infoView);
			AlertDialog.Builder aDialog5 = new AlertDialog.Builder(
					MakeWLBActivity.this);

			
			wlbView5.setText("5�� ǳ���̴� ");
			aDialog5.setTitle("5�� ǳ��"); // Ÿ��Ʋ�� ����
			aDialog5.setView(layout5); // dialog.xml ������ ��� ����

			// �׳� �ݱ��ư�� ���� �κ�
			aDialog5.setNegativeButton("�ݱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// �׳� ����ϱ��ư�� ���� �κ�
			aDialog5.setPositiveButton("����ϱ�",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							shape = 5;
							selectedShape.setBackgroundResource(MySkyActivity
									.determineShape(shape));
						}
					});
			aDialog5.setIcon(MySkyActivity.determineShape(i5));
			// �˾�â ����
			AlertDialog ad5 = aDialog5.create();
			ad5.show();// ������!
			break;
		case R.id.shape6:
			if (shapePermission[0] == true) {

				int i6 = 6;
				LayoutInflater inflater6 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout6 = inflater6.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView6 = (TextView) layout6
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog6 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView6.setText("6�� ǳ���̴� ");
				aDialog6.setTitle("6�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog6.setView(layout6); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog6.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� ����ϱ��ư�� ���� �κ�
				aDialog6.setPositiveButton("����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								shape = 6;
								selectedShape
										.setBackgroundResource(MySkyActivity
												.determineShape(shape));
							}
						});
				aDialog6.setIcon(MySkyActivity.determineShape(i6));
				// �˾�â ����
				AlertDialog ad6 = aDialog6.create();
				ad6.show();// ������!
			} else {
				int i6 = 6;
				LayoutInflater inflater6 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout6 = inflater6.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView6 = (TextView) layout6
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog6 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView6.setText("6�� ǳ���̴� ");
				aDialog6.setTitle("6�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog6.setView(layout6); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog6.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� ����ϱ��ư�� ���� �κ�
				aDialog6.setPositiveButton("�����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								int star = MySkyActivity.myInfo.getStar();
								int perm = MySkyActivity.myInfo
										.getShapepermission();
								if (star >= 10) {
									MySkyActivity.myInfo.setStar(star - 10);
									MySkyActivity.myInfo
											.setShapepermission(perm + 4);

									try {
										phpConnect task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/starchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&star="
												+ MySkyActivity.myInfo
														.getStar());
										task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/shapepermissionchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&shapepermission="
												+ MySkyActivity.myInfo
														.getShapepermission());

										shapePermission[0] = true;
										shape = 6;
										shape6.setBackgroundResource(MySkyActivity
												.determineShape(shape));

									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									Toast.makeText(getApplicationContext(),
											"���� ���ڶ�� �Ф�", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
				aDialog6.setIcon(MySkyActivity.determineShape(i6));
				// �˾�â ����
				AlertDialog ad6 = aDialog6.create();
				ad6.show();// ������!
			}
			break;
		case R.id.shape7:
			if (shapePermission[1] == true) {

				int i7 = 7;
				LayoutInflater inflater7 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout7 = inflater7.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView7 = (TextView) layout7
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog7 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView7.setText("7�� ǳ���̴� ");
				aDialog7.setTitle("7�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog7.setView(layout7); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog7.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� ����ϱ��ư�� ���� �κ�
				aDialog7.setPositiveButton("����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								shape = 7;
								selectedShape
										.setBackgroundResource(MySkyActivity
												.determineShape(shape));
							}
						});
				aDialog7.setIcon(MySkyActivity.determineShape(i7));
				// �˾�â ����
				AlertDialog ad7 = aDialog7.create();
				ad7.show();// ������!
			} else {
				int i7 = 7;
				LayoutInflater inflater7 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout7 = inflater7.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView7 = (TextView) layout7
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog7 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView7.setText("7�� ǳ���̴� ");
				aDialog7.setTitle("7�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog7.setView(layout7); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog7.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� �����ϱ��ư�� ���� �κ�
				aDialog7.setPositiveButton("�����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								int star = MySkyActivity.myInfo.getStar();
								int perm = MySkyActivity.myInfo
										.getShapepermission();
								if (star >= 20) {
									MySkyActivity.myInfo.setStar(star - 20);
									MySkyActivity.myInfo
											.setShapepermission(perm + 2);

									try {
										phpConnect task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/starchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&star="
												+ MySkyActivity.myInfo
														.getStar());
										task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/shapepermissionchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&shapepermission="
												+ MySkyActivity.myInfo
														.getShapepermission());

										shapePermission[1] = true;
										shape = 7;
										shape7.setBackgroundResource(MySkyActivity
												.determineShape(shape));
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									Toast.makeText(getApplicationContext(),
											"���� ���ڶ�� �Ф�", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
				aDialog7.setIcon(MySkyActivity.determineShape(i7));
				// �˾�â ����
				AlertDialog ad7 = aDialog7.create();
				ad7.show();// ������!
			}
			break;
		case R.id.shape8:
			if (shapePermission[2] == true) {

				int i8 = 8;
				LayoutInflater inflater8 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout8 = inflater8.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView8 = (TextView) layout8
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog8 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView8.setText("8�� ǳ���̴� ");
				aDialog8.setTitle("8�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog8.setView(layout8); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog8.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� ����ϱ��ư�� ���� �κ�
				aDialog8.setPositiveButton("����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								shape = 8;
								selectedShape
										.setBackgroundResource(MySkyActivity
												.determineShape(shape));
							}
						});
				aDialog8.setIcon(MySkyActivity.determineShape(i8));
				// �˾�â ����
				AlertDialog ad8 = aDialog8.create();
				ad8.show();// ������!
			} else {
				int i8 = 8;
				LayoutInflater inflater8 = (LayoutInflater) getApplicationContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
				View layout8 = inflater8.inflate(R.layout.wlbinfo_dialog,
						(ViewGroup) findViewById(R.id.wlbinfo));
				TextView wlbView8 = (TextView) layout8
						.findViewById(R.id.infoView);
				AlertDialog.Builder aDialog8 = new AlertDialog.Builder(
						MakeWLBActivity.this);

				
				wlbView8.setText("8�� ǳ���̴� ");
				aDialog8.setTitle("8�� ǳ��"); // Ÿ��Ʋ�� ����
				aDialog8.setView(layout8); // dialog.xml ������ ��� ����

				// �׳� �ݱ��ư�� ���� �κ�
				aDialog8.setNegativeButton("�ݱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// �׳� �����ϱ��ư�� ���� �κ�
				aDialog8.setPositiveButton("�����ϱ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								int star = MySkyActivity.myInfo.getStar();
								int perm = MySkyActivity.myInfo
										.getShapepermission();
								if (star >= 20) {
									MySkyActivity.myInfo.setStar(star - 20);
									MySkyActivity.myInfo
											.setShapepermission(perm + 2);

									try {
										phpConnect task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/starchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&star="
												+ MySkyActivity.myInfo
														.getStar());
										task = new phpConnect();
										task.execute("http://ljs93kr.cafe24.com/shapepermissionchange.php?id="
												+ URLEncoder.encode(
														MySkyActivity.myInfo
																.getId(),
														"utf-8")
												+ "&shapepermission="
												+ MySkyActivity.myInfo
														.getShapepermission());

										shapePermission[2] = true;
										shape = 8;
										shape8.setBackgroundResource(MySkyActivity
												.determineShape(shape));
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									Toast.makeText(getApplicationContext(),
											"���� ���ڶ�� �Ф�", Toast.LENGTH_LONG)
											.show();
								}
							}
						});
				aDialog8.setIcon(MySkyActivity.determineShape(i8));
				// �˾�â ����
				AlertDialog ad8 = aDialog8.create();
				ad8.show();// ������!
			}
			break;

		}
	}

	// �ҿ��� ����� ��ư�� ��������
	public void saveClick(View v) {

		if (shape == 0) {
			Toast.makeText(getApplicationContext(), "ǳ���缱���� �ʿ��մϴ�",
					Toast.LENGTH_LONG).show();
			return;
		}

		editTitle = (EditText) findViewById(R.id.inputTitle);
		editContent = (EditText) findViewById(R.id.inputContent);

		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		// handler.insert(title, content, Integer.parseInt(shape));
		// handler.close();
		if (makeWLBID() == -1) {
			Toast.makeText(getApplicationContext(), "�ҿ����� 5�� �̻� ���� �� �����ϴ�",
					Toast.LENGTH_LONG).show();
			return;
		}

		int rqCode = makeWLBID();
		Log.d("dayCal", String.valueOf(dayCal()));
		WLB wlb = new WLB(MySkyActivity.myInfo.getId(), makeWLBID(), shape,
				title, content, hour + ":" + minute, s_year + "."
						+ (s_month + 1) + "." + s_date, e_year + "."
						+ (e_month + 1) + "." + e_date, dayCal(), isSecret);

		//

		s_month = s_month + 1;
		startdate = s_year + "." + s_month + "." + s_date;
		Log.d("startdate", startdate);
		selectedDay = new ArrayList<Integer>();
		Log.d("s", String.valueOf(1));
		selectedDate = new ArrayList<String>();
		Log.d("s", String.valueOf(2));

		fillselectedDay(dayCal());
		Log.d("s", String.valueOf(3));
		try {
			fillselectedDate();
			Log.d("s", String.valueOf(4));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.d("s", String.valueOf(5));
			e.printStackTrace();
		}
		Log.d("s", String.valueOf(6));

		handler = new MyDBHandler(getApplicationContext(),
				("wlb_" + String.valueOf(rqCode)));
		handler.open();
		StringTokenizer st;
		Log.d("s", String.valueOf(7));
		int seq = 1;
		for (int i = 0; i < selectedDate.size(); i++) {
			Log.d("s", String.valueOf(8));
			Log.d("selectedDate", selectedDate.get(i));
			st = new StringTokenizer(selectedDate.get(i), ".");

			Log.d("s", String.valueOf(9));
			handler.insert(seq, Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), hour, minute);
			Log.d("s", String.valueOf(10));
			// Log.d("selectedDate",
			// "seq:"+seq+"e_year:"+st.nextToken()+"e_month:"+st.nextToken()+"e_date:"+st.nextToken());
			++seq;
		}
		Log.d("s", String.valueOf(11));

		Cursor c = handler.select();
		startManagingCursor(c);
		String data = "";
		Log.d("s", String.valueOf(12));
		Log.d("db count", String.valueOf(c.getCount()));
		while (c.moveToNext()) {
			Log.d("s", String.valueOf(13));
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

		String data2 = "";
		data2 = handler.selectData(1);
		Log.d("data2", data2);
		StringTokenizer st2 = new StringTokenizer(data2, ".");
		e_year = Integer.parseInt(st2.nextToken());
		e_month = Integer.parseInt(st2.nextToken()) - 1;
		e_date = Integer.parseInt(st2.nextToken());
		Log.d("saveeCalendar", "e_year:" + e_year + "e_month:" + e_month
				+ "e_date:" + e_date);
		eCalendar.set(e_year, e_month, e_date, hour, minute);

		//

		try {
			task2 = new phpUp();
			task2.execute("http://ljs93kr.cafe24.com/wlbinput.php?id="
					+ URLEncoder.encode(wlb.getId(), "utf-8") + "&wlbid="
					+ makeWLBID() + "&shape=" + wlb.getShape() + "&title="
					+ URLEncoder.encode(wlb.getTitle(), "utf-8") + "&content="
					+ URLEncoder.encode(wlb.getContent(), "utf-8")
					+ "&popuptime="
					+ URLEncoder.encode(wlb.getPopuptime(), "utf-8")
					+ "&startdate="
					+ URLEncoder.encode(wlb.getStartdate(), "utf-8")
					+ "&finishdate="
					+ URLEncoder.encode(wlb.getFinishdate(), "utf-8")
					+ "&dayinterval=" + wlb.getDayinterval() + "&secret="
					+ wlb.getSecret());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startAlarm(rqCode);
		handler.close();

		finish();

	}

	public void duringClick(View v) {
		switch (v.getId()) {
		case R.id.during1:
			duration = 1;
			Log.d("duringClick", String.valueOf(duration));
			during1.setBackgroundResource(R.drawable.day12);
			during2.setBackgroundResource(R.drawable.day2);
			during3.setBackgroundResource(R.drawable.day3);
			during4.setBackgroundResource(R.drawable.day4);
			during5.setBackgroundResource(R.drawable.day5);
			break;
		case R.id.during2:
			duration = 2;
			Log.d("duringClick", String.valueOf(duration));
			during1.setBackgroundResource(R.drawable.day1);
			during2.setBackgroundResource(R.drawable.day22);
			during3.setBackgroundResource(R.drawable.day3);
			during4.setBackgroundResource(R.drawable.day4);
			during5.setBackgroundResource(R.drawable.day5);
			break;
		case R.id.during3:
			duration = 4;
			Log.d("duringClick", String.valueOf(duration));
			during1.setBackgroundResource(R.drawable.day1);
			during2.setBackgroundResource(R.drawable.day2);
			during3.setBackgroundResource(R.drawable.day32);
			during4.setBackgroundResource(R.drawable.day4);
			during5.setBackgroundResource(R.drawable.day5);
			break;
		case R.id.during4:
			duration = 26;
			Log.d("duringClick", String.valueOf(duration));
			during1.setBackgroundResource(R.drawable.day1);
			during2.setBackgroundResource(R.drawable.day2);
			during3.setBackgroundResource(R.drawable.day3);
			during4.setBackgroundResource(R.drawable.day42);
			during5.setBackgroundResource(R.drawable.day5);
			break;
		case R.id.during5:
			duration = 52;
			Log.d("duringClick", String.valueOf(duration));
			during1.setBackgroundResource(R.drawable.day1);
			during2.setBackgroundResource(R.drawable.day2);
			during3.setBackgroundResource(R.drawable.day3);
			during4.setBackgroundResource(R.drawable.day4);
			during5.setBackgroundResource(R.drawable.day52);
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
				eCalendar.getTime().toString() + "�˶��� �����߽��ϴ�",
				Toast.LENGTH_SHORT).show();
		Log.d("makestartalarm", eCalendar.getTime().toString());

	}

	private PendingIntent pendingIntent(int rqCode) {
		Intent mIntent = new Intent(getApplicationContext(),
				MyAlarmService.class);

		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();

		mIntent.putExtra("shape", shape);
		mIntent.putExtra("title", title);
		mIntent.putExtra("content", content);
		mIntent.putExtra("rqCode", rqCode);
		mIntent.putExtra("seq", 1);
		mIntent.putExtra("success", 1);
		mIntent.putExtra("duration", duration);

		PendingIntent pi = PendingIntent.getService(getApplicationContext(),
				rqCode, mIntent, 0);

		return pi;
	}

	// dd��ŭ�� ��¥�� ���ؼ� �����Ѵ�.
	public String addDate(String da, int dd) throws java.text.ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		Date date = format.parse(da);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, dd);

		return format.format(calendar.getTime());
	}

	// ��¥�� �Է��ϸ� ������ �����Ѵ�.
	public int calDay(String da)// 0:�� 1:�� 2:�� 3:ȭ 4:�� 5:�� 6:��
	{
		int zellerMonth;
		int zellerYear;

		StringTokenizer st = new StringTokenizer(da, ".");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int day = Integer.parseInt(st.nextToken());

		if (month < 3) { // ������ 3���� ������

			zellerMonth = month + 12; // �� + 12
			zellerYear = year - 1; // �� - 1
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

	private void decodeShapePermission(int i) {

		// shape 6 �����ֱ�
		if ((i - Math.pow(2, 2)) >= 0) {
			shapePermission[0] = true;
			i -= Math.pow(2, 2);
		}
		// shape 7 �����ֱ�
		if ((i - Math.pow(2, 1)) >= 0) {
			shapePermission[1] = true;
			i -= Math.pow(2, 1);
		}
		// shape 8 �����ֱ�
		if ((i - Math.pow(2, 0)) >= 0) {
			shapePermission[2] = true;
			i -= Math.pow(2, 0);
		}
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

	// �������� �Ľ�
	private class phpUp extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				// ���� url ����
				URL url = new URL(urls[0]);
				// Ŀ�ؼ� ��ü ����
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// ����Ǿ�����.
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// ����Ǿ��� �ڵ尡 ���ϵǸ�.
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"UTF-8"));
						for (;;) {
							// ���� �������� �ؽ�Ʈ�� ���δ����� �о� ����.
							String line = br.readLine();
							Log.d("line", line);
							if (line == null)
								break;
							// ����� �ؽ�Ʈ ������ jsonHtml�� �ٿ�����
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
