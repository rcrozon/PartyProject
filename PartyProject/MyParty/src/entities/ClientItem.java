package entities;

import lists.Items;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myparty.R;

import databaseHandler.DatabaseHandler;

public class ClientItem extends LinearLayout implements Items{ 
	
	
	private Client client ;
	public ClientItem(Context context, Client client,Concert concert){
		super(context);
		DatabaseHandler database = new DatabaseHandler(context);
		database.open();
		this.client = client;
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
		imgView.setBackgroundResource(R.drawable.ic_action_person_blue);
		imgView.setLayoutParams(llp);
		this.addView(imgView);
		
		TextView firstName = new TextView(context);
		TextView lastName = new TextView(context);
		TextView birth = new TextView(context);
		TextView email = new TextView(context);
		TextView nbTicket = new TextView(context);
		TextView login = new TextView(context);
		TextView pwd = new TextView(context);
		
		firstName.setText("First Name : "+client.getFirstName());
		lastName.setText("Last Name : "+client.getLastName());
//		birth.setText(client.getBirth());
		email.setText("Email : "+client.getEmail());
		if (concert.getId()!=0 && client.getId()!=0){
			int nb = database.getNumberResClientForOneConcert(concert, client);
			int nbScan = database.getNumberResClientForOneConcertScanned(concert, client);
			nbTicket.setText("Tickets : "+nbScan+" Scanned / "+nb);
		}
		login.setText(client.getLogin());
		pwd.setText(client.getPassword());
		
		firstName.setTextColor(getResources().getColor(R.color.white));
		lastName.setTextColor(getResources().getColor(R.color.white));
		birth.setTextColor(getResources().getColor(R.color.white));
		email.setTextColor(getResources().getColor(R.color.white));
		nbTicket.setTextColor(getResources().getColor(R.color.white));
		login.setTextColor(getResources().getColor(R.color.white));
		pwd.setTextColor(getResources().getColor(R.color.white));

		layoutClientData.addView(firstName);
		layoutClientData.addView(lastName);
		layoutClientData.addView(birth);
		layoutClientData.addView(email);
		layoutClientData.addView(nbTicket);
//		layoutClientData.addView(login);
//		layoutClientData.addView(pwd);
		
		this.addView(layoutClientData); 
	}
	
	@Override
	public String toString(){
		return client.getFirstName() + " " + client.getLastName();
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.setVisible(visible);
	}
}
