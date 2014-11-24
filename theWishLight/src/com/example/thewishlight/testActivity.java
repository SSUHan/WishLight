package com.example.thewishlight;

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
import android.widget.ImageView;

public class testActivity extends ActionBarActivity {

	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;

	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.image1btn);
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image1 = (ImageView) findViewById(R.id.image1);

				Animation animation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.image1);
				image1.startAnimation(animation);

			}
		});

		btn2 = (Button) findViewById(R.id.image2btn);
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image2 = (ImageView) findViewById(R.id.image2);

				Animation animation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.image2);
				image2.startAnimation(animation);
			}
		});
		
		btn3 = (Button) findViewById(R.id.image3btn);
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image3 = (ImageView) findViewById(R.id.image3);

				Animation animation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.image3);
				image3.startAnimation(animation);
			}
		});
		btn4 = (Button) findViewById(R.id.image4btn);
		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				image4 = (ImageView) findViewById(R.id.image4);
				
				AnimationSet set = new AnimationSet(true);
				
				Animation alpha = new AlphaAnimation(0.0f,1.0f);
				alpha.setDuration(1000);
				set.addAnimation(alpha);
				
				
				image4.startAnimation(set);
				
			}
		});
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
