package com.example.thewishlight;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class StartActivity extends ActionBarActivity {
	
	Button startBtn;
	RelativeLayout loginLayout;
	AnimationDrawable frameAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_layout);
		
		loginLayout = (RelativeLayout)findViewById(R.id.loginLayout);
		
		loginLayout.setBackgroundResource(R.drawable.loginsky);
		frameAnimation = (AnimationDrawable)loginLayout.getBackground();
		
		
	    LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login);
	    AnimationSet set = new AnimationSet(true);
	    Animation alpha = new AlphaAnimation(0.0f,1.0f);
	    set.setDuration(2000);
	    set.addAnimation(alpha);
	    set.setStartOffset(2000);
	    loginLayout.setAnimation(set);
	    loginLayout.startAnimation(set);
	    
	    frameAnimation.start();
		
		startBtn = (Button)findViewById(R.id.startBtn);
		startBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(getApplicationContext(),MySkyActivity.class);
				startActivity(intent);
				frameAnimation.stop();
				finish();
				
			}
		});
				
	}
	
	/*
	public class ViewAnimation extends Animation{
		
		int centerX,centerY;
		@Override
		public void initialize(int width, int height, int parentWidth,
				int parentHeight) {
			// TODO Auto-generated method stub
			super.initialize(width, height, parentWidth, parentHeight);
			setDuration(5000);
			setFillAfter(true);
			centerX = width/2;
			centerY = height/2;
			
			
		}
		
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			// TODO Auto-generated method stub
			super.applyTransformation(interpolatedTime, t);
			
			
			
			Matrix matrix = t.getMatrix();
		    matrix.setScale(interpolatedTime, interpolatedTime);
			
			
//			Matrix matrix = t.getMatrix();
//		    matrix.setScale(interpolatedTime, interpolatedTime);
			
		}
		
	}
	 */

}
