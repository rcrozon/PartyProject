package com.example.myparty;

import databaseHandler.DatabaseHandler;
import entities.Concert;
import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private Button buttonReservations ;
	private Button buttonAllConcerts ;
	private Button buttonNextConcerts ;
	private Button buttonNews ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private MenuItem connectedItem;
	private MenuItem updateItem;
	private int index = 0;
	private int nextIndex = 0;
	private DatabaseHandler dataBase;
	private ProgressBar progressBar;
	private LinearLayout layoutMain;
	private Context context;
	private ListLayout listReservations; 
	private ListLayout listAll ;
	private ListLayout listNext ;
	private ListLayout listNews; 
	private int idClient ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
		setProgressBarIndeterminateVisibility(true);
		context = this;
		setContentView(R.layout.activity_concerts);
		buttonReservations = (Button)findViewById(R.id.buttonReservations);
		layoutMain = (LinearLayout)findViewById(R.id.layoutMain);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		buttonAllConcerts = (Button)findViewById(R.id.buttonAllConcerts);
		buttonNews = (Button)findViewById(R.id.buttonNews);
		buttonNextConcerts = (Button)findViewById(R.id.buttonNextConcerts);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
		updateLists();
		/******* OUVERTURE SQLITE ******************************************************************************************/

		Bundle b = getIntent().getExtras();
		idClient = b.getInt("idClient");

		loadDatabase();
		dataBase = new DatabaseHandler(this);
		dataBase.open();
		
		/************************************************/
		this.buttonReservations.setBackgroundResource(R.drawable.button_selected);
		this.view_flipper.setDisplayedChild(0);
		buttonReservations.setOnClickListener(this);
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);
	}

	private void loadDatabase(){
		progressBar.setVisibility(View.VISIBLE);
		layoutMain.setVisibility(View.GONE);
		new Thread(new Runnable() { 
			@Override
			public void run() {
				if (DatabaseHandler.updateAllTables(context, idClient)){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							connectedToServer(0);  
							updateLists();
							progressBar.setVisibility(View.GONE);
							layoutMain.setVisibility(View.VISIBLE);
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
 
							updateLists();

							progressBar.setVisibility(View.GONE);
							layoutMain.setVisibility(View.VISIBLE);
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
		if (connectedItem != null){
			switch (lighted){
			case 0: connectedItem.setIcon(R.drawable.ic_action_location_found_green);break;
			case 1: connectedItem.setIcon(R.drawable.ic_action_location_found_red);break;
			default: connectedItem.setIcon(R.drawable.ic_action_refresh);break;
			}
		}
	}
	
	private void updateLists(){
		listReservations = new ListLayout(this, new ReservationsList(this, idClient));
		listAll = new ListLayout(this, new ConcertList(this, 0));
		listNext = new ListLayout(this, new ConcertList(this, 1));
		listNews = new ListLayout(this, new ConcertList(this, 2));
		this.view_flipper.removeAllViews();
		this.view_flipper.addView(listReservations); 
		this.view_flipper.addView(listAll); 
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		//updateItem = menu.findItem(R.id.u); 
		connectedItem = menu.findItem(R.id.menu_refresh);
		decoItem.setOnMenuItemClickListener(this);
		//updateItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		index = view_flipper.getDisplayedChild();
		buttonReservations.setBackgroundResource(R.drawable.button_unselected);
		buttonAllConcerts.setBackgroundResource(R.drawable.button_unselected);
		buttonNews.setBackgroundResource(R.drawable.button_unselected);
		buttonNextConcerts.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonReservations){
			nextIndex = 0;
			buttonReservations.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonAllConcerts){
			nextIndex = 1;
			buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonNextConcerts){
			nextIndex = 2; 
			buttonNextConcerts.setBackgroundResource(R.drawable.button_selected);
		}else{ 
			nextIndex = 3;
			buttonNews.setBackgroundResource(R.drawable.button_selected);
		}
		if (nextIndex != index){
			if (nextIndex > index){
				view_flipper.setInAnimation(this, R.anim.in_from_right);
				view_flipper.setOutAnimation(this, R.anim.out_to_left);
			}else{
				view_flipper.setInAnimation(this, R.anim.in_from_left);
				view_flipper.setOutAnimation(this, R.anim.out_to_right);
			}
			view_flipper.setDisplayedChild(nextIndex);	
		}
	}
	

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent;
		if (item == decoItem){
			intent = new Intent(this, ConnectionActivity.class);
			this.startActivity(intent);
		}
		else if(item == updateItem){
			loadDatabase();
			//			if(DatabaseHandler.updateAllTables(this)){
			//				intent = new Intent(this, ConcertActivity.class);
			//				this.startActivity(intent);
			//			}
		}
		return false;
	}
	
	/**
	 * 
	 * No return
	 * 
	 */
	
	public void onBackPressed(){
		//No implementation
	}
	
}
