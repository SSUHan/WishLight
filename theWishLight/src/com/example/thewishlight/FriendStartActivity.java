package com.example.thewishlight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendStartActivity extends ActionBarActivity {
	
	List<String> friends = new ArrayList<String>();
	phpDown task;
	phpUp task2;
	
	Button searchBtn;
	EditText friendedit;
	TextView selectFriend;
	boolean search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		init();
		
	}
	
	protected void onResume()
	{
		super.onResume();
		init();
	}
	
	void init()
	{
		setContentView(R.layout.friend_start);
		task=new phpDown();
		task.execute("http://ljs93kr.cafe24.com/friendoutput.php?id="+MySkyActivity.myID);
		Log.d("php","http://ljs93kr.cafe24.com/friendoutput.php?id="+MySkyActivity.myID);
		

		selectFriend=(TextView)findViewById(R.id.selectfriend);
		friendedit=(EditText)findViewById(R.id.friendedit);
		searchBtn=(Button)findViewById(R.id.friendsearch);
		searchBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				search=false;
				for(int i=0;i<AppStartActivity.clientList.size();i++)
					if(friendedit.getText().toString().equals(
							AppStartActivity.clientList.get(i).getId()))
					{
						selectFriend.setVisibility(View.VISIBLE);
						selectFriend.setText(AppStartActivity.clientList.get(i).getId());
						search=true;
					}
				if(search==false)
				{
					selectFriend.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(),"그런 친구는 존재하지 않아요!", Toast.LENGTH_LONG).show();
				}
				
			}
			
		});
		selectFriend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean exist=false;
				String fid="";
				if(search==true)
				{
					for(int i=0;i<friends.size();i++)
						if(friends.get(i).equals(friendedit.getText().toString()))
							exist=true;
							
					if(exist==false){
						
						task2=new phpUp();
						task2.execute("http://ljs93kr.cafe24.com/friendinput.php?id="+MySkyActivity.myID
								+"&fid="+friendedit.getText().toString());
						task2=new phpUp();
						task2.execute("http://ljs93kr.cafe24.com/friendinput.php?id="+friendedit.getText().toString()
								+"&fid="+MySkyActivity.myID);
						
						Toast.makeText(getApplicationContext(), friendedit.getText().toString()+" 친구 추가 완료", Toast.LENGTH_LONG).show();
						
						init();
					}
					else
						Toast.makeText(getApplicationContext(), "친구가 이미 존재합니다.", Toast.LENGTH_LONG).show();
					
				}
				
			}
			
		});
		
	}
	private class phpUp extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				// 연결 url 설정
				URL url = new URL(urls[0]);
				// 커넥션 객체 생성
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 연결되었으면.
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// 연결되었음 코드가 리턴되면.
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"UTF-8"));
						for (;;) {
							// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
							String line = br.readLine();
							Log.d("line", line);
							if (line == null)
								break;
							// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
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

		protected void onPostExecute(String str) 
		{

			//Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
		}
	}
	
	private class phpDown extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {
				// 연결 url 설정
				URL url = new URL(urls[0]);
				// 커넥션 객체 생성
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 연결되었으면.
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// 연결되었음 코드가 리턴되면.
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream(),
										"UTF-8"));
						for (;;) {
							// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
							String line = br.readLine();
							Log.d("line", line);
							if (line == null)
								break;
							// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
							jsonHtml.append(line + "\n");
						}
						Log.d("json", "d");
						Log.d("json", jsonHtml.toString());
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

			friends = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(str, ",");
			Log.d("tc", str);
			int count=Integer.parseInt(st.nextToken());
			Log.d("tc", String.valueOf(count));
			for (int i = 0; i < count; i++)
				friends.add(st.nextToken());

			ListView list = (ListView) findViewById(R.id.friendlist);
			ListAdapter adapter = new ListAdapter();
			list.setAdapter(adapter);
		}
	}
	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return friends.size();
		}

		@Override
		public Object getItem(int position) {
			return friends.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			FriendList layout = new FriendList(getApplicationContext());

			final int pos=position;
			layout.setFriendText(friends.get(position));
			
			layout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
				
			});
			layout.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View v) {
					task2=new phpUp();
					task2.execute("http://ljs93kr.cafe24.com/frienddelete.php?id="+MySkyActivity.myID
							+"&fid="+friends.get(pos));
					task2=new phpUp();
					task2.execute("http://ljs93kr.cafe24.com/frienddelete.php?id="+friends.get(pos)
							+"&fid="+MySkyActivity.myID);
				
					Toast.makeText(getApplicationContext(), friends.get(pos)+" 친구 삭제 완료", Toast.LENGTH_LONG).show();
					
					init();
					return true;
					// TODO Auto-generated method stub
					
				}
				
			});
			
			return layout;
		}
	}

}
