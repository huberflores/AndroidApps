package ee.ut.cs.service.binder;

import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class ServiceMain extends ListActivity {
	
	public final static String TAG = ServiceMain.class.getCanonicalName();
	
	private KeywordService myService; 

	private ArrayAdapter<String> adapter;
	private List<String> keywordList;

	BroadcastReceiver scheduler;
	BroadcastReceiver customScheduler;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_main); 
		
		keywordList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				keywordList);
	
		setListAdapter(adapter);
		
		
		
		//registerServiceScheduler();
		
		registerCustomScheduler(); //comment this procedure if the action is declared in the Manifest.
		
	}
	
	
	/**
	 * Register the scheduler "MyScheduleReceiver"
	 * The scheduler implements an alarm that initiates a particular 
	 * service each t seconds
	 * 
	 * The scheduler is triggered by an action from the OS
	 */
	
	public void registerServiceScheduler(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_USER_PRESENT);  //this is a predefined Intent from Android
		scheduler = new MyScheduleReceiver();
		registerReceiver(scheduler, filter);
	}
	
	/**
	 * The scheduler is triggered by a customized action which 
	 * is implemented in this same project
	 */
	public void registerCustomScheduler(){
		IntentFilter filter = customIntentFilter();
		customScheduler = new MyScheduleReceiver();
		registerReceiver(customScheduler, filter);

	}
	
	
	public void onClick(View view) {
		
		switch (view.getId()) {
			case R.id.button1:
				if (myService != null) {
					Toast.makeText(this, "Number of elements: " + myService.getWordList().size(),
							Toast.LENGTH_SHORT).show();
					keywordList.clear();
					//myService.serviceTask();
					keywordList.addAll(myService.getWordList());
					adapter.notifyDataSetChanged();
				}
				
				break;
		
			case R.id.button2:
					triggerCustomizedReceiver();
				
				 
			
				break;
				
			case R.id.button3:
				
					stopServiceScheduler();
				
				break;		
		}
		
		
	}
	
	
	private void triggerCustomizedReceiver(){
		Intent intent = new Intent("custom-event");
		  intent.putExtra("message", "data");
		  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			KeywordService.MyBinder b = (KeywordService.MyBinder) binder;

			myService = b.getService(); 
			
			Toast.makeText(ServiceMain.this, "Connected", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			myService = null;
		}
	};
	
	
	@Override
	protected void onResume() {
		super.onResume();
		Intent intent= new Intent(this, KeywordService.class);
		bindService(intent, mConnection,
				Context.BIND_AUTO_CREATE);
		
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
			      new IntentFilter("custom-event"));
	}
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		    // Extract data included in the Intent
		    String message = intent.getStringExtra("message");
		    Log.d(TAG, "Got message from custom receiver: " + message);
		    
		    Intent intentScheduler = new Intent(MyScheduleReceiver.CUSTOM_INTENT);
		    sendBroadcast(intentScheduler);
		    
		    Log.d(MyScheduleReceiver.TAG, "Got message from custom receiver: " + message);
		    
		  }
	};
	
	
	 private static IntentFilter customIntentFilter() {
		    final IntentFilter intentFilter = new IntentFilter();
		    
		    //action is programmed here
		    //it can be also declared within the Manifest, but in that case, this action must be removed. 
		    intentFilter.addAction(MyScheduleReceiver.CUSTOM_INTENT); 
		    return intentFilter;
		}
	

	@Override
	protected void onPause() {
		super.onPause();
		unbindService(mConnection);
		
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	}
	
	
	public void stopServiceScheduler(){
		 Intent intent2 = new Intent(this, MyStartServiceReceiver.class);
	 	    PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent2, 
	               PendingIntent.FLAG_CANCEL_CURRENT);
	 	    
	 	   AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
	 	   alarmManager.cancel(pending);
	 	   
	 	  Toast.makeText(this, "Service stopped: ",Toast.LENGTH_SHORT).show();
	}

	public void onDestroy(){
		//Unregisters the scheduler
		unregisterReceiver(scheduler);
		
		//RULE: a service that is started, it must be stopped as well.
		Intent intent1 = new Intent(this, KeywordService.class);
 	    stopService(intent1);	
 	    
 	  super.onPause();
 	   
 	   
	}
	
}
