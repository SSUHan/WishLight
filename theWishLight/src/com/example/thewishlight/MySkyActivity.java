package com.example.thewishlight;

import java.util.Random;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MySkyActivity extends ActionBarActivity implements
		View.OnTouchListener {

	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;

	MyDBHandler handler; // 풍등 디비 핸들러

	Button deleteBtn;

	EditText editDelete;

	TextView textView01;

	RelativeLayout myskyLayout;
	
	Button goRankBtn;
	Button goFriendBtn;

	// Flipper
	ViewFlipper flipper;
	float YAtDown;
	float YAtUp;
	int count = 0;

	// Flipper

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		showDB();
	}

	/*
	public void mClick(View v) {
		if (v.getId() == R.id.myskyLayout) {
			Intent intent = new Intent(getApplicationContext(),
					MakeWLBActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.goRankBtn) {
			Intent intent = new Intent(getApplicationContext(),
					MyRankActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.goFriendBtn) {
			Intent intent = new Intent(getApplicationContext(),
					FriendStartActivity.class);
			startActivity(intent);
		}
		
		 * else if (v.getId() == R.id.image1) {
		 * Toast.makeText(getApplicationContext(), "image1",
		 * Toast.LENGTH_SHORT).show(); image1 = (ImageView)
		 * findViewById(R.id.image1); Animation animation =
		 * AnimationUtils.loadAnimation( getApplicationContext(),
		 * R.anim.image1); image1.startAnimation(animation); } else if
		 * (v.getId() == R.id.image2) { Toast.makeText(getApplicationContext(),
		 * "image2", Toast.LENGTH_SHORT).show(); image2 = (ImageView)
		 * findViewById(R.id.image2); Animation animation =
		 * AnimationUtils.loadAnimation( getApplicationContext(),
		 * R.anim.image2); image2.startAnimation(animation); } else if
		 * (v.getId() == R.id.image3) { Toast.makeText(getApplicationContext(),
		 * "image3", Toast.LENGTH_SHORT).show(); image3 = (ImageView)
		 * findViewById(R.id.image3);
		 * 
		 * Animation animation = AnimationUtils.loadAnimation(
		 * getApplicationContext(), R.anim.image3);
		 * image3.startAnimation(animation); } else if (v.getId() ==
		 * R.id.image4) { Toast.makeText(getApplicationContext(), "image4",
		 * Toast.LENGTH_SHORT).show(); image4 = (ImageView)
		 * findViewById(R.id.image4);
		 * 
		 * AnimationSet set = new AnimationSet(true);
		 * 
		 * Animation alpha = new AlphaAnimation(0.0f, 1.0f);
		 * alpha.setDuration(1000); set.addAnimation(alpha);
		 * 
		 * image4.startAnimation(set);
		 * 
		 * }
		 */

	

	public void makeWLB(int shape, int topMargin, int leftMargin, String title,
			String content) {

		ImageView wlb = new ImageView(this);
		wlb.setBackgroundResource(determineShape(shape));

		final int mShape = shape;
		final String mTitle = title;
		final String mContent = content;
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.basic1);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// 시작위치 설정
		params.topMargin = topMargin;
		params.leftMargin = leftMargin;

		wlb.setLayoutParams(params);

		wlb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Context mContext = getApplicationContext();
				String str1 = mTitle;
				String str2 = mContent;
				int shape = mShape;
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(LAYOUT_INFLATER_SERVICE);

				// R.layout.dialog는 xml 파일명이고 R.id.popup은 보여줄 레이아웃 아이디
				View layout = inflater.inflate(R.layout.wlb_dialog,
						(ViewGroup) findViewById(R.id.popup));
				TextView wlbView = (TextView) layout.findViewById(R.id.wlbView);
				AlertDialog.Builder aDialog = new AlertDialog.Builder(
						MySkyActivity.this);

				wlbView.setText(" -제목: " + str1 + "\n\n -내용: " + str2
						+ "\n\n -모양: " + shape);
				aDialog.setTitle("소원등 정보"); // 타이틀바 제목
				aDialog.setView(layout); // dialog.xml 파일을 뷰로 셋팅

				// 그냥 닫기버튼을 위한 부분
				aDialog.setNegativeButton("닫기",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				// 팝업창 생성
				AlertDialog ad = aDialog.create();
				ad.show();// 보여줌!

			}
		});

		// 소원등 길게 누르면 삭제되도록 만듬
		wlb.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ab = new AlertDialog.Builder(
						MySkyActivity.this);
				ab.setTitle("소원등 삭제");
				ab.setMessage("해당 소원등을 정말 삭제하시겠습니까?");
				ab.setNeutralButton("닫기",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dlg, int sumthin) {
								// 닫기 버튼을 누르면 아무것도 안하고 닫기 때문에 그냥 비움

							}

						});
				ab.setPositiveButton("삭제",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String title = mTitle;
								handler.delete(title);
								Toast.makeText(getApplicationContext(), "삭제완료",
										Toast.LENGTH_SHORT).show();
								showDB();
							}
						});

				AlertDialog ad = ab.create();
				ad.show();

				return true;
			}
		});

		myskyLayout.addView(wlb);
		wlb.startAnimation(anim);
	}

	// 모양 결정 함수
	private int determineShape(int shape) {
		switch (shape) {
		case 1:
			return R.drawable.bluelight;
		case 2:
			return R.drawable.redlight;
		case 3:
			return R.drawable.redlight2;
		case 4:
			return R.drawable.redlight3;
		case 5:
			return R.drawable.roselight;
		case 6:
			return R.drawable.roselight2;
		default:
			return -1;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		showDB();
		super.onResume();
	}

	// 풍등 디비 보여주는 함수
	private void showDB() {
		setContentView(R.layout.mysky);
		
		goRankBtn = (Button)findViewById(R.id.goRankBtn);
		goRankBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),MyRankActivity.class);
				startActivity(intent);
				count=0;
			}
		});
		goFriendBtn = (Button)findViewById(R.id.goFriendBtn);
		goFriendBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),FriendStartActivity.class);
				startActivity(intent);
				count=0;
			}
		});

		// Flipper
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		flipper.setOnTouchListener(this);
		// Flipper

		Intent intent = getIntent();
		String myID = intent.getStringExtra("myID");
		Toast.makeText(getApplicationContext(), myID + "의 하늘입니다",
				Toast.LENGTH_LONG).show();

		myskyLayout = (RelativeLayout) findViewById(R.id.myskyLayout);
		/*
		myskyLayout.setOnClickListener(new OnClickListener()
		{
			 @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Log.d("onClick", String.valueOf(v.getId()));
				 Intent intent = new Intent(getApplicationContext(),
							MakeWLBActivity.class);
					startActivity(intent);
			}
		});
		*/
		myskyLayout.setOnTouchListener(this);
		textView01 = (TextView) findViewById(R.id.textView01);

		handler = MyDBHandler.open(getApplicationContext(), "wlb");

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

		//
		String data = "";
		Cursor c = handler.select();
		startManagingCursor(c);
		Random ranTop = new Random();
		Random ranLeft = new Random();
		Log.d("db count", String.valueOf(c.getCount()));
		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("_id"));
			String title = c.getString(c.getColumnIndex("title"));
			String content = c.getString(c.getColumnIndex("content"));
			int shape = c.getInt(c.getColumnIndex("shape"));
			int top = ranTop.nextInt(500);
			int left = ranLeft.nextInt(500);
			Log.d("start location", "top:" + top + "left:" + left);
			makeWLB(shape, top, left, title, content);

			data += _id + " " + title + " " + content + " " + "shpae:" + shape
					+ "\n";
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

	// Flipper ㄱ
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// 터치 이벤트가 일어난 뷰가 ViewFlipper가 아니면 return
		Log.d("onTouch", String.valueOf(event));
		if (v != flipper)
			return false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			YAtDown = event.getY();// 터치 시작지점 y좌표 저장
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			YAtUp = event.getY();// 터치 끝난지점 y좌표 저장

			if ((YAtDown - YAtUp) > 100 || ((YAtDown - YAtUp) < -100)) {
				if ((YAtUp < YAtDown) && (count == 0)) {
					// 아래서 위로
					flipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.flipani));
					count++;
					flipper.showNext();
				} else if ((YAtUp > YAtDown) && (count == 1)) {
					// 위에서 아래로
					count--;
					flipper.showPrevious();
					flipper.setInAnimation(AnimationUtils.loadAnimation(this,
							R.anim.flipani));
				}
			}
			//유효하지 않을 터치일때 
			else{
				Log.d("unable", String.valueOf(event));
				
					
					Intent intent = new Intent(getApplicationContext(),
							MakeWLBActivity.class);
					startActivity(intent);
					count=0;
				
			}
		}
		return true;
	}
}
