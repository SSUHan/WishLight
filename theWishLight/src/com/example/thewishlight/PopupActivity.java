package com.example.thewishlight;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopupActivity extends Activity {

	EditText edit;
	Button answerBtn;
	TextView textView01;

	MyDBHandler handler;

	private GregorianCalendar eCalendar; // ������¥

	private int e_year, e_month, e_date; // ���� ��¥

	AlarmManager mManager;

	String content;
	String title;
	int shape;
	int seq;
	int success;
	int duration;

	// boolean[] trophyPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup_start);
		// ȭ���� ��������� �����ֱ�
		// Ű��� �����ϱ�
		// ȭ���ѱ�
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		/*
		 * Display display = ((WindowManager)
		 * getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		 * 
		 * int width = (int) (display.getWidth() * 0.7); //Display �������� 70%
		 * 
		 * int height = (int) (display.getHeight() * 0.9); //Display �������� 90%
		 * 
		 * getWindow().getAttributes().width = width;
		 * 
		 * getWindow().getAttributes().height = height;
		 */

		eCalendar = new GregorianCalendar();
		Intent mIntent = getIntent();
		final int rqCode = mIntent.getIntExtra("rqCode", 0);
		content = mIntent.getStringExtra("content");
		title = mIntent.getStringExtra("title");
		shape = mIntent.getIntExtra("shape", 0);
		seq = mIntent.getIntExtra("seq", 0) + 1;
		success = mIntent.getIntExtra("success", 0);
		duration = mIntent.getIntExtra("duration", 0);

		Log.d("popup onCreate", "rqCode:" + String.valueOf(rqCode));
		Log.d("popup onCreate", "seq:" + String.valueOf(seq));
		Log.d("popup onCreate", "success:" + String.valueOf(success));

		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
		View layout = inflater.inflate(R.layout.popup_dialog,
				(ViewGroup) findViewById(R.id.popupLayout));

		TextView titleView = (TextView) layout.findViewById(R.id.titleView01);
		TextView contentView = (TextView) layout
				.findViewById(R.id.contentView01);

		titleView.setText(title);
		contentView.setText(content);

		Log.d("popup onCreate", "shape:" + String.valueOf(shape));

		AlertDialog.Builder ab = new AlertDialog.Builder(PopupActivity.this);
		ab.setTitle("�ҿ��� ����"); // Ÿ��Ʋ�� ����
		ab.setView(layout); // dialog.xml ������ ��� ����
		ab.setIcon(MySkyActivity.determineShape(shape));

		ab.setPositiveButton("��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				stopAlarm(rqCode);

				handler = new MyDBHandler(getApplicationContext(), "wlb_"
						+ rqCode);
				handler.open();

				String data = handler.selectData(seq);

				//�ҿ��� �������϶� 
				if (data == null) {

					if (success == 1) {
						// try {
						// trophyPermission = new boolean[5];
						// decodeTrophy(MySkyA)
						// switch(duration){
						// case 1:
						// if(trophyPermission[0]==false){
						// MySkyActivity.myInfo.setTrophypermission(MySkyActivity.myInfo.getTrophypermission()+(int)Math.pow(2,
						// 4));
						// }
						// break;
						// case 2:
						// MySkyActivity.myInfo.setTrophypermission(MySkyActivity.myInfo.getTrophypermission()+(int)Math.pow(2,
						// 3));
						// break;
						// case 4:
						// MySkyActivity.myInfo.setTrophypermission(MySkyActivity.myInfo.getTrophypermission()+(int)Math.pow(2,
						// 2));
						// break;
						// case 26:
						// MySkyActivity.myInfo.setTrophypermission(MySkyActivity.myInfo.getTrophypermission()+(int)Math.pow(2,
						// 1));
						// break;
						// case 52:
						// MySkyActivity.myInfo.setTrophypermission(MySkyActivity.myInfo.getTrophypermission()+(int)Math.pow(2,
						// 0));
						// break;
						//
						//
						//
						// }
						// phpConnect task = new phpConnect();
						// task.execute("http://ljs93kr.cafe24.com/trophypermissionchange.php?id="
						// + URLEncoder.encode(
						// MySkyActivity.myInfo
						// .getId(),
						// "utf-8")
						// + "&trophypermission="
						// + MySkyActivity.myInfo
						// .getTrophypermission());
						// } catch (UnsupportedEncodingException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// }
						Toast.makeText(getApplicationContext(), "�ҿ��� �ٳ������ϴ�!",
								Toast.LENGTH_LONG).show();
						phpConnect delete = new phpConnect();
						try {
							delete.execute("http://ljs93kr.cafe24.com/wlbdelete.php?id="
									+ URLEncoder.encode(
											MySkyActivity.myInfo.getId(),
											"utf-8") + "&wlbid=" + rqCode);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Toast.makeText(getApplicationContext(), "�����Ϸ�",
								Toast.LENGTH_SHORT).show();

					} else {
						StringTokenizer st2 = new StringTokenizer(data, ".");
						e_year = Integer.parseInt(st2.nextToken());
						e_month = Integer.parseInt(st2.nextToken()) - 1;
						e_date = Integer.parseInt(st2.nextToken());
						int hour = Integer.parseInt(st2.nextToken());
						int minute = Integer.parseInt(st2.nextToken());

						eCalendar.set(e_year, e_month, e_date, hour, minute);
						Log.d("new Alarm", eCalendar.getTime().toString());
						startAlarm(rqCode);
					}
					handler.removeTable();
				}
				handler.close();
				try {
					MySkyActivity.myInfo.setStar(MySkyActivity.myInfo.getStar() + 1);
					phpConnect task = new phpConnect();
					task.execute("http://ljs93kr.cafe24.com/starchange.php?id="
							+ URLEncoder.encode(MySkyActivity.myInfo.getId(),
									"utf-8") + "&star="
							+ MySkyActivity.myInfo.getStar());
					Toast.makeText(
							getApplicationContext(),
							"��1�� ŉ���ϼ̽��ϴ�!" + "���� ����:"
									+ MySkyActivity.myInfo.getStar(),
							Toast.LENGTH_LONG).show();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PopupActivity.this.finish();
			}
		});

		ab.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				success = 0;
				stopAlarm(rqCode);

				handler = new MyDBHandler(getApplicationContext(), "wlb_"
						+ rqCode);
				handler.open();

				String data = handler.selectData(seq);
				if (data == null) {
					Toast.makeText(getApplicationContext(), "�ҿ��� �ٳ������ϴ�!",
							Toast.LENGTH_LONG).show();
					phpConnect delete = new phpConnect();
					try {
						delete.execute("http://ljs93kr.cafe24.com/wlbdelete.php?id="
								+ URLEncoder.encode(
										MySkyActivity.myInfo.getId(), "utf-8")
								+ "&wlbid=" + rqCode);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Toast.makeText(getApplicationContext(), "�����Ϸ�",
							Toast.LENGTH_SHORT).show();
				} else {
					StringTokenizer st2 = new StringTokenizer(data, ".");
					e_year = Integer.parseInt(st2.nextToken());
					e_month = Integer.parseInt(st2.nextToken()) - 1;
					e_date = Integer.parseInt(st2.nextToken());
					int hour = Integer.parseInt(st2.nextToken());
					int minute = Integer.parseInt(st2.nextToken());

					eCalendar.set(e_year, e_month, e_date, hour, minute);
					Log.d("new Alarm", eCalendar.getTime().toString());
					startAlarm(rqCode);
				}
				handler.close();
				PopupActivity.this.finish();
			}
		});

		AlertDialog ad = ab.create();
		ad.show();

	}

	//
	// private void decodeTrophy(int i){
	// if((i - Math.pow(2,4))>=0){
	// trophyPermission[0]=true;
	// i-=Math.pow(2,4);
	// }if((i - Math.pow(2,3))>=0){
	// trophyPermission[1]=true;
	// i-=Math.pow(2,3);
	// }if((i - Math.pow(2,2))>=0){
	// trophyPermission[2]=true;
	// i-=Math.pow(2,2);
	// }if((i - Math.pow(2,1))>=0){
	// trophyPermission[3]=true;
	// i-=Math.pow(2,1);
	// }if((i - Math.pow(2,0))>=0){
	// trophyPermission[4]=true;
	// i-=Math.pow(2,0);
	// }
	// }

	private void stopAlarm(int rqCode) {
		PendingIntent pi = pendingIntent(rqCode);
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mManager.cancel(pi);
		pi.cancel();
		MyAlarmService.mp.stop();
		stopService(new Intent("com.example.thewishlight.MyAlarmService"));
	}

	private void startAlarm(int rqCode) {
		// mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
		// pendingIntent());

		mManager.set(AlarmManager.RTC_WAKEUP, eCalendar.getTimeInMillis(),
				pendingIntent2(rqCode));
		Log.d("rqCode", String.valueOf(rqCode - 1));
		Toast.makeText(getApplicationContext(),
				eCalendar.getTime().toString() + "�˶��� �����߽��ϴ�",
				Toast.LENGTH_SHORT).show();
		Log.d("make restart alarm", eCalendar.getTime().toString());

	}

	private PendingIntent pendingIntent2(int rqCode) {
		Intent mIntent = new Intent(getApplicationContext(),
				MyAlarmService.class);

		mIntent.putExtra("shape", shape);
		mIntent.putExtra("title", title);
		mIntent.putExtra("content", content);
		mIntent.putExtra("rqCode", rqCode);
		mIntent.putExtra("seq", 1);
		mIntent.putExtra("success", success);
		mIntent.putExtra("duration", duration);
		

		PendingIntent pi = PendingIntent.getService(getApplicationContext(),
				rqCode, mIntent, 0);

		return pi;
	}

	private PendingIntent pendingIntent(int rqCode) {
		Intent mIntent = new Intent(getApplicationContext(),
				MyAlarmService.class);
		Log.d("second pendingIntent", String.valueOf(rqCode));
		PendingIntent pi = PendingIntent.getService(getApplicationContext(),
				rqCode, mIntent, 0);
		return pi;
	}

}
