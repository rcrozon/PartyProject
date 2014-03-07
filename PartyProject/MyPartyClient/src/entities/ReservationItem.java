package entities;

import lists.Items;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myparty.R;

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
		imgView.setBackgroundResource(R.drawable.party2);
		imgView.setLayoutParams(llp);
		this.addView(imgView);
		
		TextView concert = new TextView(context);
		TextView nbSeets = new TextView(context);
		
		concert.setText(reservation.getConcertName());
		nbSeets.setText("Number of seets : " + reservation.getNbSeets());
		
		concert.setTextColor(getResources().getColor(R.color.white));
		nbSeets.setTextColor(getResources().getColor(R.color.white));
		
		layoutClientData.addView(concert);
		layoutClientData.addView(nbSeets);
			
		this.addView(layoutClientData); 
	}
	
	public Reservation getReservation(){
		return reservation;
	}
	
	@Override
	public String toString(){
		return reservation.getConcertName() + "  " + reservation.getNbSeets();
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
}
