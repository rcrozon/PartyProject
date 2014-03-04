package databaseHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ThreadBitMap extends Thread {


	private Bitmap result; 
	private String url;
	int size;//0 correspond à une petite image; 1 : correspond grande image

	public ThreadBitMap(String url,int size){
		super();
		this.url = url;
		this.size=size;
	}

	public Bitmap getResult() {
		return result;
	}

	public void setResult(Bitmap result){
		this.result = result;
	}

	public void run(){

		try {
			if (size==0){
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				URL url2 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				//Bitmap myBitMap = BitmapFactory.decodeStream(input);
				Bitmap myBitMap = BitmapFactory.decodeStream(input, null, options);
				Bitmap test = Bitmap.createScaledBitmap(myBitMap, 200, 200, true);
				setResult(test);
				myBitMap.recycle();
				//test.recycle();
			}
			else{
				URL url2 = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitMap = BitmapFactory.decodeStream(input);
				setResult(myBitMap);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			setResult(null);
		}


	}
}
