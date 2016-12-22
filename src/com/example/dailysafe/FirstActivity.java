package com.example.dailysafe;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.widget.ImageView;

public class FirstActivity extends Activity{

	 AnimationDrawable frameAnimation;
	 ImageView animationview;
	 
	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    animationview = (ImageView)findViewById(R.id.imageView1);
    animationview.setBackgroundResource(R.drawable.animation);
 
	/*
    	Thread timer = new Thread(){
    		public void run()
    		{
    			try {
    				
    				
    				frameAnimation.start();
    			
    				
    				sleep(3000);		// to sleep the activity
    		
       			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}finally{
    				frameAnimation.stop();
    				Intent second = new Intent(getApplicationContext(),SecondActivity.class);
    				startActivity(second);
    			}
    			
    		}
    			
    		};												//imp semicolon
    		timer.start();

      */
	
	
	
    }


	 public void onWindowFocusChanged (boolean hasFocus) {
			super.onWindowFocusChanged(hasFocus);
			  Timer timer;
			  TimerTask timerTask;
			 frameAnimation = (AnimationDrawable) animationview.getBackground();
			if(hasFocus) {
				frameAnimation.start();
				
				   timer = new Timer();  
				  
				    timerTask = new TimerTask(){  
				       @Override  
				       public void run() {  
				  
				                  frameAnimation.stop();  
				                  Intent second = new Intent(getApplicationContext(),SecondActivity.class);
				              	startActivity(second);

				       }  
				      };  
				
				      timer.schedule(timerTask,800);  

			}
						
			
			
		}
	 
	 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();			// to kill this activity
	}

    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_first, menu);
        return true;
    }



    
}
