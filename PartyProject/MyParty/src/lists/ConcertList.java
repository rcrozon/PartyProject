package lists;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.myparty.ConcertDetailsActivity;
import com.example.myparty.R;

import concert.Concert;
import databaseHandler.DatabaseHandler;

public class ConcertList extends List {

	private Adapter adapter;

	private DatabaseHandler dataBase;

	public ConcertList(final Context context) {
		super(context);
		items = new ArrayList<Items>();

		dataBase = new DatabaseHandler(context);
		dataBase.open();
		Concert c1 = new Concert(1, "", "Michael Jackson", "11/12/14",
				"11/12/14", "Lyon", 10.0, 200, false);
		Concert c2 = new Concert(2, "", "Edith Piaf", "11/12/14", "11/12/14",
				"Paris", 25.0, 500, false);
		Concert c3 = new Concert(3, "", "Balavoine", "11/12/14", "11/12/14",
				"Grenoble", 30.0, 500, false);
		Concert c4 = new Concert(4, "", "Goldman", "11/12/14", "11/12/14",
				"Londres", 40.0, 500, false);
		Concert c5 = new Concert(5, "", "Queen", "11/12/14", "11/12/14",
				"La Rochelle", 12.0, 500, false);
		Concert c6 = new Concert(6, "", "AC/DC", "11/12/14", "11/12/14",
				"Poitiers", 25.0, 500, false);
		Concert c7 = new Concert(7, "", "Dire Straits", "11/12/14", "11/12/14",
				"Londres", 40.0, 500, false);
		Concert c8 = new Concert(8, "", "Boston", "11/12/14", "11/12/14",
				"La Rochelle", 12.0, 500, false);
		Concert c9 = new Concert(9, "", "The Beatles", "11/12/14", "11/12/14",
				"Poitiers", 25.0, 500, false);
		/* TODO A decommenter pour utiliser la bdd */
		/*
		 * ConcertItem i1 = new
		 * ConcertItem(this.getContext(),dataBase.getConcertWithId(1) );
		 * ConcertItem i2 = new ConcertItem(this.getContext(),
		 * dataBase.getConcertWithId(2));
		 */
		ConcertItem i1 = new ConcertItem(this.getContext(), c1);
		ConcertItem i2 = new ConcertItem(this.getContext(), c2);
		ConcertItem i3 = new ConcertItem(this.getContext(), c3);
		ConcertItem i4 = new ConcertItem(this.getContext(), c4);
		ConcertItem i5 = new ConcertItem(this.getContext(), c5);
		ConcertItem i6 = new ConcertItem(this.getContext(), c6);
		ConcertItem i7 = new ConcertItem(this.getContext(), c7);
		ConcertItem i8 = new ConcertItem(this.getContext(), c8);
		ConcertItem i9 = new ConcertItem(this.getContext(), c9);
		items.add(i1);
		items.add(i2);
		items.add(i3);
		items.add(i4);
		items.add(i5);
		items.add(i6);
		items.add(i7);
		items.add(i8);
		items.add(i9);

		adapter = new Adapter(items);
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long id) {
				// Toast.makeText(getBaseContext(), item,
				// Toast.LENGTH_LONG).show();

				ConcertItem selectedItem = (ConcertItem) view;
				Concert concert = selectedItem.getConcert();

				Intent intent = new Intent(context,
						ConcertDetailsActivity.class);
				intent.putExtra("id", concert.getId());
				intent.putExtra("imgPath", concert.getImagePath());
				intent.putExtra("title", concert.getTitle());
				// intent.putExtra("beginDate", concert.getBeginDate());
				// intent.putExtra("endDate", concert.getEndDate());
				intent.putExtra("location", concert.getLocation());
				intent.putExtra("price", concert.getPrice());
				intent.putExtra("nbSeets", concert.getNbSeets());
				// intent.putExtra("full", concert.getFull());

				context.startActivity(intent);
			}
		});
		this.setBackgroundColor(getResources().getColor(R.color.black));

	}

}
