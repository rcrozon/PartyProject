package bluetooth;

import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import entities.BluetoothDeviceItem;

public class BluetoothDevices {

	private BluetoothAdapter bluetoothAdapter;
	private ArrayList<BluetoothDevice> listDevices = new ArrayList<BluetoothDevice>();
	private Context context;
	
	public BluetoothDevices(Context context){
		this.context = context;
		//register local BT adapter
	    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    //check to see if there is BT on the Android device at all
	    if (bluetoothAdapter == null){
	        int duration = Toast.LENGTH_SHORT;
	    }
	    //let's make the user enable BT if it isn't already
	    if (!bluetoothAdapter.isEnabled()) {
	    	bluetoothAdapter.enable();
        }
	    /*
	    // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.context.registerReceiver(receiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.context.registerReceiver(receiver, filter);
        */
	    //re-start discovery
	    //cancel any prior BT device discovery
	    if (bluetoothAdapter.isDiscovering()){
	        bluetoothAdapter.cancelDiscovery();
	    }
	    bluetoothAdapter.startDiscovery();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

	    for (BluetoothDevice device : pairedDevices) {
	    	listDevices.add(device);
        }
	}
	
	public ArrayList<BluetoothDevice> getBluetoothDevices(){
		return listDevices;
	}
	
	/*
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("DEVICE", "device "+  device.getName());
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                	listDevices.add(device);
                } 
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }
    };*/
}
