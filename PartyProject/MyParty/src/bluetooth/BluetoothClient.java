package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

public class BluetoothClient extends Bluetooth {
	
    private BluetoothSocket blueSocket ;
    private final BluetoothDevice blueDevice ;
    private InputStream tmpIn;
    private OutputStream tmpOut;
    private Handler mHandler = new Handler();
    
//	private static final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08102009c666");
	
    public BluetoothClient(BluetoothDevice device, Context context) {
    	
    	blueDevice = device;
    	BluetoothAdapter blueAdapter;
        // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
        BluetoothSocket tmp = null;
        //blueDevice = device;

        // On r�cup�re un objet BluetoothSocket gr�ce � l'objet BluetoothDevice
        try {
            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est n�cessaire c�t� serveur �galement !
        	blueAdapter = BluetoothAdapter.getDefaultAdapter();
//            Method getUuidsMethod;
//			getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
//			ParcelUuid[] uuids;
//			uuids = (ParcelUuid[])getUuidsMethod.invoke(blueAdapter, null);
//		
//	    	for (ParcelUuid uuid: uuids) {
//	    	    Log.i("TAG UUIDS CLIENT", "UUID: " + uuid.getUuid().toString());
//	    	}
        	String s = "0000111f-0000-1000-8000-00805f9b34fb";
        	blueSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(s));
        } catch (IOException e) { }
         catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    public void run() {
    	while(true){
	        try {
	            // On se connecte. Cet appel est bloquant jusqu'� la r�ussite ou la lev�e d'une erreur
	            blueSocket.connect();
	            Log.i("TAG RUN CLIENT", "connected"); 
	            manageConnectedSocket(blueSocket);
	        } catch (IOException e) {
	            Log.i("TAG RUN CLIENT", "NOT connected");
				e.printStackTrace();
	            // Impossible de se connecter, on ferme la socket et on tue le thread
	            cancel();
	        }
    	}
        // Utilisez la connexion (dans un thread s�par�) pour faire ce que vous voulez
    }

    /**
     * Write to the connected OutStream.
     * @param buffer  The bytes to write
     */
/*
    public void write(final int id_res) {
    	try {
			tmpOut = blueSocket.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
		            byte[] buff = ByteBuffer.allocate(4).putInt(id_res).array();
		            blueSocket.connect();
		            Log.i("TAG RUN CLIENT", "connected"); 
		            tmpOut.write(buff);
			        // Share the sent message back to the UI Activity
		        } catch (IOException e) {
		            Log.e("TAG", "Exception during write", e);
		            e.printStackTrace();
		        }
				cancel();
			}
		});
    	t.start();

    }*/


    private void manageConnectedSocket(BluetoothSocket blueSocket) {
		Log.i("TAG MANAGE SERVER", "PASSE");
		// Keep listening to the InputStream while connected
        try {
            // Read from the InputStream
    		tmpIn = blueSocket.getInputStream();
            //tmpOut = blueSocket.getOutputStream();
            Log.i("TAG", "BEGIN mConnectedThread");
//        	Thread read = new Thread(new Runnable() {
//				@Override
//				public void run() {
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
//				}
//			});
//        	read.start();
            // Send the obtained bytes to the UI Activity
        } catch (IOException e) {
            Log.e("TAG", "disconnected", e);
        }
		//TODO Recup�rer infos et stockage bd
		
	}
	// Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }
}
