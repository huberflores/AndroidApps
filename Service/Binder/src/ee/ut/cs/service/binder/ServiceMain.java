package ee.ut.cs.service.binder;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class ServiceMain extends ListActivity {
	
	private KeywordService myService; 

	private ArrayAdapter<String> adapter;
	private List<String> keywordList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_main); 
		
		keywordList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				keywordList);
	
		setListAdapter(adapter);
		
	}
	
	public void onClick(View view) {
		if (myService != null) {
			Toast.makeText(this, "Number of elements: " + myService.getWordList().size(),
					Toast.LENGTH_SHORT).show();
			keywordList.clear();
			//myService.serviceTask();
			keywordList.addAll(myService.getWordList());
			adapter.notifyDataSetChanged();
		}
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
	}

	@Override
	protected void onPause() {
		super.onPause();
		unbindService(mConnection);
	}

	
}
