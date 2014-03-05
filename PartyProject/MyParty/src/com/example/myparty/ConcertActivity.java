package com.example.myparty;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import databaseHandler.Tables;
import databaseHandler.ThreadTestServer;
import entities.Client;
import entities.Concert;
import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dataBase = new DatabaseHandler(this);
		dataBase.open();
		/*********  Mise a jour des tables*************/
		updateAllTable();

		setContentView(R.layout.activity_concerts);
		buttonAllConcerts = (Button)findViewById(R.id.buttonAllConcerts);
		buttonNews = (Button)findViewById(R.id.buttonNews);
		buttonNextConcerts = (Button)findViewById(R.id.buttonNextConcerts);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);

		ListLayout listAll = new ListLayout(this, new ConcertList(this, null));
		ListLayout listNext = new ListLayout(this, new ConcertList(this, null));
		ListLayout listNews = new ListLayout(this, new ConcertList(this, null));
		this.view_flipper.addView(listAll);
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
		this.buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		this.view_flipper.setDisplayedChild(1);
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
			if(updateAllTable()){
				intent = new Intent(this, ConcertActivity.class);
				this.startActivity(intent);
			}
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

	/**
	 * 
	 * No return
	 * 
	 */

	public void onBackPressed(){
		//No implementation
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());

	}

	public Boolean isAvailableServer(){
		ThreadTestServer tPing = new ThreadTestServer(this);
		tPing.start();
		try {
			tPing.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tPing.getResult();
	}

	public Boolean updateAllTable(){
		/********************* Test du serveur et de la connexion internet ******************************/
		if(isNetworkConnected(this) && isAvailableServer()){
			/*Vide la table*/
			dataBase.deleteAllTable();
			/*ON ENVOI LA REQUETE*/
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
			return true;
		}


		else{
			Log.i("NET", "On n'est pas connecté !!");
			return false;

		}
	}


}
