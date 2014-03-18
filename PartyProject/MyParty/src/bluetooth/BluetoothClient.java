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

        // On récupère un objet BluetoothSocket grâce à l'objet BluetoothDevice
        try {
            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est nécessaire côté serveur également !
        	BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            Method getUuidsMethod;
			getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
			ParcelUuid[] uuids;
			uuids = (ParcelUuid[])getUuidsMethod.invoke(blueAdapter, null);
		
	    	for (ParcelUuid uuid: uuids) {
	    	    Log.d("TAG UUIDS CLIENT", "UUID: " + uuid.getUuid().toString());
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
        manageConnectedSocket(blueSocket);
    }

    private void manageConnectedSocket(final BluetoothSocket blueSocket) {
//		Thread t = new Thread(new Runnable() {
//			@Override
//			public void run() {
				try {
					tmpIn = blueSocket.getInputStream();
			        tmpOut = blueSocket.getOutputStream();
			        Log.i("TAG", "BEGIN mConnectedThread");
			        byte[] buffer = new byte[1024];
			        
			        int bytes;
			        int id_res = 0;
	                byte[] buff = ByteBuffer.allocate(4).putInt(id_res).array();
	                write(buff);
	                
			        // Keep listening to the InputStream while connected
//			        while (true) {
			            //try {
			                // Read from the InputStream
			            	//bytes = tmpIn.read(buffer);
			                
			                // Send the obtained bytes to the UI Activity
			                //Log.i("TAG RECEIVED",""+ bytes);
//		            	} catch (IOException e) {
//			                Log.e("TAG", "disconnected", e);
//			                break; 
//			            }
//			        }
				} catch (IOException e) {
		            Log.e("TAG", "temp sockets not created", e);
		        }
//			}
//		});
//		t.start();
		
	}

    /**
     * Write to the connected OutStream.
     * @param buffer  The bytes to write
     */
    public void write(byte[] buffer) {
        try {
            Log.e("TAG WRITE ", "WRITE" + buffer.toString());
        	tmpOut.write(buffer);
        	int id_res = 2;
            // Share the sent message back to the UI Activity
        } catch (IOException e) {
            Log.e("TAG", "Exception during write", e);
        }
    }

	// Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }
}
