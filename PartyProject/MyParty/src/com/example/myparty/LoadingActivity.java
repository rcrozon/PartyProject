package com.example.myparty;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import databaseHandler.ThreadRequestResult;
import entities.Client;
import entities.Concert;


public class LoadingActivity extends Activity {

	private ProgressBar progressBar ;
	private DatabaseHandler dataBase;    
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		context = this;
		try {  
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context = this;
		//progressBar = (ProgressBar)findViewById(R.id.progressBarLoading);
		//progressBar.setProgress(0);

		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
			
				/****************** OUVERTURE BDD ***********************************/
		
				dataBase = new DatabaseHandler(context);
				dataBase.open();
				
				/******************  BDD EXTERNE  ***********************************/
				ThreadRequestResult t = new ThreadRequestResult("http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/", "getAllConcerts");
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("NET", ""+t.getResult());
				
				/********************* Test du serveur et de la connexion internet ******************************/
				if(isNetworkConnected(context) /*&& t.getResult() != null*/){
					
					/*ON ENVOI LA REQUETE*/
					DatabaseServer dbbs = new DatabaseServer(); 
					MyJsonParser parser = new MyJsonParser(context);
					
					String tmp =dbbs.getRequest("getAllClients");
					String concertString = dbbs.getRequest("getAllConcerts");
					String reservationString = dbbs.getRequest("getAllReservations");
					String tarrifString = dbbs.getRequest("getAllTariffs");
		
					List<Client> clientlist = parser.getClientFromJson(tmp);
					List<Concert> concertlist = parser.getConcertFromJson(concertString);
					progressBar.setMax(clientlist.size() + concertlist.size());
					
					/*On insere les concerts dans bdd*/
					for (int i=0 ; i< concertlist.size() ; i++){
						Concert c = concertlist.get(i);
						progressBar.setProgress(progressBar.getProgress() + 1);
						Log.i("Concert",c.testToString());
						dataBase.insertConcert(c);
					}
					
					/*On insere les clients dans bdd*/
					for (int i=0 ; i< clientlist.size() ; i++){
						Client c = clientlist.get(i);
						Log.i("Client",c.testToString());
						progressBar.setProgress(progressBar.getProgress() + 1);
						dataBase.insertClient(c);
					}
					/*On insere les reservations*/
					parser.getReservationAndInsert(reservationString);
					
					/*On insere les Tarrifs*/
					parser.getTariffsAndInsert(tarrifString);
					
					Log.i("SCAN", "TARIF ADULTE ?? ::"+ dataBase.getLabelById(7));
					Log.i("NET", "On est connecté !!");
					changeActivity();
				}
				else{
					Log.i("NET", "On n'est pas connecté !!");
				
				}
				
			}
		});
		t.start();
		
	}
	
	public void changeActivity(){
		Intent intent = new Intent(this, ConnectionActivity.class);
    	this.startActivity(intent);
	}
	
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
		
	}
	
	
}
