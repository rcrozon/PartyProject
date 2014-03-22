package lists;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.myparty.ConcertDetailsActivity;
import com.example.myparty.R;

import databaseHandler.DatabaseHandler;
import entities.Concert;
import entities.ConcertItem;
import entities.DateParser;
import entities.Entity;

public class ConcertList extends List {

	private Adapter adapter;

	private DatabaseHandler dataBase;

	public ConcertList(final Context context, int listType, int idCLient) {
		super(context);
		items = new ArrayList<Items>();
		
/****************** OUVERTURE BDD ***********************************/		
		
		dataBase = new DatabaseHandler(context);
		dataBase.open();

/****************** LISTE DES CONCERTS ***********************************/
		Log.i("DATE", new Date().toString());
		java.util.List<Concert> listeConcerts = dataBase.getConcerts();
		if (listeConcerts != null){
			switch(listType){
				case 0 : // All
					for (Concert concert : listeConcerts){
						ConcertItem item = new ConcertItem(this.getContext(), concert);
						items.add(item);
					}break;
				case 1 : // Next 
					for (Concert concert : listeConcerts){
						if (DateParser.isNextConcert(concert.getBeginDate())){
							ConcertItem item = new ConcertItem(this.getContext(), concert);
							items.add(item);
						}
					}break;
				case 2 : //News
					for (Concert concert : listeConcerts){
						if (DateParser.isNewConcert(concert.getBeginDate())){
							ConcertItem item = new ConcertItem(this.getContext(), concert);
							items.add(item);
						}
					}break;
				default : 
					for (Concert concert : listeConcerts){
						ConcertItem item = new ConcertItem(this.getContext(), concert);
						items.add(item);
					}break;
			}
			
		}
		
		adapter = new Adapter(items);
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
				ConcertItem selectedItem = (ConcertItem) view;
				Concert concert = selectedItem.getConcert();
				if (concert.getId()!=0){
					Intent intent = new Intent(context,
							ConcertDetailsActivity.class);

					intent.putExtra("id", concert.getId());
					intent.putExtra("idClient", concert.getId());
					context.startActivity(intent);
				}
			}
		});
		this.setBackgroundColor(getResources().getColor(R.color.black));

	}

}
