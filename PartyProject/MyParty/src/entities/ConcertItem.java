package entities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter.BigDecimalLayoutForm;

import lists.Items;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.text.GetChars;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myparty.R;

import databaseHandler.Tables;
import databaseHandler.ThreadBitMap;
import databaseHandler.ThreadTestServer;

public class ConcertItem extends LinearLayout implements Items{
	
	private Concert concert;
	
	public ConcertItem(Context context, Concert concert){
		super(context);
		this.concert = concert;
		this.setBackgroundResource(R.drawable.list_border);
		this.setOrientation(HORIZONTAL);
		
		LinearLayout layoutConcertData = new LinearLayout(context);
		layoutConcertData.setOrientation(VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT-50, 
																			   LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 0, 20, 0);
		layoutConcertData.setLayoutParams(layoutParams);
		ImageView imgView = new ImageView(context);
		LayoutParams llp = new LayoutParams(140, 250, Gravity.CENTER_HORIZONTAL); 
		
		/*TODO Si pas de connexion*/
		//imgView.setBackgroundResource(R.drawable.party2);
		//imgView.setLayoutParams(llp);
		
		/*********** Si le serveur est dispo **********************/
		ThreadTestServer tPing = new ThreadTestServer(context);
		tPing.start();
		
		try {
			tPing.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (isNetworkConnected(context) && tPing.getResult()){
			ThreadBitMap t = new ThreadBitMap(Tables.IMG_PATH_SERVER + concert.getImagePath(),0);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imgView.setImageBitmap(t.getResult());
			imgView.setLayoutParams(llp);

			Log.i("ConcertPath", Tables.IMG_PATH_SERVER + concert.getImagePath());
		}
		else{
			imgView.setBackgroundResource(R.drawable.party2);
			imgView.setLayoutParams(llp);
		}
		
		/***********************************************************/
		
		this.addView(imgView);
		TextView title = new TextView(context);
		//TextView price = new TextView(context);
		TextView location = new TextView(context);
		TextView begin = new TextView(context);
		TextView end = new TextView(context);
		
		title.setText(concert.getTitle());
		begin.setText(""+concert.getBeginDate());
		end.setText(""+concert.getEndDate());
		location.setText("Location : " + concert.getLocation());
		
		title.setTextColor(getResources().getColor(R.color.white));
		location.setTextColor(getResources().getColor(R.color.white));
		//price.setTextColor(getResources().getColor(R.color.white));
		end.setTextColor(getResources().getColor(R.color.white));
		begin.setTextColor(getResources().getColor(R.color.white));
		
		layoutConcertData.addView(title);
		layoutConcertData.addView(begin);
		layoutConcertData.addView(end);
		//layoutConcertData.addView(price);
		layoutConcertData.addView(location);
		
		//this.addView(imgView);
		this.addView(layoutConcertData);
		
		
	}
	
	public Concert getConcert(){
		return this.concert;
	}

	@Override
	public String toString(){
		return concert.getTitle();
	}

	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
	
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
		
	}
	
	

}
