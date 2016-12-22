package com.example.dailysafe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SecondActivity extends Activity implements OnClickListener,LocationListener{

	MediaPlayer mediaPlayer;
	Button options,speak,send;
	ToggleButton siren;
	TextView dispmsg,dispnum1;
	//Boolean ch1,ch2= false;
	public static final String filename="myshare";
	SharedPreferences folder;
	String sendmsg,sendtonum1;
	
	LocationManager locationmanager;
	Location location;
public static double  lat =0;
public static double longi = 0;
	public Geocoder gc;	
public static String display="";

	SmsManager smsManager1,smsManager2;


	
	  static final int check=1111;
	
	@Override
	protected void onCreate(Bundle savedInstanceState2) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState2);
		setContentView(R.layout.secondactivity);
		
		
		
		siren=(ToggleButton)findViewById(R.id.toggleButtonsiren);
		options=(Button)findViewById(R.id.buttonoptions);
		speak = (Button)findViewById(R.id.buttonvoice);
		send = (Button)findViewById(R.id.buttonsend);
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
		

    dispmsg = (TextView)findViewById(R.id.editTextmessage);
    dispnum1 = (TextView)findViewById(R.id.editTextnumber1);
		
		 
			folder = getSharedPreferences(filename, 0);
			
		  //  sendmsg= folder.getString("msg","could not load message");	//key and default value if didnt find shared references
		//	sendtonum1=folder.getString("num1", "could not load numer");
	

	

	//	smsManager = SmsManager.getDefault(); 
		locationmanager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			location = locationmanager.getLastKnownLocation(locationmanager.NETWORK_PROVIDER);
			
			
			if(location!=null){
				
			lat=location.getLatitude();
			longi=location.getLongitude();
			}

			
	
			
		    PackageManager pm = getPackageManager();
	        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
	          RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
	        
	        if (activities.size() == 0)
	        {
	            speak.setEnabled(false);
	            Toast.makeText(this, "Voice recognizer not present",
	            	     Toast.LENGTH_SHORT).show();
	        
	        }
	    
	
	        siren.setOnClickListener(this);
			 options.setOnClickListener(this);
			 send.setOnClickListener(this);
			 speak.setOnClickListener(this);
	 
}

	
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
 locationmanager.requestLocationUpdates(locationmanager.NETWORK_PROVIDER,5000,1,this);	// provider,mintime,mindistance,listener
	
			
}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
			locationmanager.removeUpdates(this);
		//ch=false;
		
	}



	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int callerid = v.getId();
		
		switch(callerid)
		{
			
		case R.id.toggleButtonsiren :

			if(siren.isChecked())
			{
				if(mediaPlayer==null)
				 {
					 mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
				 }
				mediaPlayer.start();
				Toast.makeText(getApplicationContext(),"Siren is ON",Toast.LENGTH_SHORT).show();
			}
			else{
				
				 if(mediaPlayer.isPlaying())
			     {
			    	mediaPlayer.stop();
			    	Toast.makeText(getApplicationContext(),"Siren is OFF",Toast.LENGTH_SHORT).show();
			    	mediaPlayer=null;
			     }
       			}
			
			break;
			
		case R.id.buttonoptions :	
			
			Intent third = new Intent(getApplicationContext(),ThirdActivity.class);
			startActivity(third);
			break;
			
		case R.id.buttonsend : 


			if(location!=null){
				
				lat=location.getLatitude();
				longi=location.getLongitude();
				}
			
		//	getlocation();
			
		

		    sendmsg= folder.getString("msg","could not load message");	//key and default value if didnt find shared references
			sendtonum1=folder.getString("num1", "could not load numer");
			
			
		//	dispmsg.setText(""+sendmsg);
	//		dispnum1.setText(""+sendtonum1);
			
			getlocation();
			
				StringBuilder total1 = new StringBuilder();
			total1.append(sendmsg+"\nMy Location is: ");
			total1.append(display);
			total1.append("latitude: "+lat + "\n Longitude: "+longi );
			
			String finalmessagesendto;
			finalmessagesendto=total1.toString();
			 
			smsManager2 = SmsManager.getDefault();		 
			smsManager2.sendTextMessage(sendtonum1,null,finalmessagesendto,null,null); 
		//	ch1=true;
			Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_SHORT).show();
	
			
			break;
			
		case R.id.buttonvoice :
			
			Intent  i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say GO!");
			startActivityForResult(i,check);
			break;
			
		} //switch ends
		
		
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	
		if(requestCode==check && resultCode == RESULT_OK){
		
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	
	//		String sarray[] = results.toArray(new String[results.size()]);
		if(results.contains("go")){

	
			if(location!=null){
				
				lat=location.getLatitude();
				longi=location.getLongitude();
				}
			
		
	//		getlocation();
		

//folder = getSharedPreferences(filename, 0);
			
		    sendmsg= folder.getString("msg","could not load message");	//key and default value if didnt find shared references
			sendtonum1=folder.getString("num1", "could not load numer");
	
//			dispmsg.setText(""+sendmsg);
	//		dispnum1.setText(""+sendtonum1);

			getlocation();
			
//			sendSMS(sendmsg,sendtonum1);
			
		StringBuilder total = new StringBuilder();
		total.append(sendmsg+"\nMy Location: ");
		total.append(display);
		total.append("latitude: "+lat + "\n Longitude: "+longi );
		
		String finalmessagetosend;
		finalmessagetosend=total.toString();
		
		
/*

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        Intent sentIntent = new Intent(SENT);
        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0,sentIntent, 0);

        Intent deliveryIntent = new Intent(DELIVERED);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0,deliveryIntent, 0);

        //—when the SMS has been sent—
        registerReceiver(new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
        switch (getResultCode())
        {
        case Activity.RESULT_OK:
        Toast.makeText(getApplicationContext(), "SMS sent",Toast.LENGTH_SHORT).show();
        break;
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
        Toast.makeText(getApplicationContext(), "Generic failure",Toast.LENGTH_SHORT).show();
        break;
        case SmsManager.RESULT_ERROR_NO_SERVICE:
        Toast.makeText(getApplicationContext(), "No service",Toast.LENGTH_SHORT).show();
        break;
        case SmsManager.RESULT_ERROR_NULL_PDU:
        Toast.makeText(getApplicationContext(), "Null PDU",Toast.LENGTH_SHORT).show();
        break;
        case SmsManager.RESULT_ERROR_RADIO_OFF:
        Toast.makeText(getApplicationContext(), "Radio off",Toast.LENGTH_SHORT).show();
        break;
       }
     }
   }, new IntentFilter(SENT));

        //—when the SMS has been delivered—
        registerReceiver(new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
        switch (getResultCode())
        {
        case Activity.RESULT_OK:
        Toast.makeText(getApplicationContext(), "SMS delivered",Toast.LENGTH_SHORT).show();
        break;
        case Activity.RESULT_CANCELED:
        Toast.makeText(getApplicationContext(), "SMS not delivered",Toast.LENGTH_SHORT).show();
        break; 
        }
        }
        }, new IntentFilter(DELIVERED));

*/
		
		
		
	smsManager1 = SmsManager.getDefault(); 
		smsManager1.sendTextMessage(sendtonum1,null,finalmessagetosend,null,null); 
				
	//	ch2=true;
	Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_SHORT).show();
			
			//locationmanager.requestLocationUpdates(locationmanager.NETWORK_PROVIDER,1000,1,this);
		}//if ends
		
	}//first if ends	
		
		
		
}//function ends
		
	
		
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
	
		
		lat=arg0.getLatitude();
		longi=arg0.getLongitude();
		
	//	getlocation();
		
		
		
		/* no
		gc = new Geocoder(getApplicationContext(),Locale.getDefault());
		try{
			

		List <Address> address = gc.getFromLocation(lat,longi,1);

		 if(address.size()>0)
		 {
			 
			  for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
				
				display = display + address.get(0).getAddressLine(i)+"\n";
			   }
			  
		 }
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
		
no	*/
	
	}

 

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}


