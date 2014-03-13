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
			
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				URL url2 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				//Bitmap myBitMap = BitmapFactory.decodeStream(input);
				Bitmap myBitMap = BitmapFactory.decodeStream(input, null, options);
				Bitmap test = Bitmap.createScaledBitmap(myBitMap, 400, 300, true);
				setResult(test);
				myBitMap.recycle();
				//test.recycle();
				
			
		} catch (Exception e) {
			// TODO: handle exception
			setResult(null);
		}


	}
}
