package lists;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.myparty.ConcertDetailsActivity;
import com.example.myparty.R;

import databaseHandler.DatabaseHandler;
import entities.Concert;
import entities.ConcertItem;
import entities.Entity;
import entities.Reservation;
import entities.ReservationItem;
import entities.Ticket;
import entities.TicketItem;

public class TicketsList extends List {

	private Adapter adapter;

	private DatabaseHandler dataBase;

	public TicketsList(final Context context, ArrayList<Entity> list) {
		super(context);
		items = new ArrayList<Items>();

//		dataBase = new DatabaseHandler(context);
//		dataBase.open();

		TicketItem t1 = new TicketItem(context, new Ticket(0, 3, "Michael Jackson"));
		TicketItem t2 = new TicketItem(context, new Ticket(0, 3, "Michael Jackson"));
		TicketItem t3 = new TicketItem(context, new Ticket(0, 3, "Michael Jackson"));
		TicketItem t4 = new TicketItem(context, new Ticket(0, 3, "Michael Jackson"));
		TicketItem t5 = new TicketItem(context, new Ticket(0, 3, "Michael Jackson"));
		items.add(t1);
		items.add(t2);
		items.add(t3);
		items.add(t4);
		items.add(t5);
		
		adapter = new Adapter(items);
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				Intent intent = new Intent(context,
						ConcertDetailsActivity.class);
				context.startActivity(intent);
			}
		});
		this.setBackgroundColor(getResources().getColor(R.color.black));
	}

}
