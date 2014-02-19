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

public class ReservationsList extends List {

	private Adapter adapter;

	private DatabaseHandler dataBase;

	public ReservationsList(final Context context, ArrayList<Entity> list) {
		super(context);
		items = new ArrayList<Items>();

//		dataBase = new DatabaseHandler(context);
//		dataBase.open();

		ReservationItem r1 = new ReservationItem(context, new Reservation(0, 3, "Michael Jackson",1));
		ReservationItem r2 = new ReservationItem(context, new Reservation(1, 5, "AC/DC",2));
		ReservationItem r3 = new ReservationItem(context, new Reservation(2, 1, "Queen",3));
		ReservationItem r4 = new ReservationItem(context, new Reservation(3, 8, "Dire Straits",4));
		ReservationItem r5 = new ReservationItem(context, new Reservation(4, 9, "Boston",5));
		items.add(r1);
		items.add(r2);
		items.add(r3);
		items.add(r4);
		items.add(r5);
		
		adapter = new Adapter(items);
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				
				ReservationItem selectedItem = (ReservationItem) view;
				Reservation res = selectedItem.getReservation();
				
				Intent intent = new Intent(context,
						ConcertDetailsActivity.class);
				intent.putExtra("id", res.getIdConcert());
				context.startActivity(intent);
			}
		});
		this.setBackgroundColor(getResources().getColor(R.color.black));
	}

}
