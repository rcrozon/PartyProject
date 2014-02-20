package com.example.myparty;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import bluetooth.BluetoothServer;
import entities.BluetoothDeviceItem;

public class BluetoothActivity extends Activity implements OnClickListener {
	
    private BluetoothServer bluetoothServer ;
    private LinearLayout layoutDevices ;
    private BroadcastReceiver receiver;
    private ProgressBar progressBar ;
	private BluetoothAdapter bluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		layoutDevices = (LinearLayout)findViewById(R.id.layoutDevicesList);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
	
      //register local BT adapter
	    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    //check to see if there is BT on the Android device at all
	    if (bluetoothAdapter == null){
	        int duration = Toast.LENGTH_SHORT;
	        Toast.makeText(this, "No Bluetooth on this handset", duration).show();
	    }
	    //let's make the user enable BT if it isn't already
	    if (!bluetoothAdapter.isEnabled()) {
	    	bluetoothAdapter.enable();
        }
	    
	    // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
	    //re-start discovery
	    //cancel any prior BT device discovery
	    if (bluetoothAdapter.isDiscovering()){
	        bluetoothAdapter.cancelDiscovery();
	    }
	    bluetoothAdapter.startDiscovery();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

	    for (BluetoothDevice device : pairedDevices) {
	    	layoutDevices.addView(new BluetoothDeviceItem(this, device));
        }
	}

	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                progressBar.setVisibility(View.VISIBLE);
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                	layoutDevices.addView(new BluetoothDeviceItem(context, device));
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    };
	
	
	//////////////////////////////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}  
	@Override
	public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
	}
	 
	@Override
	public void onClick(View v) {
		Button b = (Button)v;
	}
}

