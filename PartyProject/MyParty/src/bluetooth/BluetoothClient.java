package bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.telephony.TelephonyManager;

public class BluetoothClient extends Thread {
	
    private final BluetoothSocket blueSocket = null;
    private final BluetoothDevice blueDevice = null;
    private final BluetoothAdapter blueAdapter = null;
    
    public BluetoothClient(BluetoothDevice device, Context context) {
    
    	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (blueAdapter == null) {
    	    // Le terminal ne possède pas le Bluetooth
    	}
    	if (!blueAdapter.isEnabled()) {
    	    blueAdapter.enable();
        // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
	        BluetoothSocket tmp = null;
	        //blueDevice = device;
	
	        // On récupère un objet BluetoothSocket grâce à l'objet BluetoothDevice
//	        try {
//	            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté serveur également !
//	        	TelephonyManager tManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//	        	String uuid = tManager.getDeviceId();
//	        	tmp = device.createRfcommSocketToServiceRecord(uuid);
//	        } catch (IOException e) { }
//	        blueSocket = tmp;
    	}
    }

    public void run() {
        // On annule la découverte des périphériques (inutile puisqu'on est en train d'essayer de se connecter)
        blueAdapter.cancelDiscovery();

        try {
            // On se connecte. Cet appel est bloquant jusqu'à la réussite ou la levée d'une erreur
            blueSocket.connect();
        } catch (IOException connectException) {
            // Impossible de se connecter, on ferme la socket et on tue le thread
            try {
                blueSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Utilisez la connexion (dans un thread séparé) pour faire ce que vous voulez
        //manageConnectedSocket(blueSocket);
    }

    // Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }
}
