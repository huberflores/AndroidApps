package ee.cs.ut.service.startstop;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    
	private final String TAG = LocalService.class.getSimpleName();
	Thread Runningthread = null;
    private final Random mGenerator = new Random();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand executed");
        
        Runningthread = new Thread(new RunningThread());
  	  	Runningthread.start();
        
        return Service.START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    
    public int getRandomNumber() {
      return mGenerator.nextInt(100);
    }
    
    public void notifyLabelChange(String value){
		Intent intent = new Intent(StartStopMain.label);
	    intent.putExtra("message", false);
	    intent.putExtra("value", value);
	    
	    sendBroadcast(intent);
	  }
    
    
    private class RunningThread implements Runnable {
    	
    	@Override
    	public void run() {
    		RunService();
    	}
    };
    
    
    public void RunService(){
    	int number = getRandomNumber();
    	
    	String result = "The result is: "+ number;
    	
    	notifyLabelChange(result);
    }
    
}