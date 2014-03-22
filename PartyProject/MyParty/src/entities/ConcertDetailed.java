package entities;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.MapActivity;
import com.example.myparty.R;

import databaseHandler.DatabaseHandler;
import databaseHandler.Tables;

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
			File ftest = new File(Environment.getExternalStorageDirectory() +
					File.separator +  Tables.PATH_REP_IMG+"/detail"+concert.getId()+".png");
			if (ftest.exists()){
			
				BitmapDrawable bm = new BitmapDrawable(getResources(), Environment.getExternalStorageDirectory() +
						File.separator + Tables.PATH_REP_IMG +"/detail"+concert.getId()+".png");
				imgView.setBackground(bm);
				imgView.setLayoutParams(layoutParams);
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
			TextView textArtist = new TextView(context);
			Button buttonMap = new Button(context);

			textTitle.setTextColor(getResources().getColor(R.color.white));
			textLocation.setTextColor(getResources().getColor(R.color.white));
			textNbSeets.setTextColor(getResources().getColor(R.color.white));
			textDate.setTextColor(getResources().getColor(R.color.white));
			textArtist.setTextColor(getResources().getColor(R.color.white));
			buttonMap.setTextColor(getResources().getColor(R.color.white));

			final Concert fConcert = concert;
			textLocation.setText("Location : " + concert.getLocation());
			textNbSeets.setText("Number of seets taken : "+
			DatabaseHandler.getNumberResForOneConcert(concert.getId()) +" / " 
					+concert.getNbSeets());
			
			if(DatabaseHandler.getNumberResForOneConcert(concert.getId()) == 
					concert.getNbSeets())
				textNbSeets.setText(textNbSeets.getText() + " (FULL)");
			
			String all[] = concert.getBeginDate().split(" ");
			String date[] = all[0].split("-");
			String hour[] = all[1].split(":");
			

			textDate.setText("Beginning : " +  date[2] +
					" "+ getMonthLetter(Integer.parseInt(date[1])) + 
					" "+ date[0] +" ("+ hour[0]+"H"+hour[1] +")");
			
			textTitle.setText("Title : "+ concert.getTitle());
			
			List<String> listArt = DatabaseHandler.getArtistsFromConcert(concert.getId());
			String artist = "";
			int i=0;
			for (String s : listArt){
				if (i!=0)
					artist+= " / ";
				artist += s;
				i++;
			}
			textArtist.setText("Artists : "+artist);
			
			
			
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
			layoutConcertData.addView(textArtist);
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
		
		public String getMonthLetter(int indice){
			switch (indice) {
			case 1:
				return "Jan";
			case 2:
				return "Feb";
			case 3:
				return "Mar";
			case 4:
				return "Apr";
			case 5:
				return "May";
			case 6:
				return "Jun";
			case 7:
				return "Jul";
			case 8:
				return "Aou";
			case 9:
				return "Sep";
			case 10:
				return "Oct";
			case 11:
				return "Nov";
			case 12:
				return "Dec";
				
			default:
				return "null";
			}
		}
}
