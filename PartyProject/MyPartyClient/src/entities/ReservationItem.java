package entities;

import java.io.File;

import lists.Items;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myparty.R;

import databaseHandler.Tables;

public class ReservationItem extends LinearLayout implements Items{ 
	
	private Reservation reservation ;
	
	public ReservationItem(Context context, Reservation reservation){
		super(context);
		this.reservation = reservation;
		this.setBackgroundResource(R.drawable.list_border);
		this.setOrientation(HORIZONTAL);
		LinearLayout layoutClientData = new LinearLayout(context);
		layoutClientData.setOrientation(VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				   LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 0, 20, 0); 
		layoutClientData.setLayoutParams(layoutParams);  
		layoutParams.weight = 1;
		ImageView imgView = new ImageView(context);
		LayoutParams llp = new LayoutParams(400, 300, Gravity.CENTER_HORIZONTAL); 
		layoutParams.weight = 4;
		
		/*********** Si l'image existe on la met sinon, image par défaut **********************/
		File ftest = new File(Environment.getExternalStorageDirectory() +
				File.separator + Tables.PATH_REP_IMG +"/detail" + reservation.getConcert().getId()+".png");
		if (ftest.exists()){
		
			BitmapDrawable bm = new BitmapDrawable(getResources(), Environment.getExternalStorageDirectory() +
					File.separator +  Tables.PATH_REP_IMG+"/detail"+reservation.getConcert().getId()+".png");
			imgView.setBackground(bm);
			imgView.setLayoutParams(llp);
		}
		else{
			imgView.setBackgroundResource(R.drawable.party2);
			imgView.setLayoutParams(llp);
		}
		imgView.setLayoutParams(llp);
		this.addView(imgView);
		
		TextView concert = new TextView(context);
		TextView location = new TextView(context);
		TextView date = new TextView(context);
		TextView nbSeatsBought = new TextView(context);
		
		concert.setText(reservation.getConcert().getTitle());
		nbSeatsBought.setText("Number of seats reserved : " + reservation.getNbTickets());
		location.setText("Location : " + reservation.getConcert().getLocation());
		date.setText("Date : " + reservation.getConcert().getBeginDate());
		
		concert.setTextColor(getResources().getColor(R.color.white));
		nbSeatsBought.setTextColor(getResources().getColor(R.color.white));
		date.setTextColor(getResources().getColor(R.color.white));
		location.setTextColor(getResources().getColor(R.color.white));
		
		layoutClientData.addView(concert);
		layoutClientData.addView(location);
		layoutClientData.addView(date);
		layoutClientData.addView(nbSeatsBought);
			
		this.addView(layoutClientData); 
	}
	
	public Reservation getReservation(){
		return reservation;
	}
	
	@Override
	public String toString(){
		return reservation.getConcert().getTitle() + "  " + reservation.getConcert().getNbSeets();
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
}
