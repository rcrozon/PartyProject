package com.example.myparty;

import lists.ConcertList;
import lists.ListLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private Button buttonAllConcerts ;
	private Button buttonNextConcerts ;
	private Button buttonNews ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private MenuItem updateItem;
	private MenuItem scanPushItem;
	private int index = 0;
	private int nextIndex = 0;
	private DatabaseHandler dataBase;
	private Context context;
	private ProgressBar progressBar;
	private LinearLayout layoutMain;
	private MenuItem connectedItem;

	private ListLayout listAll;
	private ListLayout listNext;
	private ListLayout listNews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_concerts);
		layoutMain = (LinearLayout)findViewById(R.id.layoutMain);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		buttonAllConcerts = (Button)findViewById(R.id.buttonAllConcerts);
		buttonNews = (Button)findViewById(R.id.buttonNews);
		buttonNextConcerts = (Button)findViewById(R.id.buttonNextConcerts);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);

		/******* OUVERTURE SQLITE ******************************************************************************************/
 
		loadDatabase();
		dataBase = new DatabaseHandler(this);
		dataBase.open(); 

		/******* MISE A JOUR SQLITE DEPUIS LE SERVEUR ******************************************************************************************/

		updateLists();
		this.buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		this.view_flipper.setDisplayedChild(0);
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);

	}

	private void updateLists(){
		listAll = new ListLayout(this, new ConcertList(this, 0));
		listNext = new ListLayout(this, new ConcertList(this, 1));
		listNews = new ListLayout(this, new ConcertList(this, 2));
		this.view_flipper.removeAllViews();
		this.view_flipper.addView(listAll); 
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
		view_flipper.setDisplayedChild(nextIndex);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		bluetoothItem = menu.findItem(R.id.bluetooth);
		updateItem = menu.findItem(R.id.update); 
		scanPushItem = menu.findItem(R.id.scanpush);
		connectedItem = menu.findItem(R.id.menu_refresh);
		//decoItem.setIcon(R.drawable.logout);
		decoItem.setOnMenuItemClickListener(this);
		bluetoothItem.setOnMenuItemClickListener(this);
		updateItem.setOnMenuItemClickListener(this);
		scanPushItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		index = view_flipper.getDisplayedChild();
		buttonAllConcerts.setBackgroundResource(R.drawable.button_unselected);
		buttonNews.setBackgroundResource(R.drawable.button_unselected);
		buttonNextConcerts.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonAllConcerts){
			nextIndex = 0;
			buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonNextConcerts){
			nextIndex = 1; 
			buttonNextConcerts.setBackgroundResource(R.drawable.button_selected);
		}else{ 
			nextIndex = 2;
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
		else if (item == scanPushItem){
			
			
			/*tester la connexion*/
			progressBar.setVisibility(View.VISIBLE);
			layoutMain.setVisibility(View.GONE);
			
			if(DatabaseHandler.isNetworkConnected(context) && DatabaseHandler.isAvailableServer(context)){
				
				
				new Thread(new Runnable() { 
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								String jsonScan;
								jsonScan = dataBase.getJsonScanMAJ();
								Log.i("ScanJson", "Json: "+jsonScan);
								DatabaseServer ser = new DatabaseServer();
								String reponse = ser.postRequest("majReservation", jsonScan);
								/*TODO Supprimer en fonction de la reponse*/
								Log.i("ScanJson", "REP : "+reponse);
								String jsonScan2;
								jsonScan2 = dataBase.getJsonScanMAJ();
								Log.i("ScanJson", "Json2: "+jsonScan2);
								Context myContext = getApplicationContext();
								
								MyJsonParser pars = new MyJsonParser(context);
								if (pars.reponseIsClient(reponse)){
									Log.i("ScanJson", "ON A REUSSI");
									dataBase.deleteResMAJ();
									CharSequence text = "PUSH OK!";
									int duration = Toast.LENGTH_LONG;

									Toast toast = Toast.makeText(myContext, text, duration);
									toast.show();
								}
								else{
									Log.i("ScanJson", "ON A PAAAAASSSSSS REUSSI");
									CharSequence text = "PUSH NOT OK!";
									int duration = Toast.LENGTH_LONG;

									Toast toast = Toast.makeText(myContext, text, duration);
									toast.show();
								}
								
								progressBar.setVisibility(View.GONE);
								layoutMain.setVisibility(View.VISIBLE);
								
							}
						});
						
						
					}
				}).start();
				
				
			}
			else{
				progressBar.setVisibility(View.GONE);
				layoutMain.setVisibility(View.VISIBLE);
				Context myContext = getApplicationContext();
				CharSequence text = "ERROR DATABASE PUSH!";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(myContext, text, duration);
				toast.show();
			}
		}
		
		
		
		
		else{
			intent = new Intent(this, BluetoothActivity.class);
			this.startActivity(intent);
		}
		return false;
	}

	private void loadDatabase(){
		progressBar.setVisibility(View.VISIBLE);
		layoutMain.setVisibility(View.GONE);
		new Thread(new Runnable() { 
			@Override
			public void run() {
				if (DatabaseHandler.updateAllTables(context)){
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

	public void onBackPressed(){
		//No implementation
	}

}
