package com.example.thewishlight;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeWLBActivity extends ActionBarActivity {
	
	MyDBHandler handler;
	
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
		handler = MyDBHandler.open(getApplicationContext(), "wlb");
		editShape = (EditText)findViewById(R.id.inputShape);
		editTitle = (EditText)findViewById(R.id.inputTitle);
		editContent = (EditText)findViewById(R.id.inputContent);
		
		String shape = editShape.getText().toString();
		String title = editTitle.getText().toString();
		String content = editContent.getText().toString();
		
		handler.insert(title, content, Integer.parseInt(shape));
		handler.close();
		finish();
	}

}
