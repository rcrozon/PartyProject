package com.example.myparty;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import entities.Client;
import entities.Concert;


public class LoadingActivity extends Activity {

	private ProgressBar progressBar ;
	private DatabaseHandler dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		progressBar = (ProgressBar)findViewById(R.id.progressBarLoading);
		progressBar.setProgress(0);

/****************** OUVERTURE BDD ***********************************/
		
		dataBase = new DatabaseHandler(this);
		dataBase.open();
		
/******************  BDD EXTERNE  ***********************************/
		/*ON ENVOI LA REQUET*/
		DatabaseServer dbbs = new DatabaseServer(); 
		MyJsonParser parser = new MyJsonParser();
		
		String tmp =dbbs.getRequest("getAllClients");
		String concertString = dbbs.getRequest("getAllConcerts");
		
		List<Client> clientlist = parser.getClientFromJson(tmp);
		List<Concert> concertlist = parser.getConcertFromJson(concertString);
		progressBar.setMax(clientlist.size() + concertlist.size());
		/*On insere les concerts dans bdd*/
		for (int i=0 ; i< concertlist.size() ; i++){
			Concert c = concertlist.get(i);
			progressBar.setProgress(progressBar.getProgress() + 1);
			Log.i("Concert",c.testToString());
			//dataBase.insertConcert(c);
		}
		
		/*On insere les clients dans bdd*/
		for (int i=0 ; i< clientlist.size() ; i++){
			Client c = clientlist.get(i);
			Log.i("Client",c.testToString());
			progressBar.setProgress(progressBar.getProgress() + 1);
			//dataBase.insertClient(c);
		}
		Intent intent = new Intent(this, ConnectionActivity.class);
    	this.startActivity(intent);
	
	}
	
	
}
