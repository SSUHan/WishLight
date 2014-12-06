package com.example.thewishlight;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmService extends Service {

	private int rqCode;
	public static MediaPlayer mp;
	private NotificationManager nm;
	private Notification myNotification;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "P8e,MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		Log.d("onCreate", "onCreate start");
		
		Toast.makeText(getApplicationContext(), "P8e,MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
		mp = MediaPlayer.create(this,R.raw.aquarium);
		mp.setLooping(true);
		Log.d("media", mp.toString());
		
		//notification
		nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		//myNotification = new Notification(R.drawable.ic_launcher, "Notification from Alarm", System.currentTimeMillis());
		
		
//		Context context = getApplicationContext();
//		String notificationTitle  = "Notification from Alarm!";
//		String notificationText = "Alarm Alarm";
//		Intent myIntent = new Intent(getApplicationContext(), SecondActivity.class);
//		myIntent.putExtra("rqCode", rqCode);
//		
//		PendingIntent pendingIntent = PendingIntent.getActivity(MyAlarmService.this, rqCode, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		myNotification.defaults = Notification.DEFAULT_SOUND;
//		myNotification.flags = Notification.FLAG_AUTO_CANCEL;
//		myNotification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
	
		
		//nm.notify(rqCode,myNotification);
		
		// SecondActivity
		
//		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(myIntent);
	super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		rqCode = intent.getIntExtra("rqCode", 0);
		String content = intent.getStringExtra("content");
		String title = intent.getStringExtra("title");
		int shape = intent.getIntExtra("shape",0);
		int seq = intent.getIntExtra("seq", 0);
		
		Log.d("onStartCommand", "onStartCommand start");
		Log.d("onStartCommand", "rqCode:"+String.valueOf(rqCode));
		Log.d("onStartCommand", "shape:"+String.valueOf(shape));
		mp.start();
		
		myNotification = new Notification(R.drawable.notification1, "Notification from Alarm"+rqCode, System.currentTimeMillis());
		
		Intent myIntent = new Intent(getApplicationContext(), PopupActivity.class);
		myIntent.putExtra("rqCode", rqCode);
		myIntent.putExtra("content", content);
		myIntent.putExtra("title", title);
		myIntent.putExtra("shape", shape);
		myIntent.putExtra("seq", seq);
		Log.d("seq", "seq:"+String.valueOf(seq));
		
		Context context = getApplicationContext();
		String notificationTitle  = "Notification from Alarm!"+rqCode;
		String notificationText = "Alarm Alarm"+rqCode;
		PendingIntent pendingIntent = PendingIntent.getActivity(MyAlarmService.this, rqCode, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		myNotification.defaults = Notification.DEFAULT_SOUND;
		myNotification.flags = Notification.FLAG_AUTO_CANCEL;
		myNotification.setLatestEventInfo(context, notificationTitle, notificationText, pendingIntent);
		
		nm.notify(rqCode,myNotification);
		Log.d("media", mp.toString());
		
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(myIntent);
		
		return rqCode;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "P8e,MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
		mp.stop();
		
		super.onDestroy();
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "P8e,MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
		//mp.start();
		//Log.d("media", mp.toString());
		super.onStart(intent, startId);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "P8e,MyAlarmService.oUnbind()", Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);
	}
}
