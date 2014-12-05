package com.example.thewishlight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;





import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AppStartActivity extends ActionBarActivity {

	Button joinBtn;
	RelativeLayout loginLayout;

	EditText editID;
	EditText editPW;
	Button loginBtn;
	AnimationDrawable frameAnimation;

	// ����
	phpDown task;
	phpUp task2;
	public static List<Client> clientList = new ArrayList<Client>();
	int totalClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_start);

		loginLayout = (RelativeLayout) findViewById(R.id.loginLayout);

		loginLayout.setBackgroundResource(R.drawable.loginsky2);
		frameAnimation = (AnimationDrawable) loginLayout.getBackground();

		LinearLayout loginLayout = (LinearLayout) findViewById(R.id.login);
		AnimationSet set = new AnimationSet(true);
		Animation alpha = new AlphaAnimation(0.0f, 1.0f);
		set.setDuration(2000);
		set.addAnimation(alpha);
		set.setStartOffset(1000);
		loginLayout.setAnimation(set);
		loginLayout.startAnimation(set);

		frameAnimation.start();

		editID = (EditText) loginLayout.findViewById(R.id.inputID);
		editPW = (EditText) loginLayout.findViewById(R.id.inputPW);

		task = new phpDown();

		task.execute("http://ljs93kr.cafe24.com/client.php");

		// �α��� �ϱ�
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String inputId = editID.getText().toString();
				String inputPw = editPW.getText().toString();
				Boolean state = false;
				int position = 0;
				if(inputId.equals("")||inputPw.equals("")){
					Toast.makeText(getApplicationContext(), "������ �Է��ϼ���", Toast.LENGTH_LONG).show();
					return;
				}
				
				for (int i = 0; i < clientList.size(); i++)
					if (clientList.get(i).getId().equals(inputId)) {
						state = true;
						position = i;
					}
				if (state == false)
					Toast.makeText(getApplicationContext(), "�ش� ���̵� �����ϴ�.",
							Toast.LENGTH_LONG).show();
				else if (clientList.get(position).getPw().equals(inputPw)) {
					Toast.makeText(getApplicationContext(), "�α��� �Ϸ�!",
							Toast.LENGTH_LONG).show();

					Intent intent = new Intent(getApplicationContext(),
							MySkyActivity.class);
					intent.putExtra("myID", inputId);
					intent.putExtra("mode", 0);                //change
					startActivity(intent);                

					frameAnimation.stop();

					finish();

				}

				else
					Toast.makeText(getApplicationContext(), "��й�ȣ�� Ʋ�Ƚ��ϴ�.",
							Toast.LENGTH_LONG).show();

			}
		});

		// ȸ�������ϱ�
		joinBtn = (Button) findViewById(R.id.joinBtn);
		joinBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String inputId = editID.getText().toString();
				String inputPw = editPW.getText().toString();
				Boolean state = false;
				
				if(inputId.equals("")||inputPw.equals("")){
					Toast.makeText(getApplicationContext(), "������ �Է��ϼ���", Toast.LENGTH_LONG).show();
					return;
				}

				for (int i = 0; i < clientList.size(); i++)
					if (clientList.get(i).getId().equals(inputId)) {
						state = true;
					}
				if (state == true)
					Toast.makeText(getApplicationContext(),
							"�̹� ���� ���̵� �����մϴ�.", Toast.LENGTH_LONG).show();
				else {
					if (inputPw.equals("")) {
						Toast.makeText(getApplicationContext(), "����Է��ϼ���",
								Toast.LENGTH_LONG).show();
					} else {
						task2 = new phpUp();

						task2.execute("http://ljs93kr.cafe24.com/join.php?_id="
								+ totalClient + "&id=" + inputId + "&pw="
								+ inputPw);

						task2 = new phpUp();

						task2.execute("http://ljs93kr.cafe24.com/wlb.php?id=" +inputId);
						
						task2 = new phpUp();

					    task2.execute("http://ljs93kr.cafe24.com/friend.php?id=" + inputId);
						
					    editID.setText("");
						editPW.setText("");
						Toast.makeText(getApplicationContext(), "ȸ�������� �����Ͽ����ϴ�!",
								Toast.LENGTH_LONG).show();
						
						task = new phpDown();
						task.execute("http://ljs93kr.cafe24.com/client.php");
					}
				}
			}
		});

	}

	// �б������Ľ�

	private class phpDown extends AsyncTask<String, Integer, String> {

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
			StringTokenizer st = new StringTokenizer(str, ":");
			Log.d("tc", str);
			totalClient = Integer.parseInt(st.nextToken());
			Log.d("tc", String.valueOf(totalClient));
			for (int i = 0; i < totalClient; i++)
				clientList.add(new Client(Integer.parseInt(st.nextToken()), st
						.nextToken(), st.nextToken()));

		}
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

	/*
	 * public class ViewAnimation extends Animation{
	 * 
	 * int centerX,centerY;
	 * 
	 * @Override public void initialize(int width, int height, int parentWidth,
	 * int parentHeight) { // TODO Auto-generated method stub
	 * super.initialize(width, height, parentWidth, parentHeight);
	 * setDuration(5000); setFillAfter(true); centerX = width/2; centerY =
	 * height/2;
	 * 
	 * 
	 * }
	 * 
	 * @Override protected void applyTransformation(float interpolatedTime,
	 * Transformation t) { // TODO Auto-generated method stub
	 * super.applyTransformation(interpolatedTime, t);
	 * 
	 * 
	 * 
	 * Matrix matrix = t.getMatrix(); matrix.setScale(interpolatedTime,
	 * interpolatedTime);
	 * 
	 * 
	 * // Matrix matrix = t.getMatrix(); // matrix.setScale(interpolatedTime,
	 * interpolatedTime);
	 * 
	 * }
	 * 
	 * }
	 */

}
