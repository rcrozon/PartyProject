package lists;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.myparty.R;
import com.example.myparty.TicketsActivity;

import databaseHandler.DatabaseHandler;
import entities.Reservation;
import entities.ReservationItem;
import entities.Ticket;

public class ReservationsList extends List {

	private Adapter adapter;
	private DatabaseHandler dataBase;

	public ReservationsList(final Context context, final int idClient) {
		super(context);
		items = new ArrayList<Items>();

/****************** OUVERTURE BDD ***********************************/		
		
		dataBase = new DatabaseHandler(context);
		dataBase.open();

/****************** LISTE DES RESERVATIONS ***********************************/
		ArrayList<Ticket> listeTicket = dataBase.getTicketClient(idClient);
		int cpt = 0;
		if (listeTicket != null){
			int idConcert = listeTicket.get(0).getIdConcert();
			int i = 0;
			while(i < listeTicket.size()){
				if (listeTicket.get(i).getIdConcert() == idConcert){
					cpt++;
					i++;
				}else{
					Reservation res = new Reservation(listeTicket.get(i).getConcert(), cpt);
					items.add(new ReservationItem(context, res));	
					idConcert = listeTicket.get(i).getIdConcert();
					cpt = 0;
				}
			}
		}
		
		adapter = new Adapter(items);
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				
				ReservationItem selectedItem = (ReservationItem) view;
				Reservation res = selectedItem.getReservation();
				
				Intent intent = new Intent(context,
						TicketsActivity.class);
				intent.putExtra("idConcert", res.getConcert().getId());
				intent.putExtra("idClient", idClient);
				context.startActivity(intent);
			}
		});
		this.setBackgroundColor(getResources().getColor(R.color.black));
	}

}
