package bluetooth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class BluetoothServer extends Bluetooth {
    
	private final BluetoothServerSocket blueServerSocket;
	//private static final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08102009c666");
	 
    public BluetoothServer() {
        // On utilise un objet temporaire qui sera assigné plus tard à blueServerSocket car blueServerSocket est "final"
        BluetoothServerSocket tmp = null;
    	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
        	Method getUuidsMethod;
				getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
				ParcelUuid[] uuids = (ParcelUuid[])getUuidsMethod.invoke(blueAdapter, null);
			
        	for (ParcelUuid uuid: uuids) {
        	    Log.d("TAG UUIDS SERVER", "UUID: " + uuid.getUuid().toString());
        	}
        	// MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté client également !
            tmp = blueAdapter.listenUsingRfcommWithServiceRecord("MYPARTY", uuids[0].getUuid());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
        } catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
        blueServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket blueSocket = null;
        // On attend une erreur ou une connexion entrante
        while (true) {
            try {
            	Log.i("INIT", "init connection");
                blueSocket = blueServerSocket.accept();
            	Log.i("INIT", "connection accepté");
            } catch (IOException e) {
                break;
            }
            // Si une connexion est acceptée
            if (blueSocket != null) {
                // On fait ce qu'on veut de la connexion (dans un thread séparé), à vous de la créer
                manageConnectedSocket(blueSocket);
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

    private void manageConnectedSocket(BluetoothSocket blueSocket) {
		Log.i("TAG MANAGE SERVER", "PASSE");
		
	}

	// On stoppe l'écoute des connexions et on tue le thread
    public void cancel() {
        try {
            blueServerSocket.close();
        } catch (IOException e) { }
    }
}