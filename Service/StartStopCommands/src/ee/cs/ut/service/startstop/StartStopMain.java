package ee.cs.ut.service.startstop;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartStopMain extends ActionBarActivity {

	public static String label = "CHANGE_LABEL"; 
	MessageReceiver receiver;
	
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_stop_main);
		
		button = (Button) findViewById(R.id.button1);
		
		button.setOnClickListener(new Button.OnClickListener() {
	         @Override
	         public void onClick(View arg0) { 
	        	 Run();
	         }
	         });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_stop_main, menu);
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

	
	/**
	 * Intent – Data to be used by the Service for any sort of asynchronous execution.
	 * Flag – Representing the history of this start request. We will discuss this in a bit.
	 * Start ID – A unique ID provided by the runtime for this start request. 
	 * If the process is terminated and then at a later stage restarted then onStartCommand() will be called with the same start ID.
	 */
	 private void Run() {
	        startService(new Intent(this, LocalService.class));
	        /*Intent intent = new Intent(this, LocalService.class);
	        intent.putExtra("key", "val");
	        startService(intent);*/
	 }
	 
	 public class MessageReceiver extends BroadcastReceiver {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	
	            boolean flag = intent.getBooleanExtra("message", true);
	        	if(flag){
	        		String value = intent.getStringExtra("value");
	                
	                changeLabel(value);
	        	}else{
	        		String value = intent.getStringExtra("value");
	                
	        		changeLabel(value);
	        	}
	        	
	        }
	  }
	 
	 private void changeLabel(String value){
	    	TextView tv1 = (TextView)this.findViewById(R.id.textView1);
	    	tv1.setText(value);
	 }
	 
	 @Override 
     public void onResume() {
       IntentFilter filter;
       filter = new IntentFilter(StartStopMain.label);
       receiver = new MessageReceiver();
       registerReceiver(receiver, filter);

       super.onResume();
     }

     @Override
     public void onPause() {
       unregisterReceiver(receiver);
             
       super.onPause();
     }
     
     @Override
 	 public void onDestroy(){
 		Intent intent = new Intent(this, LocalService.class);
 	    stopService(intent);	
 	}
	
}
