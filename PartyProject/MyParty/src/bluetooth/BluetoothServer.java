package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

public class BluetoothServer extends Bluetooth {
    
	private BluetoothServerSocket blueServerSocket ;
    private InputStream tmpIn;
    private OutputStream tmpOut;
    //private static final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08102009c666");
	 
    public BluetoothServer() {
        // On utilise un objet temporaire qui sera assignï¿½ plus tard ï¿½ blueServerSocket car blueServerSocket est "final"
        BluetoothServerSocket tmp = null;
    	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
        	Method getUuidsMethod;
				getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
				ParcelUuid[] uuids = (ParcelUuid[])getUuidsMethod.invoke(blueAdapter, null);
			
        	for (ParcelUuid uuid: uuids) {
        	    Log.d("TAG UUIDS SERVER", "UUID: " + uuid.getUuid().toString());
        	}
        	// MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nï¿½cessaire cï¿½tï¿½ client ï¿½galement !
        	blueServerSocket = blueAdapter.listenUsingInsecureRfcommWithServiceRecord("MYPARTY", uuids[0].getUuid());
        } catch (NullPointerException e) {
			e.printStackTrace();
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
        
        Log.i("TAG SERVER", "SERVER CONSTRUCTOR");
        //blueServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket blueSocket = null;
        // On attend une erreur ou une connexion entrante
        while (true) {
            try {
            	if (blueServerSocket != null){
	            	Log.i("TAG INIT", "init connection");
	                blueSocket = blueServerSocket.accept();
	            	Log.i("TAG INIT", "connection accepté");
            	}else{
            		Log.i("TAG INIT", "connection accepté");	
            	}
            } catch (IOException e) {
	            break;
	        }
            // Si une connexion est acceptée
            if (blueSocket != null) {
                // On fait ce qu'on veut de la connexion (dans un thread sï¿½parï¿½), ï¿½ vous de la crï¿½er
                manageConnectedSocket(blueSocket);
//                try {
//					blueServerSocket.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                break;
            }
    		Log.i("TAG MANAGE SERVER", "BLUESOCKET NULL");
        }
    }

    private void manageConnectedSocket(BluetoothSocket blueSocket) {
		Log.i("TAG MANAGE SERVER", "PASSE");
		// Keep listening to the InputStream while connected
        try {
            // Read from the InputStream
    		tmpIn = blueSocket.getInputStream();
            //tmpOut = blueSocket.getOutputStream();
            Log.i("TAG", "BEGIN mConnectedThread");
        	Thread read = new Thread(new Runnable() {
				@Override
				public void run() {
					byte[] buffer = new byte[1024];
			        int bytes;
			        while (true) {
				        try {
							Log.i("TAG RECEIVED1","AVANT");
							bytes = tmpIn.read(buffer);
							Log.i("TAG RECEIVED2","ID = "+ bytes);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
				}
			});
        	read.start();
            // Send the obtained bytes to the UI Activity
        } catch (IOException e) {
            Log.e("TAG", "disconnected", e);
        }
		//TODO Recupï¿½rer infos et stockage bd
		
	}

	// On stoppe l'ï¿½coute des connexions et on tue le thread
    public void cancel() {
        try {
            blueServerSocket.close();
        } catch (IOException e) { }
    }
}