package edu.ucla.ee.nesl;


import android.os.Bundle;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

 public class MainActivity extends Activity {
	String TAG = "MainActivity";
	Context mContext;
	Button startButton, stopButton;
	EditText editTextClientID, editTextServer, editTextPort;
	String client = "", server = "";
	int port = 0;
	Boolean isAccChecked = false, isGpsChecked = false, isGyroChecked = false, isLightChecked = false;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        
        setContentView(R.layout.activity_main);
        
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(startListener); 
        
        stopButton = (Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(stopListener); 
        
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	void readParamValues() {
		Editable tmp;
		CheckBox tempCheck;
		
		// read the client, server and port values. 
		editTextClientID = (EditText)findViewById(R.id.clientID);
		tmp = (Editable)editTextClientID.getText();
        client = tmp.toString().trim();
        
        editTextServer = (EditText)findViewById(R.id.server);
        tmp = (Editable)editTextServer.getText();
        server = tmp.toString().trim();
        
        editTextPort = (EditText)findViewById(R.id.port);
        tmp = (Editable)editTextPort.getText();
        port = Integer.parseInt(tmp.toString().trim());
        
        tempCheck = (CheckBox)findViewById(R.id.acc);
        isAccChecked = tempCheck.isChecked();
        
        tempCheck = (CheckBox)findViewById(R.id.gps);
        isGpsChecked = tempCheck.isChecked();
        
        tempCheck = (CheckBox)findViewById(R.id.gyro);
        isGyroChecked = tempCheck.isChecked();
        
        tempCheck = (CheckBox)findViewById(R.id.light);
        isLightChecked = tempCheck.isChecked();
	}
	
	public Intent getIntent() {
		Intent mIntent = new Intent(MainActivity.this, dataService.class);
		mIntent.putExtra("isAccChecked", isAccChecked);
		mIntent.putExtra("isGpsChecked", isGpsChecked);
		mIntent.putExtra("isGyroChecked", isGyroChecked);
		mIntent.putExtra("isLightChecked", isLightChecked);
		
		mIntent.putExtra("client", client);
		mIntent.putExtra("server", server);
		mIntent.putExtra("port", port);
		return mIntent;
	}
	
	//Create an anonymous implementation of OnClickListener
    private OnClickListener startListener = new OnClickListener() {
    	Intent mIntent;
		@Override
		public void onClick(View v) {
			Log.d(TAG,"onClick() called - start button");
			readParamValues();	
			if(!client.isEmpty() && !server.isEmpty() && port != 0) {	
				mIntent = getIntent();
				startService(mIntent);
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
			else {
				String errStr = "You need to provide Client, Server and Port details to connect";
				Toast.makeText(mContext, errStr, Toast.LENGTH_LONG).show();
				Log.d(TAG, errStr);
			}
			Log.d(TAG, "Client = " + client + "; Server = " + server + "; Port = " + port);
		}                                                                                          
    };  

    // Create an anonymous implementation of OnClickListener
    private OnClickListener stopListener = new OnClickListener() {
        public void onClick(View v) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            Log.d(TAG,"onClick() called - stop button");
            Intent intent = new Intent(MainActivity.this, dataService.class);
            stopService(intent);
        } 
    };
 }
