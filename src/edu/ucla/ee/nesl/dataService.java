package edu.ucla.ee.nesl;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;



public class dataService extends Service implements SensorEventListener, LocationListener {

	String TAG = "DataService" ;
	SensorManager sensorManager = null;
	LocationManager locationManager = null;
	String server = "", client = "";
	int port = 0;
	SimpleConnection connection;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// Creating an unbound service
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d("TAG", "Creating Service First Time");
		sensorManager = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
		locationManager = (LocationManager)this.getSystemService(this.LOCATION_SERVICE);
		Log.d(TAG, "Initialized the managers");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Stopping Service");
		sensorManager.unregisterListener(this);
		connection = null;
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Sensor sensor;
		Log.d("TAG", "Starting Service");
		// Log.d(TAG,"server = " + intent.getStringExtra("server") + " client = " + intent.getStringExtra("client") + " port = " + intent.getIntExtra("port",0));
		server = intent.getStringExtra("server");
		client = intent.getStringExtra("client");
		port = intent.getIntExtra("port",0);
		//connect = connectToMQTT(server, client, port);
		if(intent.getBooleanExtra("isAccChecked", false)) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		if(intent.getBooleanExtra("isGyroChecked", false)) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		if(intent.getBooleanExtra("isLightChecked", false)) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		connection = SimpleConnection.createConnection(client, server, port);
		
		if(connection != null)
			Log.d(TAG, "Connected to MQTT Server");
		else 
			Log.d(TAG, "Not connected to Server");
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		String str = "";
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			str = "a-X = " + event.values[0] + "; a-Y = " + event.values[1] + "; a-Z = " + event.values[2];
			if(connection != null) {
				connection.publish("accelerometer", str);
			}
			//Log.d(TAG, str);
		}
		if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			str = "g-X = " + event.values[0] + "; g-Y = " + event.values[1] + "; g-Z = " + event.values[2];
			if(connection != null) {
				connection.publish("gyroscope", str);
			}
			Log.d(TAG, str);
		}
		if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
			str = "light = " + event.values[0];
			if(connection != null) {
				connection.publish("light", str);
			}
			Log.d(TAG, str);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}