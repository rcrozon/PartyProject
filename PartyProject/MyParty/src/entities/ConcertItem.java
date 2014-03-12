package entities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Environment;
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

		/*********** Si l'image existe on la met sinon, image par défaut **********************/
		File ftest = new File(Environment.getExternalStorageDirectory() +
				File.separator + Tables.PATH_REP_IMG +"/detail"+concert.getId()+".png");
		if (ftest.exists()){
		
			BitmapDrawable bm = new BitmapDrawable(getResources(), Environment.getExternalStorageDirectory() +
					File.separator +  Tables.PATH_REP_IMG+"/detail"+concert.getId()+".png");
			imgView.setBackground(bm);
			imgView.setLayoutParams(llp);
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
