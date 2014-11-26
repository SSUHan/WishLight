package com.example.thewishlight;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySkyActivity extends ActionBarActivity {

	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;

	MyDBHandler handler;

	
	Button deleteBtn;

	EditText editDelete;

	TextView textView01;

	LinearLayout myskyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysky);

		textView01 = (TextView) findViewById(R.id.textView01);

		
		

		deleteBtn = (Button) findViewById(R.id.deleteBtn);
		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editDelete = (EditText) findViewById(R.id.inputDelete);
				String title = editDelete.getText().toString();
				handler.delete(title);
				editDelete.setText("");
				showDB();
			}
		});

		showDB();
	}

	public void mClick(View v) {
		if (v.getId() == R.id.myskyLayout) {
			Intent intent = new Intent(getApplicationContext(),
					MakeWLBActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.image1) {
			Toast.makeText(getApplicationContext(), "image1",
					Toast.LENGTH_SHORT).show();
			image1 = (ImageView) findViewById(R.id.image1);
			Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.image1);
			image1.startAnimation(animation);
		} else if (v.getId() == R.id.image2) {
			Toast.makeText(getApplicationContext(), "image2",
					Toast.LENGTH_SHORT).show();
			image2 = (ImageView) findViewById(R.id.image2);
			Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.image2);
			image2.startAnimation(animation);
		} else if (v.getId() == R.id.image3) {
			Toast.makeText(getApplicationContext(), "image3",
					Toast.LENGTH_SHORT).show();
			image3 = (ImageView) findViewById(R.id.image3);

			Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.image3);
			image3.startAnimation(animation);
		} else if (v.getId() == R.id.image4) {
			Toast.makeText(getApplicationContext(), "image4",
					Toast.LENGTH_SHORT).show();
			image4 = (ImageView) findViewById(R.id.image4);

			AnimationSet set = new AnimationSet(true);

			Animation alpha = new AlphaAnimation(0.0f, 1.0f);
			alpha.setDuration(1000);
			set.addAnimation(alpha);

			image4.startAnimation(set);

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		showDB();
		super.onResume();
	}

	private void showDB() {
		handler = MyDBHandler.open(getApplicationContext(), "wlb");

		String data = "";
		Cursor c = handler.select();
		startManagingCursor(c);
		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("_id"));
			String title = c.getString(c.getColumnIndex("title"));
			String content = c.getString(c.getColumnIndex("content"));
			int shape = c.getInt(c.getColumnIndex("shape"));
			data += _id + " " + title + " " + content + " " + "shpae:" + shape+"\n";
		}
		textView01.setText(data);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
