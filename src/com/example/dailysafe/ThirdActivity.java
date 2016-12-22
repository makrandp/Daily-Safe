package com.example.dailysafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdActivity extends Activity {
	

	String getmsg,getnum1;
	Button save,clear;
	EditText msg,num1;
	public static final String filename="myshare";
	
	@Override
	protected void onCreate(Bundle savedInstanceState3) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState3);
		setContentView(R.layout.thirdactivity);
		
		
		save = (Button)findViewById(R.id.buttonsave);
		clear = (Button)findViewById(R.id.buttonclear);
		msg = (EditText)findViewById(R.id.editTextmessage);
		num1 = (EditText)findViewById(R.id.editTextnumber1);
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				getmsg = msg.getText().toString();
				getnum1 = num1.getText().toString();
				
				SharedPreferences folder = getSharedPreferences(filename, 0);	//filename and mode
				SharedPreferences.Editor editor = folder.edit();		//to create the editor
				
				editor.putString("msg",getmsg);	//key and value
				editor.putString("num1",getnum1);	//key and value...key is used to reference while reading
				
				editor.commit();	
				Toast.makeText(getApplicationContext(), "Saved",Toast.LENGTH_SHORT).show();
			}
		});
	
	
	clear.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		
			msg.setText("");
			num1.setText("");


			
		}
	});	
		
		
	}
	
	
	
}
