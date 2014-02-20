package com.example.myparty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import android.R.bool;
import databaseHandler.DatabaseHandler;
import databaseHandler.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private Button buttonReservations ;
	private Button buttonAllConcerts ;
	private Button buttonNextConcerts ;
	private Button buttonNews ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private int index = 0;
	private int nextIndex = 0;
	private boolean isClient = false;
	private DatabaseHandler dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			this.view_flipper.setDisplayedChild(0);
		}else{
			this.buttonReservations.setVisibility(View.GONE);
			this.view_flipper.setDisplayedChild(1);
		}
		buttonReservations.setOnClickListener(this);
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);
		
/****************** BDD EXTERNE ***********************************/
	
	new Thread(new Runnable() {
	
	@Override
	public void run() {
		StringBuilder response = new StringBuilder();
		 URI uri2=null;
		try {
			uri2 = new URI("http://jeremy.etcheverry.emi.u-bordeaux.fr/PartySite/concerts.json");
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	            try {
	                HttpGet get = new HttpGet();
	                get.setURI(uri2);
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpResponse httpResponse = httpClient.execute(get);
	         
	 
	                    HttpEntity messageEntity = httpResponse.getEntity();
	                    InputStream is = messageEntity.getContent();
	                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	                    String line;
	                    while ((line = br.readLine()) != null) {
	                        response.append(line);
	                    
	                }
	            } catch (Exception e) {
	               //Log.e("[GET REQUEST]", e.getMessage());
	            	Log.i("twit", "excep");
	            }
	           Log.d("[GET REQUEST]", "Done with HTTP getting");
			
	            Log.i("TWIT", "on recoit : "+ response.toString());
	        
	            try {
	            	
					//ON CREER UN JSON GRACE A LA REPONSE
	            	JSONObject js = new JSONObject(response.toString());
	            	//ON EXTRAIT LA STRING SANS POST
					String tmp = js.getString("posts");
					//ON CRÉE UN TABLEEAU
					JSONArray ja = js.getJSONArray("posts");
					//ON CRÉE UN JSON AVEC CHAQUE LIGNE DU TABLEAU
					JSONObject t = ja.getJSONObject(0);
					//ON EXTRAIT LA CHAINE DE LA LIGNE
					String te = t.getString("Concert");
					//ON CRÉE UN OBJET DEPUIS LA CHIANE
					JSONObject en = new JSONObject(te);
					//ON A LES INFO
					String p = en.getString("id");
					String p2 = en.getString("location");
					
					Log.i("twit",p2);
					
					
					String s = ja.getString(0);
					String tab[] = s.split("\"");
					//Log.i("twit",tab[5] );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
}).start();
	 
		
/****************** OUVERTURE BDD ***********************************/
		
		dataBase = new DatabaseHandler(this);
		dataBase.open();
	
	} 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		bluetoothItem = menu.findItem(R.id.bluetooth);
		//decoItem.setIcon(R.drawable.logout);
		decoItem.setOnMenuItemClickListener(this);
		bluetoothItem.setOnMenuItemClickListener(this);
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
		}else{
			intent = new Intent(this, BluetoothActivity.class);
		}
		this.startActivity(intent);	
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
