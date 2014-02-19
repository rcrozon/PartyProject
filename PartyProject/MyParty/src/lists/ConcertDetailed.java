package lists;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myparty.R;

import entities.Concert;

public class ConcertDetailed extends RelativeLayout { 
	
	public ConcertDetailed(Context context, Concert concert){
		super(context);
		ImageView imgView = new ImageView(context);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
																				   500);
		imgView.setBackgroundResource(R.drawable.party2);
		imgView.setLayoutParams(layoutParams);
		
		LinearLayout layoutConcertData = new LinearLayout(context);
		layoutConcertData.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams layoutConcert = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutConcert.setMargins(30, 510, 30, 0);
		layoutConcertData.setLayoutParams(layoutConcert);
		TextView textTitle = new TextView(context);
		TextView textLocation = new TextView(context);
		TextView textPrice = new TextView(context);
		TextView textNbSeets = new TextView(context);
		TextView textDate = new TextView(context);
		
		textTitle.setTextColor(getResources().getColor(R.color.white));
		textLocation.setTextColor(getResources().getColor(R.color.white));
		textPrice.setTextColor(getResources().getColor(R.color.white));
		textNbSeets.setTextColor(getResources().getColor(R.color.white));
		textDate.setTextColor(getResources().getColor(R.color.white));
  
	    textLocation.setText("Location : " + concert.getLocation());
	    textPrice.setText("Price : " + concert.getPrice());
	    textNbSeets.setText("Number of seets : " + concert.getNbSeets());
	    textDate.setText("Beginning : " + concert.getBeginDate().toString());
	    textTitle.setText(concert.getTitle());
		
		layoutConcertData.addView(textTitle);
		layoutConcertData.addView(textDate);
		layoutConcertData.addView(textLocation);
		layoutConcertData.addView(textNbSeets);
		layoutConcertData.addView(textPrice);
		
		this.addView(imgView);
		this.addView(layoutConcertData); 
	}
}
