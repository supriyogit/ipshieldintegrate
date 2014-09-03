package edu.ucla.ee.nesl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class dataService extends Service {

	String TAG = "DataService" ;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// Creating an unbound service
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d("TAG", "Creating Service First Time");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Stopping Service");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("TAG", "Starting Service");
		return super.onStartCommand(intent, flags, startId);
	}
}