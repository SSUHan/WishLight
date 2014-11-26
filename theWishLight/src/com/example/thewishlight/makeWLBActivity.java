package com.example.thewishlight;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class makeWLBActivity extends ActionBarActivity {
	
	
	EditText editTitle;
	EditText editContent;
	EditText editShape;
	
	Button makeSave;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_wlb);
		
		
	}
	
	public void saveClick(View v){
		Toast.makeText(getApplicationContext(), "saveClick", Toast.LENGTH_SHORT).show();
	}

}
