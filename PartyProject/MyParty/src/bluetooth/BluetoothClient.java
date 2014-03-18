package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

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
    	
        // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
        BluetoothSocket tmp = null;
        //blueDevice = device;

        // On r�cup�re un objet BluetoothSocket gr�ce � l'objet BluetoothDevice
        try {
            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est n�cessaire c�t� serveur �galement !
        	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            Method getUuidsMethod;
			getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
			ParcelUuid[] uuids;
			uuids = (ParcelUuid[])getUuidsMethod.invoke(blueAdapter, null);
		
	    	for (ParcelUuid uuid: uuids) {
	    	    Log.i("TAG UUIDS CLIENT", "UUID: " + uuid.getUuid().toString());
	    	}
        	tmp = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        } catch (IOException e) { }
        catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        blueSocket = tmp;
    }

    
    public void run() {
        try {
            // On se connecte. Cet appel est bloquant jusqu'� la r�ussite ou la lev�e d'une erreur
            blueSocket.connect();
            write(1000);
            //manageConnectedSocket(blueSocket);
            Log.i("TAG RUN CLIENT", "connected"); 
        } catch (IOException connectException) {
            Log.i("TAG RUN CLIENT", "NOT connected");
            // Impossible de se connecter, on ferme la socket et on tue le thread
//            try {
//                blueSocket.close();
//            } catch (IOException closeException) { }
            return;
        }

        // Utilisez la connexion (dans un thread s�par�) pour faire ce que vous voulez
    }

    private void manageConnectedSocket(final BluetoothSocket blueSocket) {
        
        // Keep listening to the InputStream while connected
        try {
            // Read from the InputStream
    		tmpIn = blueSocket.getInputStream();
            tmpOut = blueSocket.getOutputStream();
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
	}

    /**
     * Write to the connected OutStream.
     * @param buffer  The bytes to write
     */
    public boolean write(int id_res) {
        try {
            byte[] buff = ByteBuffer.allocate(4).putInt(id_res).array();
            tmpOut = blueSocket.getOutputStream();
	        tmpOut.write(buff);
	        return true;
            // Share the sent message back to the UI Activity
        } catch (IOException e) {
            Log.e("TAG", "Exception during write", e);
        }
        return false;
    }

	// Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }
}
