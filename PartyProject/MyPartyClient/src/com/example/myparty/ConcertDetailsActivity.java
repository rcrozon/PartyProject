package com.example.myparty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.LinearLayout;
import databaseHandler.DatabaseHandler;
import entities.Concert;
import entities.ConcertDetailed;

public class ConcertDetailsActivity extends Activity implements OnMenuItemClickListener {

	private MenuItem decoItem;
	private DatabaseHandler dataBase;
	private LinearLayout layoutDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_details);
		layoutDetails = (LinearLayout)findViewById(R.id.layoutDetails);

		/****************** OUVERTURE BDD ***********************************/

		this.dataBase = new DatabaseHandler(this);
		this.dataBase.open();

		/****************** RECUPERATION DE L'ID DU CONCERT *****************/

		Bundle b = getIntent().getExtras();
		Concert concert = null;
		
        concert = dataBase.getConcertWithId(b.getInt("id"));

		this.layoutDetails.addView(new ConcertDetailed(this, concert));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		this.decoItem = menu.findItem(R.id.menu_deconect);
		// decoItem.setIcon(R.drawable.logout);
		this.decoItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent;
		intent = new Intent(this, ConnectionActivity.class);
		this.startActivity(intent);
		return false;
	}

}