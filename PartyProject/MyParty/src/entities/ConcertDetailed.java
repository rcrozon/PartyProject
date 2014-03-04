package entities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.MapActivity;
import com.example.myparty.R;
import com.google.android.gms.internal.bu;

import databaseHandler.Tables;
import databaseHandler.ThreadBitMap;
import databaseHandler.ThreadTestServer;

public class ConcertDetailed extends RelativeLayout {

	public ConcertDetailed(final Context context, Concert concert) {
		super(context);
		if(concert != null){
			ImageView imgView = new ImageView(context);
			RelativeLayout.LayoutParams layoutParamsButtonMap = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParamsButtonMap.addRule(RelativeLayout.CENTER_HORIZONTAL);
			layoutParamsButtonMap.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layoutParamsButtonMap.setMargins(0, 80, 0, 0);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, 500);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);



			/*TODO SI HORS ligne*/
			/*imgView.setBackgroundResource(R.drawable.party2);
		imgView.setLayoutParams(layoutParams);*/


			/*********** Si le serveur est dispo **********************/
			ThreadTestServer tPing = new ThreadTestServer(context);
			tPing.start();

			try {
				tPing.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isNetworkConnected(context) && tPing.getResult()){
				ThreadBitMap t = new ThreadBitMap(Tables.IMG_PATH_SERVER + concert.getImagePath());
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imgView.setImageBitmap(t.getResult());
				imgView.setLayoutParams(layoutParams);

				Log.i("ConcertPath", Tables.IMG_PATH_SERVER + concert.getImagePath());
			}
			else{
				imgView.setBackgroundResource(R.drawable.party2);
				imgView.setLayoutParams(layoutParams);
			}

			/***********************************************************/


			LinearLayout layoutConcertData = new LinearLayout(context);
			layoutConcertData.setOrientation(LinearLayout.VERTICAL);
			RelativeLayout.LayoutParams layoutConcert = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layoutConcert.setMargins(30, 600, 30, 0);
			layoutConcertData.setLayoutParams(layoutConcert);
			TextView textTitle = new TextView(context);
			TextView textLocation = new TextView(context);
			TextView textNbSeets = new TextView(context);
			TextView textDate = new TextView(context);
			Button buttonMap = new Button(context);

			textTitle.setTextColor(getResources().getColor(R.color.white));
			textLocation.setTextColor(getResources().getColor(R.color.white));
			textNbSeets.setTextColor(getResources().getColor(R.color.white));
			textDate.setTextColor(getResources().getColor(R.color.white));
			buttonMap.setTextColor(getResources().getColor(R.color.white));

			final Concert fConcert = concert;
			textLocation.setText("Location : " + concert.getLocation());
			textNbSeets.setText("Number of seets : " + concert.getNbSeets());
			textDate.setText("Beginning : " + concert.getBeginDate().toString());
			textTitle.setText(concert.getTitle());
			buttonMap.setText("Afficher sur la carte");
			buttonMap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_map, R.color.black, R.color.black, R.color.black);
			buttonMap.setTextColor(getResources().getColor(R.color.white));
			buttonMap.setBackgroundResource(R.color.black);
			buttonMap.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) { 
					Intent intent = new Intent(context, MapActivity.class);
					intent.putExtra("address", fConcert.getLocation());
					context.startActivity(intent);
				}
			});

			layoutConcertData.addView(textTitle);
			layoutConcertData.addView(textDate);
			layoutConcertData.addView(textLocation);
			layoutConcertData.addView(textNbSeets);
			layoutConcertData.addView(buttonMap, layoutParamsButtonMap);

			this.addView(imgView);
			this.addView(layoutConcertData);
		}
		}
		
		public static boolean isNetworkConnected(Context context) {
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
			
		}
}
