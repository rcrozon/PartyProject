package com.example.myparty;

import java.util.List;

import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import entities.Client;
import entities.Concert;
import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private Button buttonReservations ;
	private Button buttonAllConcerts ;
	private Button buttonNextConcerts ;
	private Button buttonNews ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private MenuItem updateItem;
	private int index = 0;
	private int nextIndex = 0;
	private boolean isClient = false;
	DatabaseHandler dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dataBase = new DatabaseHandler(this);
		dataBase.open();
		
		setContentView(R.layout.activity_concerts);
		buttonReservations = (Button)findViewById(R.id.buttonReservations);
		buttonAllConcerts = (Button)findViewById(R.id.buttonAllConcerts);
		buttonNews = (Button)findViewById(R.id.buttonNews);
		buttonNextConcerts = (Button)findViewById(R.id.buttonNextConcerts);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
		ListLayout listReservations = new ListLayout(this, new ReservationsList(this, null));
		ListLayout listAll = new ListLayout(this, new ConcertList(this, null));
		ListLayout listNext = new ListLayout(this, new ConcertList(this, null));
		ListLayout listNews = new ListLayout(this, new ConcertList(this, null));
		this.view_flipper.addView(listReservations);	
		this.view_flipper.addView(listAll);
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
		if (isClient){
			this.buttonReservations.setVisibility(View.VISIBLE);
			this.buttonReservations.setBackgroundResource(R.drawable.button_selected);
			this.view_flipper.setDisplayedChild(0);
		}else{
			this.buttonReservations.setVisibility(View.GONE);
			this.buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
			this.view_flipper.setDisplayedChild(1);
		}
		buttonReservations.setOnClickListener(this);
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
		//decoItem.setIcon(R.drawable.logout);
		decoItem.setOnMenuItemClickListener(this);
		bluetoothItem.setOnMenuItemClickListener(this);
		updateItem.setOnMenuItemClickListener(this);
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
			//if(isNetworkConnected(this) /*&& t.getResult() != null*/){
				
				/*ON ENVOI LA REQUETE*/
				dataBase.deleteAllTable();
				
				DatabaseServer dbbs = new DatabaseServer(); 
				MyJsonParser parser = new MyJsonParser(this);
				
				String tmp =dbbs.getRequest("getAllClients");
				String concertString = dbbs.getRequest("getAllConcerts");
				String reservationString = dbbs.getRequest("getAllReservations");
				String tarrifString = dbbs.getRequest("getAllTariffs");

				List<Client> clientlist = parser.getClientFromJson(tmp);
				List<Concert> concertlist = parser.getConcertFromJson(concertString);
				
				/*On insere les concerts dans bdd*/
				for (int i=0 ; i< concertlist.size() ; i++){
					Concert c = concertlist.get(i);
					Log.i("Concert",c.testToString());
					dataBase.insertConcert(c);
				}
				
				/*On insere les clients dans bdd*/
				for (int i=0 ; i< clientlist.size() ; i++){
					Client c = clientlist.get(i);
					Log.i("Client",c.testToString());
					dataBase.insertClient(c);
				}
				/*On insere les reservations*/
				parser.getReservationAndInsert(reservationString);
				
				/*On insere les Tarrifs*/
				parser.getTariffsAndInsert(tarrifString);
				
				Log.i("SCAN", "TARIF ADULTE ?? ::"+ dataBase.getLabelById(7));
				Log.i("NET", "On est connecté !!");
				intent = new Intent(this, ConcertActivity.class);
				this.startActivity(intent);
			
		//	}
		//	else{
		//		Log.i("NET", "On n'est pas connecté !!");
			
		//	}
		}
		else{
			intent = new Intent(this, BluetoothActivity.class);
			this.startActivity(intent);
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
