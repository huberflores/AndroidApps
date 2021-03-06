package ee.ut.cs.service.binder;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyScheduleReceiver extends BroadcastReceiver {
	
	public final static String TAG = MyScheduleReceiver.class.getCanonicalName();
	
	public static final String CUSTOM_INTENT = "ee.ut.cs.intent.action.ACTIVE";

	// Restart service every 30 seconds
	private static final long REPEAT_TIME = 1000 * 30;

	@Override
	public void onReceive(Context context, Intent intent) {
		AlarmManager service = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		
		/*The service starts in the onStartCommand method*/
		Intent i = new Intent(context, MyStartServiceReceiver.class);
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
				PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar cal = Calendar.getInstance();
		// Start 30 seconds after boot completed
		cal.add(Calendar.SECOND, 30);
		//
		// Fetch every 30 seconds
		// InexactRepeating allows Android to optimize the energy consumption
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), REPEAT_TIME, pending);
		
		Toast.makeText(context, "Service is schedule to restart each: " + REPEAT_TIME/1000 + " seconds",
		        Toast.LENGTH_LONG).show();
		
		System.out.println("MyScheduleReceiver called...");

		// service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
		// REPEAT_TIME, pending);

	}
}
