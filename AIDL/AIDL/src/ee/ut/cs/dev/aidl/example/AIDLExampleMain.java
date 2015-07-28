package ee.ut.cs.dev.aidl.example;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;


public class AIDLExampleMain extends ListActivity {

	private ArrayAdapter<KeywordData> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_aidlexample_main);
        
        adapter = new ArrayAdapter<KeywordData>(this,
				android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
    }


    @Override
	protected void onStart() {
		super.onStart();
		bindService(new Intent(this, KeywordService.class), conn,
				Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		private IKeywordService keywordService;

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//Log.i("AIDL", "Service Connected");
			keywordService = IKeywordService.Stub.asInterface(service);
			try {
				List<KeywordData> words = keywordService.getWords();
				adapter.clear();
				adapter.addAll(words);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			keywordService = null;
		}
	};

}
