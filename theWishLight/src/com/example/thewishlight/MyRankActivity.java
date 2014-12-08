package com.example.thewishlight;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MyRankActivity extends ActionBarActivity implements
		View.OnTouchListener {

	// Flipper
	ViewFlipper flipper;
	float XAtDown;
	float XAtUp;
	int count = 0;

	// Flipper

	Button trophy1, trophy2, trophy3, trophy4, trophy5;
	boolean[] trophyPermission = new boolean[5];
	
	TextView starCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_start);
		// Flipper
		flipper = (ViewFlipper) findViewById(R.id.rankingviewFlipper);
		flipper.setOnTouchListener(this);
		// Flipper
		
		trophy1 = (Button)findViewById(R.id.tropy1);
		trophy2 = (Button)findViewById(R.id.tropy2);
		trophy3 = (Button)findViewById(R.id.tropy3);
		trophy4 = (Button)findViewById(R.id.tropy4);
		trophy5 = (Button)findViewById(R.id.tropy5);
		
		starCount = (TextView)findViewById(R.id.starstar);
		starCount.setText(String.valueOf(MySkyActivity.myInfo.getStar()));
		int trophyPerm = MySkyActivity.myInfo.getTrophypermission();
		decodeTrophy(trophyPerm);
		
		if(trophyPermission[0]==true){
			trophy1.setBackgroundResource(R.drawable.oneday);
		}if(trophyPermission[1]==true){
			trophy2.setBackgroundResource(R.drawable.oneweek);
		}
		if(trophyPermission[2]==true){
			trophy3.setBackgroundResource(R.drawable.onemonth);
		}
		if(trophyPermission[3]==true){
			trophy4.setBackgroundResource(R.drawable.halfyear);
		}
		if(trophyPermission[4]==true){
			trophy5.setBackgroundResource(R.drawable.oneyear);
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v != flipper)
			return false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			XAtDown = event.getX();// 터치 시작지점 x좌표 저장
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			XAtUp = event.getX();// 터치 끝난지점 x좌표 저장

			if ((XAtDown - XAtUp) > 100 || ((XAtDown - XAtUp) < -100)) {
				if ((XAtUp < XAtDown) && (count == 0)) {
					//
					flipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.flipani));
					count++;
					flipper.showNext();
				} else if ((XAtUp > XAtDown) && (count == 1)) {
					//
					count--;
					flipper.showPrevious();
					flipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.flipani));
				}
			}
			// 유효하지 않을 터치일때
			else {
			}
		}
		return true;
	}
	
	
	private void decodeTrophy(int i){
		if((i - Math.pow(2,4))>=0){
			trophyPermission[0]=true;
			i-=Math.pow(2,4);
		}if((i - Math.pow(2,3))>=0){
			trophyPermission[1]=true;
			i-=Math.pow(2,3);
		}if((i - Math.pow(2,2))>=0){
			trophyPermission[2]=true;
			i-=Math.pow(2,2);
		}if((i - Math.pow(2,1))>=0){
			trophyPermission[3]=true;
			i-=Math.pow(2,1);
		}if((i - Math.pow(2,0))>=0){
			trophyPermission[4]=true;
			i-=Math.pow(2,0);
		}
	}
}
