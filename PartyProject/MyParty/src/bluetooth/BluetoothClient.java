package bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BluetoothClient extends Bluetooth {
	
    private BluetoothSocket blueSocket ;
    private final BluetoothDevice blueDevice ;
//	private static final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08102009c666");
	
    public BluetoothClient(BluetoothDevice device, Context context) {
    	
    	blueDevice = device;
    	
        // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
        BluetoothSocket tmp = null;
        //blueDevice = device;

        // On récupère un objet BluetoothSocket grâce à l'objet BluetoothDevice
        try {
            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté serveur également !
        	tmp = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        } catch (IOException e) { }
        blueSocket = tmp;
    }

    
    public void run() {
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
