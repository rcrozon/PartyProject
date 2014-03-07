package databaseHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ThreadBitMap extends Thread {

	
		private Bitmap result; 
		    private String url;
		    
		    public ThreadBitMap(String url){
		    	super();
		    	this.url = url;
		    }
		    
		    public Bitmap getResult() {
		       return result;
		    }
		    
		    public void setResult(Bitmap result){
		    	this.result = result;
		    }
		    
		    public void run(){
		    	
				try {
					URL url2 = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
					connection.setDoInput(true);
					connection.connect();
					InputStream input = connection.getInputStream();
					Bitmap myBitMap = BitmapFactory.decodeStream(input);
					setResult(myBitMap);
				} catch (Exception e) {
					// TODO: handle exception
					setResult(null);
				}
	
		  
		    }
}
