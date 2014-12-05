package com.example.thewishlight;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FriendList extends LinearLayout{

	TextView friendText;
	
	public FriendList(Context context) {
		super(context);
		
		init(context);
	}


	public void init(Context context){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.friend_list, this, true);
		
	
		friendText = (TextView) findViewById(R.id.friendname);
		
	}
	
	
	
	public void setFriendText(String name){
		friendText.setText(name);
	}
}
