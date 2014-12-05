package com.example.thewishlight;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class MyRankActivity extends ActionBarActivity implements
View.OnTouchListener {
	
	// Flipper
		ViewFlipper flipper;
		float XAtDown;
		float XAtUp;
		int count = 0;

    // Flipper

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_start);
		// Flipper
		flipper = (ViewFlipper) findViewById(R.id.rankingviewFlipper);
		flipper.setOnTouchListener(this);
		// Flipper
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
			//유효하지 않을 터치일때 
			else{
			}
		}
		return true;
	}
}

