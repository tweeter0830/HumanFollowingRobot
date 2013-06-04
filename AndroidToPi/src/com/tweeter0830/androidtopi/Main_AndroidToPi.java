package com.tweeter0830.androidtopi;

import com.tweeter0830.androidtopi.SensorWorker;

import java.util.Set;

import android.util.Log;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.*;
import android.content.Intent;
import android.view.Menu;

public class Main_AndroidToPi extends Activity {
	public static final String LOGTAG_ = "AndroidToPi_Main";
	private final static int REQUEST_ENABLE_BT = 1;
	
	// State Array
	private double[] currentState_ = new double[6];
	private double[] currentMeas_ = new double[7];
	private double[] measZeroOffset_ = new double[7];
	
	//A SensorWorker to take care of getting the sensor readings from the phone
	private SensorWorker sensorWorker_;
	private BluetoothDevice raspPi_ = null;
	private String RaspPiMacAddr_ = "00:0f:54:0a:a9:d3";//TODO Get RaspberryPi address
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main__android_to_pi);
		
		Log.v(LOGTAG_, "Started Activity");
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(LOGTAG_, "Device does not support Bluetooth");
			errorOut("Device does not support Bluetooth");
		}
		Log.v(LOGTAG_, "Device Suports Bluetooth");
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		Log.v(LOGTAG_, "Bluetooth Enabled");
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		Log.v(LOGTAG_, "Bluetooth Enabled");
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		    	Log.v(LOGTAG_, device.getAddress().concat("<- Mac Address"));
		        if( device.getAddress().equals(RaspPiMacAddr_) ) 
		        {
	        		raspPi_ = device;
		    	// Add the name and address to an array adapter to show in a ListView
		        //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		        }
		    }
		}
		if(raspPi_ == null){
			errorOut("Could not find the MAC Address for the Raspberry Pi");
		}
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

	private void errorOut(String errorMessage){
		new AlertDialog.Builder(this)
	      .setTitle("Error")
	      .setMessage(errorMessage).show();
	}
}
