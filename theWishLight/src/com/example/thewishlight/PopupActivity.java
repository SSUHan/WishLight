package com.example.thewishlight;


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

public class PopupActivity extends Activity {
	
	
	EditText edit;
	Button answerBtn;
	TextView textView01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup_start);
		// ȭ���� ��������� �����ֱ�
		// Ű��� �����ϱ�
		// ȭ���ѱ�
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		/*
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		int width = (int) (display.getWidth() * 0.7); //Display �������� 70%

		int height = (int) (display.getHeight() * 0.9);  //Display �������� 90%

		getWindow().getAttributes().width = width;

		getWindow().getAttributes().height = height;
		*/
		Intent mIntent = getIntent();
		final int rqCode = mIntent.getIntExtra("rqCode", 0);
		String content = mIntent.getStringExtra("content");
		String title = mIntent.getStringExtra("title");
		int shape = mIntent.getIntExtra("shape", 0);
		
		Log.d("popup onCreate","rqCode:"+String.valueOf(rqCode));
		
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		// R.layout.dialog�� xml ���ϸ��̰� R.id.popup�� ������ ���̾ƿ� ���̵�
		View layout = inflater.inflate(R.layout.popup_dialog,
				(ViewGroup) findViewById(R.id.popupLayout));
		
		TextView titleView = (TextView) layout.findViewById(R.id.titleView01);
		TextView contentView = (TextView)layout.findViewById(R.id.contentView01);
		
		titleView.setText(title);
		contentView.setText(content);
		
		Log.d("popup onCreate","shape:"+String.valueOf(shape));
		
		AlertDialog.Builder ab = new AlertDialog.Builder(PopupActivity.this);
		ab.setTitle("�ҿ��� ����"); // Ÿ��Ʋ�� ����
		ab.setView(layout); // dialog.xml ������ ��� ����
		ab.setIcon(MySkyActivity.determineShape(shape));
		
		ab.setPositiveButton("�ݱ�", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				stopAlarm(rqCode);
				PopupActivity.this.finish();
			}
		});
		
		AlertDialog ad = ab.create();
		ad.show();
	
	}
	
	private void stopAlarm(int rqCode){
		PendingIntent pi = pendingIntent(rqCode);
		AlarmManager mManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		mManager.cancel(pi);
		pi.cancel();
		MyAlarmService.mp.stop();
		stopService(new Intent("com.example.thewishlight.MyAlarmService"));
	}
	
	private PendingIntent pendingIntent(int rqCode){
		Intent mIntent = new Intent(getApplicationContext(),MyAlarmService.class);
		Log.d("second pendingIntent",String.valueOf(rqCode));
		PendingIntent pi = PendingIntent.getService(getApplicationContext(), rqCode, mIntent, 0);
		return pi;
	}

}
