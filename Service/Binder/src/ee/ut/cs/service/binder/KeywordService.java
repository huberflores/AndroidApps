package ee.ut.cs.service.binder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class KeywordService extends Service{
	
	private final String TAG = KeywordService.class.getSimpleName();
	
	private final IBinder mBinder = new MyBinder();
	private ArrayList<String> list = new ArrayList<String>();
	
	
	@Override
    public void onCreate() {
        Log.d(TAG, "onCreate called"); 
    }
	
	
	
	/**
	 * It should be called explicitly
	 * onStartCommand() is not called when the service is binded
	 * In case the service is started with onStartCommand(), then it has to be stopped with stopService()
	 */

	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//android.os.Debug.waitForDebugger();
		
		/*Log.d(TAG, "onStartCommand");*/
		
		return Service.START_NOT_STICKY;
		
	}
	
	
	public void serviceTask(){
		Random random = new Random();
		if (random.nextBoolean()) {
			list.add("Linux");
		}
		if (random.nextBoolean()) {
			list.add("Android");
		}
		if (random.nextBoolean()) {
			list.add("iPhone");
		}
		if (random.nextBoolean()) {
			list.add("Windows7");
		}
		if (list.size() >= 20) {
			list.remove(0);
		}

	}
	
	
	
	@Override
	public IBinder onBind(Intent arg0) { //method to implement
		return mBinder;
	}
	

	public List<String> getWordList() {
		return list;
	}
	
	
	public class MyBinder extends Binder {
		KeywordService getService() {
			return KeywordService.this;
		}
	}

}
