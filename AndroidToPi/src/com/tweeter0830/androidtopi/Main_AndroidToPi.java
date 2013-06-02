package com.tweeter0830.androidtopi;

import com.tweeter0830.androidtopi.SensorWorker;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Main_AndroidToPi extends Activity {
	// State Array
	private double[] currentState_ = new double[6];
	private double[] currentMeas_ = new double[7];
	private double[] measZeroOffset_ = new double[7];

	//A SensorWorker to take care of getting the sensor readings from the phone
	private SensorWorker sensorWorker_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main__android_to_pi);
		
        //Create a SensorWorker to take care of getting the sensor readings from the phone
    	sensorWorker_ = new SensorWorker( (SensorManager)getSystemService(SENSOR_SERVICE) );
    	//TODO Speed this up when I think the program can handle it
    	sensorWorker_.registerListeners(SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {		
		// Unregister the listener
    	sensorWorker_.unregListeners();
    	
    	super.onStop();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main__android_to_pi, menu);
		return true;
	}

}
