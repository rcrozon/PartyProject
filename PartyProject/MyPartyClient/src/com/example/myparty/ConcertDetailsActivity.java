package com.example.myparty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import databaseHandler.DatabaseHandler;
import entities.Concert;
import entities.ConcertDetailed;

public class ConcertDetailsActivity extends Activity implements OnMenuItemClickListener {

	private MenuItem decoItem;
	private MenuItem updateItem;
	private MenuItem connectedItem;
	private DatabaseHandler dataBase;
	private LinearLayout layoutDetails;
	private Context context;
	private ProgressBar progressBar;
	private int idClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_concert_details);
		layoutDetails = (LinearLayout)findViewById(R.id.layoutDetails);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		/****************** OUVERTURE BDD ***********************************/

		this.dataBase = new DatabaseHandler(this);
		this.dataBase.open();

		/****************** RECUPERATION DE L'ID DU CONCERT *****************/

		Bundle b = getIntent().getExtras();
		idClient = b.getInt("idClient");
		Concert concert = DatabaseHandler.getConcertWithId(b.getInt("id"));

		this.layoutDetails.addView(new ConcertDetailed(this, concert));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		this.decoItem = menu.findItem(R.id.menu_deconnect);
		this.updateItem = menu.findItem(R.id.update); 
		this.connectedItem = menu.findItem(R.id.menu_refresh); 
		this.updateItem.setOnMenuItemClickListener(this);
		if (DatabaseHandler.isNetworkConnected(this)){
			connectedItem.setIcon(R.drawable.ic_action_location_found_green);
		}
		//decoItem.setIcon(R.drawable.logout);
		this.decoItem.setOnMenuItemClickListener(this);
		
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = null;
		if (item == decoItem) {
			intent = new Intent(this, ConnectionActivity.class);
			this.startActivity(intent);

		}else if(item == updateItem){
			loadDatabase();
			//			if(DatabaseHandler.updateAllTables(this)){
			//				intent = new Intent(this, ConcertActivity.class);
			//				this.startActivity(intent);
			//			}
		}
		return false;
	}


	private void loadDatabase(){
		progressBar.setVisibility(View.VISIBLE);
		//layoutDetails.setVisibility(View.GONE);
		new Thread(new Runnable() { 
			@Override
			public void run() {
				if (DatabaseHandler.updateAllTables(context, idClient)){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							connectedToServer(0); 
							progressBar.setVisibility(View.GONE);
							layoutDetails.setVisibility(View.VISIBLE);
						}
					});

				}else{
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							Context myContext = getApplicationContext();
							CharSequence text = "ERROR DATABASE PULL!";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(myContext, text, duration);
							toast.show();
							connectedToServer(1);
							progressBar.setVisibility(View.GONE);
							//layoutDetails.setVisibility(View.VISIBLE);
						}
					});
				}
			}
		}).start();
	}

	/**
	 * Change the icon
	 * @param lighted : 0 if connected, 1 if not, 2 if refreshing
	 */
	private void connectedToServer(final int lighted){
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (connectedItem != null){
					switch (lighted){
					case 0: connectedItem.setIcon(R.drawable.ic_action_location_found_green);break;
					case 1: connectedItem.setIcon(R.drawable.ic_action_location_found_red);break;
					default: connectedItem.setIcon(R.drawable.ic_action_refresh);break;
					}
				}
			}
		});
	}

}