public void getlocation()
{
	
	//lat=arg0.getLatitude();
//	longi=arg0.getLongitude();

	try{
		
			gc = new Geocoder(getApplicationContext(),Locale.getDefault());

	List <Address> address = gc.getFromLocation(lat,longi,1);

	 if(address.size()>0)
	 {
		 
		  for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
			
			display = display + address.get(0).getAddressLine(i)+"\n";
		   }
		  
	 }
	 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		
	}
	
	
}//function ends

/*
public void sendSMS(String sendmsg ,String sendtonum1){
	
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    Intent sentIntent = new Intent(SENT);
    PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0,sentIntent, 0);

    Intent deliveryIntent = new Intent(DELIVERED);
    PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0,deliveryIntent, 0);

    //—when the SMS has been sent—
    registerReceiver(new BroadcastReceiver(){
    @Override
    public void onReceive(Context arg0, Intent arg1) {
    switch (getResultCode())
    {
    case Activity.RESULT_OK:
    Toast.makeText(getApplicationContext(), "SMS sent",Toast.LENGTH_SHORT).show();
    break;
    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
    Toast.makeText(getApplicationContext(), "Generic failure",Toast.LENGTH_SHORT).show();
    break;
    case SmsManager.RESULT_ERROR_NO_SERVICE:
    Toast.makeText(getApplicationContext(), "No service",Toast.LENGTH_SHORT).show();
    break;
    case SmsManager.RESULT_ERROR_NULL_PDU:
    Toast.makeText(getApplicationContext(), "Null PDU",Toast.LENGTH_SHORT).show();
    break;
    case SmsManager.RESULT_ERROR_RADIO_OFF:
    Toast.makeText(getApplicationContext(), "Radio off",Toast.LENGTH_SHORT).show();
    break;
   }
 }
}, new IntentFilter(SENT));

    //—when the SMS has been delivered—
    registerReceiver(new BroadcastReceiver(){
    @Override
    public void onReceive(Context arg0, Intent arg1) {
    switch (getResultCode())
    {
    case Activity.RESULT_OK:
    Toast.makeText(getApplicationContext(), "SMS delivered",Toast.LENGTH_SHORT).show();
    break;
    case Activity.RESULT_CANCELED:
    Toast.makeText(getApplicationContext(), "SMS not delivered",Toast.LENGTH_SHORT).show();
    break; 
    }
    }
    }, new IntentFilter(DELIVERED));


	
	StringBuilder total = new StringBuilder();
	total.append(sendmsg+"\nMy Location: ");
	total.append(display);
	total.append("latitude: "+lat + "\n Longitude: "+longi );
	
	String finalmessagetosend;
	finalmessagetosend=total.toString();

	
smsManager1 = SmsManager.getDefault(); 
	smsManager1.sendTextMessage(sendtonum1,null,finalmessagetosend,null,null); 
	
}

public void textmessage()
{
	
	StringBuilder total = new StringBuilder();
	total.append(sendmsg+"\nMy Location is: ");
	total.append(display);
	total.append("I am at latitude: "+lat + "\n Longitude: "+longi );
	
	String finalmessagetosend;
	finalmessagetosend=total.toString();
	 
	smsManager3 = SmsManager.getDefault();
	 
	smsManager3.sendTextMessage(sendtonum1,null,finalmessagetosend,null,null); 
	
	
}//function ends


}
*/

}//class ends