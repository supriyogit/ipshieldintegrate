package edu.ucla.ee.nesl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button startButton, stopButton;
	
	String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
  
    //Create an anonymous implementation of OnClickListener
    private OnClickListener startListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(TAG,"onClick() called - start button");
			Intent intent = new Intent(MainActivity.this, dataService.class);
			startService(intent);
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			
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
