package com.example.myparty;

import java.util.List;

import lists.ClientList;
import lists.ListLayout;
import lists.ReservationsList;
import lists.StatsList;
import lists.TicketsList;
import scan.IntentIntegrator;
import scan.IntentResult;
import scan.ScanLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import entities.Client;
import entities.Concert;
import entities.ConcertDetailed;

public class ConcertDetailsActivity extends Activity implements
		OnClickListener, OnMenuItemClickListener {

	private Button buttonTickets;
	private Button buttonMap;
	private Button buttonClients;
	private Button buttonDetails;
	private Button buttonScan;
	private Button buttonStats;
	private ViewFlipper view_flipper;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private ScanLayout scanner;
	private ImageView imgView;
	private TextView textTitle;
	private TextView textDate;
	private TextView textFull;
	private TextView textLocation;
	private TextView textNbSeets;
	private TextView textPrice;
	private DatabaseHandler dataBase;
	private boolean isCLient = false;
	private Concert concert;
	private int idResScan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_details);

		this.buttonTickets = (Button) findViewById(R.id.buttonTickets);
		this.buttonMap = (Button) findViewById(R.id.buttonMap);
		this.buttonClients = (Button) findViewById(R.id.buttonClient);
		this.buttonDetails = (Button) findViewById(R.id.buttonDetails);
		this.buttonScan = (Button) findViewById(R.id.buttonScan);
		this.buttonStats = (Button) findViewById(R.id.buttonStats);
		this.view_flipper = (ViewFlipper) findViewById(R.id.view_flipper);

		/****************** OUVERTURE BDD ***********************************/

		this.dataBase = new DatabaseHandler(this);
		this.dataBase.open();
		
		
		/************************ MISE A JOUR SERVEUR POUR LES SCAN ************************************/
		
		String jsonScan;
		jsonScan = dataBase.getJsonScanMAJ();
		Log.i("ScanJson", "Json:"+jsonScan);

		/****************** RECUPERATION DE L'ID DU CONCERT *****************/

		Bundle b = getIntent().getExtras();
		List<Client> clientForConcert = null;
		concert = null;
		if (b.getInt("id") != 0){
			concert = dataBase.getConcertWithId(b.getInt("id"));
			clientForConcert = dataBase.getClientsForOneConcert(concert.getId());
		}
		for (int i =0; i < clientForConcert.size();i++){
			Log.i("NOMBRE", "Client : "+clientForConcert.get(i).getId()+clientForConcert.get(i).getFirstName()+ " Possede : "+dataBase.getNumberResClientForOneConcert(concert, clientForConcert.get(i))+" Tickets"
					+ " Pour "+ concert.getId()+concert.getTitle());
		}

		
/************************** Traitement du bouton validation scan ***********************************/
		this.scanner = new ScanLayout(this, this);
		this.scanner.getButtonTariff().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						scanner.getImageView().setBackgroundResource(
								R.drawable.qrcode_blue);
						if (idResScan !=0 ){
							dataBase.scanTicket(idResScan);
						}
						textButtonValidate(" ");
					}
				});
		
		
		/**********************************************************/

		this.view_flipper.addView(new ConcertDetailed(this, concert));
		this.view_flipper.addView(new ListLayout(this, new TicketsList(this,
				null)));
		this.view_flipper.addView(new ListLayout(this, new ReservationsList(
				this, null)));
		this.view_flipper.addView(new ListLayout(this, new ClientList(this,
				clientForConcert,concert)));
		this.view_flipper.addView(scanner);
		this.view_flipper.addView(new StatsList(this));

		if (isCLient) {
			this.buttonTickets.setVisibility(View.VISIBLE);
			this.buttonMap.setVisibility(View.VISIBLE);
			this.buttonClients.setVisibility(View.GONE);
			this.buttonScan.setVisibility(View.GONE);
			this.buttonStats.setVisibility(View.GONE);
		} else {
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
		this.decoItem = menu.findItem(R.id.menu_deconect);
		this.bluetoothItem = menu.findItem(R.id.bluetooth);
		// decoItem.setIcon(R.drawable.logout);
		this.decoItem.setOnMenuItemClickListener(this);
		this.bluetoothItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		int index = view_flipper.getDisplayedChild();
		int nextIndex;
		buttonTickets.setBackgroundResource(R.drawable.button_unselected);
		buttonMap.setBackgroundResource(R.drawable.button_unselected);
		buttonClients.setBackgroundResource(R.drawable.button_unselected);
		buttonDetails.setBackgroundResource(R.drawable.button_unselected);
		buttonScan.setBackgroundResource(R.drawable.button_unselected);
		buttonStats.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonDetails) {
			nextIndex = 0;
			buttonDetails.setBackgroundResource(R.drawable.button_selected);
		} else if (b == buttonTickets) {
			nextIndex = 1;
			buttonTickets.setBackgroundResource(R.drawable.button_selected);
		} else if (b == buttonMap) {
			nextIndex = 2;
			buttonMap.setBackgroundResource(R.drawable.button_selected);
		} else if (b == buttonClients) {
			nextIndex = 3;
			buttonClients.setBackgroundResource(R.drawable.button_selected);
		} else if (b == buttonScan) {
			nextIndex = 4;
			buttonScan.setBackgroundResource(R.drawable.button_selected);
		} else {
			nextIndex = 5;
			buttonStats.setBackgroundResource(R.drawable.button_selected);
		}
		if (nextIndex != index) {
			if (nextIndex > index) {
				view_flipper.setInAnimation(this, R.anim.in_from_right);
				view_flipper.setOutAnimation(this, R.anim.out_to_left);
			} else {
				view_flipper.setInAnimation(this, R.anim.in_from_left);
				view_flipper.setOutAnimation(this, R.anim.out_to_right);
			}
			view_flipper.setDisplayedChild(nextIndex);
		}
	}

/**********************   Récupération des infos du billet      ************************************/
	/*******id_res;id_concert;id_client;id_tarif********/
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			String barcode;
			//String typ;
			barcode = scanResult.getContents();
			//typ = scanResult.getFormatName();
			String infoRes[] = barcode.split(";");
			Log.i("SPLIT", infoRes[0]+"/"+infoRes[1]+"/"+
					infoRes[2]+"/"+ infoRes[3]);
			Boolean ok = dataBase.isValidTicket(Integer.parseInt(infoRes[0]), Integer.parseInt(infoRes[1])
				, Integer.parseInt(infoRes[2]), Integer.parseInt(infoRes[3]),concert.getId());
			
			scanner.getTextView().setText(barcode + "   ");
			scanner.getTextView().setFreezesText(true);
			
			if (ok){
				scanner.getImageView().setBackgroundResource(
						R.drawable.qrcode_green);
				idResScan = Integer.parseInt(infoRes[0]);
				String labelTarrif = dataBase.getLabelById(Integer.parseInt(infoRes[3]));
				textButtonValidate("Tarif : "+labelTarrif);
			}
			else{
				scanner.getImageView().setBackgroundResource(
						R.drawable.qrcode_red);
				idResScan = 0;
				textButtonValidate("Error Ticket");
				
			}

		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent;
		if (item == decoItem) {
			intent = new Intent(this, ConnectionActivity.class);
		} else {
			intent = new Intent(this, BluetoothActivity.class);
		}
		this.startActivity(intent);
		return false;
	}

	/**
	 * Test in the database if the given QR code is correct and if the client's
	 * reservation is recorded
	 * 
	 * @return
	 */
	public void textButtonValidate(String message) {
		// TODO when the database is done
		scanner.getButtonTariff().setText(message);
	}
}