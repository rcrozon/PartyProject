package com.example.myparty;

import java.util.ArrayList;
import java.util.List;

import lists.ClientList;
import lists.ListLayout;
import lists.ReservationsList;
import lists.StatsList;
import lists.TicketsList;
import lists.TicketsList;
import scan.IntentIntegrator;
import scan.IntentResult;
import scan.ScanLayout;
import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import databaseHandler.DatabaseHandler;
import entities.Client;
import entities.Concert;
import entities.ConcertDetailed;

public class ConcertDetailsActivity extends Activity implements OnClickListener,OnMenuItemClickListener{

	private Button buttonTickets ;
	private Button buttonMap ;
	private Button buttonClients ;
	private Button buttonDetails ;
	private Button buttonScan ;
	private Button buttonStats ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private ScanLayout scanner;
	private ScrollView scrollScan ;
	private ImageView imgView ;
	private TextView textTitle;
	private TextView textDate;
	private TextView textFull;
	private TextView textLocation;
	private TextView textNbSeets;
	private TextView textPrice;
	private DatabaseHandler dataBase;
	private boolean isCLient = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_concert_details);
		
		scrollScan = new ScrollView(this); 
		buttonTickets = (Button)findViewById(R.id.buttonTickets);
		buttonMap = (Button)findViewById(R.id.buttonMap);
		buttonClients = (Button)findViewById(R.id.buttonClient);
		buttonDetails = (Button)findViewById(R.id.buttonDetails);
		buttonScan = (Button)findViewById(R.id.buttonScan);
		buttonStats = (Button)findViewById(R.id.buttonStats);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
		
/****************** OUVERTURE BDD ***********************************/

		dataBase = new DatabaseHandler(this);
		dataBase.open();
		
/****************** RECUPERATION DE L'ID DU CONCERT *****************/		
		
		Bundle b = getIntent().getExtras();
		Concert concert = dataBase.getConcertWithId(b.getInt("id"));
		
/****************** RECUPERATION DE LA LISTE DES CLIENTS *****************/	
		
		List<Client> clientForConcert =  dataBase.getClientForOneConcert(concert.getId());
		
		scanner = new ScanLayout(this, this);
		scrollScan.addView(scanner);
		

		this.scanner.getButtonTariff().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_blue);	
			}
		});

		this.view_flipper.addView(new ConcertDetailed(this, concert));
		this.view_flipper.addView(new ListLayout(this, new TicketsList(this, null)));
		this.view_flipper.addView(new ListLayout(this, new ReservationsList(this, null)));
		this.view_flipper.addView(new ListLayout(this, new ClientList(this,clientForConcert)));
		this.view_flipper.addView(scrollScan);
		this.view_flipper.addView(new StatsList(this));

		if(isCLient){
			this.buttonTickets.setVisibility(View.VISIBLE);
			this.buttonMap.setVisibility(View.VISIBLE);
			this.buttonClients.setVisibility(View.GONE);
			this.buttonScan.setVisibility(View.GONE);
			this.buttonStats.setVisibility(View.GONE);
		}else{
			this.buttonClients.setVisibility(View.VISIBLE);
			this.buttonScan.setVisibility(View.VISIBLE);
			this.buttonStats.setVisibility(View.VISIBLE);
			this.buttonTickets.setVisibility(View.GONE);
			this.buttonMap.setVisibility(View.GONE);
		}
		this.buttonTickets.setOnClickListener(this);
		this.buttonMap.setOnClickListener(this);
		this.buttonClients.setOnClickListener(this);
		this.buttonDetails.setOnClickListener(this);
		this.buttonScan.setOnClickListener(this);
		this.buttonStats.setOnClickListener(this);
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
		int index = view_flipper.getDisplayedChild();
		int nextIndex ;
		buttonTickets.setBackgroundResource(R.drawable.button_unselected);
		buttonMap.setBackgroundResource(R.drawable.button_unselected);
		buttonClients.setBackgroundResource(R.drawable.button_unselected);
		buttonDetails.setBackgroundResource(R.drawable.button_unselected);
		buttonScan.setBackgroundResource(R.drawable.button_unselected);
		buttonStats.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonDetails){
			nextIndex = 0;
			buttonDetails.setBackgroundResource(R.drawable.button_selected);
		}else if(b == buttonTickets){
			nextIndex = 1;
			buttonTickets.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonMap){
			nextIndex = 2;
			buttonMap.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonClients){
			nextIndex = 3;
			buttonClients.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonScan){
			nextIndex = 4;
			buttonScan.setBackgroundResource(R.drawable.button_selected);
		}else{
			nextIndex = 5; 
			buttonStats.setBackgroundResource(R.drawable.button_selected);
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null){
			String barcode;
			String typ;
			barcode = scanResult.getContents();
			typ = scanResult.getFormatName();
			scanner.getTextView().setText(barcode + "   " + typ);
			scanner.getTextView().setFreezesText(true);
			if (codeDatabaseHandler())
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_green);
			else
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_red);
			
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
	 * Test in the database if the given QR code is correct and if the client's reservation is recorded
	 * @return
	 */
	public boolean codeDatabaseHandler(){
		// TODO when the database is done
		scanner.getButtonTariff().setText("Carte Etudiante n���cessaire");
		return true;
	}
}