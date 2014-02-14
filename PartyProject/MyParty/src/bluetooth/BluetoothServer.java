package bluetooth;

import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class BluetoothServer extends Thread {
    
	private final BluetoothServerSocket blueServerSocket;
	private final BluetoothAdapter blueAdapter = null;
	
    public BluetoothServer() {
        // On utilise un objet temporaire qui sera assigné plus tard à blueServerSocket car blueServerSocket est "final"
        BluetoothServerSocket tmp = null;
    	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
//        try {
//            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté client également !
//            tmp = blueAdapter.listenUsingRfcommWithServiceRecord(NOM, MON_UUID);
//        } catch (IOException e) { }
        blueServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket blueSocket = null;
        // On attend une erreur ou une connexion entrante
        while (true) {
            try {
                blueSocket = blueServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // Si une connexion est acceptée
            if (blueSocket != null) {
                // On fait ce qu'on veut de la connexion (dans un thread séparé), à vous de la créer
                //manageConnectedSocket(blueSocket);
                try {
					blueServerSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            }
        }
    }

    // On stoppe l'écoute des connexions et on tue le thread
    public void cancel() {
        try {
            blueServerSocket.close();
        } catch (IOException e) { }
    }
}