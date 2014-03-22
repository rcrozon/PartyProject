package entities;

import java.io.File;

import lists.Items;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myparty.R;

import databaseHandler.Tables;

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
		layoutParams.weight = 4;
		layoutConcertData.setLayoutParams(layoutParams);
		ImageView imgView = new ImageView(context);
		LayoutParams llp = new LayoutParams(300, 250, Gravity.CENTER_HORIZONTAL); 

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

		title.setText("Title : "+concert.getTitle());
		
		String all[] = concert.getBeginDate().split(" ");
		String date[] = all[0].split("-");
		String hour[] = all[1].split(":");
		begin.setText("Start : "+ date[2] +
				" "+ getMonthLetter(Integer.parseInt(date[1])) + 
				" "+ date[0] +" ("+ hour[0]+"H"+hour[1] +")");
		
		
		String allEnd[] = concert.getEndDate().split(" ");
		String dateEnd[] = allEnd[0].split("-");
		String hourEnd[] = allEnd[1].split(":");
		end.setText("End : "+ dateEnd[2] +
				" "+ getMonthLetter(Integer.parseInt(dateEnd[1])) + 
				" "+ dateEnd[0] +" ("+ hourEnd[0]+"H"+hourEnd[1] +")");
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
