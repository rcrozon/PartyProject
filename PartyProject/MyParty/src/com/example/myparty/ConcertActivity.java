package com.example.myparty;

import lists.ConcertList;
import lists.ListLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	DatabaseHandler dataBase;
	private Context context;
	private ProgressBar progressBar;
	private LinearLayout layoutMain;
 
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

		ListLayout listAll = new ListLayout(this, new ConcertList(this, null, 0));
		ListLayout listNext = new ListLayout(this, new ConcertList(this, null, 1));
		ListLayout listNews = new ListLayout(this, new ConcertList(this, null, 2));
		this.view_flipper.addView(listAll);
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
		this.buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		this.view_flipper.setDisplayedChild(0);
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		bluetoothItem = menu.findItem(R.id.bluetooth);
		updateItem = menu.findItem(R.id.update); 
		scanPushItem = menu.findItem(R.id.scanpush);
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
			String jsonScan;
			jsonScan = dataBase.getJsonScanMAJ();
			Log.i("ScanJson", "Json: "+jsonScan);
			DatabaseServer ser = new DatabaseServer();
			String reponse = ser.postRequest("majReservation", jsonScan);
			/*TODO Supprimer en fonction de la reponse*/
			Log.i("ScanJson", "REP : "+reponse);
			dataBase.deleteResMAJ();
			String jsonScan2;
			jsonScan2 = dataBase.getJsonScanMAJ();
			Log.i("ScanJson", "Json2: "+jsonScan2);
			if (reponse.equals("success")){
				Log.i("ScanJson", "ON A REUSSI");
			}
			else{
				Log.i("ScanJson", "ON A PAAAAASSSSSS REUSSI");
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
							progressBar.setVisibility(View.GONE);
							layoutMain.setVisibility(View.VISIBLE);
						}
					});
				}else{
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context, "Databse update impossible", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
	}

	public void onBackPressed(){
		//No implementation
	}

}
