package ee.ut.cs.dev.aidl.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class KeywordService extends Service {
	
	private List<KeywordData> list = new ArrayList<KeywordData>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		list.add(new KeywordData("Helsinki", 1));
		list.add(new KeywordData("Tartu", 2));
		list.add(new KeywordData("Tallinn", 3));
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return new IKeywordService.Stub() {
			@Override
			public List<KeywordData> getWords() throws RemoteException {
				return list;
			}
		};
	}

}
