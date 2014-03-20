package com.example.myparty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bluetooth.BluetoothClient;
import bluetooth.BluetoothDevices;
import bluetooth.BluetoothServer;
import lists.ClientList;
import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import lists.StatsList;
import lists.TicketsList;
import scan.IntentIntegrator;
import scan.IntentResult;
import scan.ScanLayout;
import BluetoothChat.BluetoothChat;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import entities.Client;
import entities.Concert;
import entities.ConcertDetailed;

public class ConcertDetailsActivity extends Activity implements
OnClickListener, OnMenuItemClickListener {


	private Button buttonClients;
	private Button buttonDetails;
	private Button buttonScan;
	private Button buttonStats;
	private ViewFlipper view_flipper;
	private MenuItem decoItem;
	private MenuItem bluetoothItem;
	private ScanLayout scanner;
	private Context context;
	private DatabaseHandler dataBase;
	private MenuItem connectedItem;

	private Concert concert;
	private int idResScan;
	private MenuItem updateItem;
	private MenuItem scanPushItem;
	private ProgressBar progressBar;
	private LinearLayout layoutMain;
	private ArrayList<BluetoothClient> listBluetoothClient = new ArrayList<BluetoothClient>();
	private BluetoothServer server;
	private List<Client> clientForConcert;
	
	//////////////////////////////
	// Debugging
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

	//////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concert_details);
		context = this;
		layoutMain = (LinearLayout)findViewById(R.id.layoutMain);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		this.buttonClients = (Button) findViewById(R.id.buttonClient);
		this.buttonDetails = (Button) findViewById(R.id.buttonDetails);
		this.buttonScan = (Button) findViewById(R.id.buttonScan);
		this.buttonStats = (Button) findViewById(R.id.buttonStats);
		this.view_flipper = (ViewFlipper) findViewById(R.id.view_flipper);

		///////////////////////////////////////////
		// Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

		///////////////////////////////////////////
		/****************** OUVERTURE BDD ***********************************/
		lightHandler();
		this.dataBase = new DatabaseHandler(this);
		this.dataBase.open();

		server = new BluetoothServer();
		server.start();
		/************************ MISE A JOUR SERVEUR POUR LES SCAN ************************************/

		String jsonScan;
		jsonScan = dataBase.getJsonScanMAJ();
		Log.i("ScanJson", "Json:"+jsonScan);

		/****************** RECUPERATION DE L'ID DU CONCERT *****************/

		Bundle b = getIntent().getExtras();
		clientForConcert = null;
		concert = null;
		if (b.getInt("id") != 0){ 
			concert = dataBase.getConcertWithId(b.getInt("id"));
			clientForConcert = dataBase.getClientsForOneConcert(concert.getId());
		}
		if (clientForConcert!=null){
			/************* TRI ALPHABETIQUE ***********/
			Log.i("LISTE", "NonTrié"+ clientForConcert.toString());

			List<Client> oui = new ArrayList<Client>();
			while(clientForConcert.size()>0){
				int num = 0;
				Log.i("Tri", "Taille "+clientForConcert.size());
				for (int i=1 ; i<clientForConcert.size() ; i++){
					if (clientForConcert.get(num).getLastName().compareToIgnoreCase(clientForConcert.get(i).getLastName()) > 0){
						num =i;
					}
				}
				oui.add(clientForConcert.get(num));
				clientForConcert.remove(num);

			}
			clientForConcert=oui;

			Log.i("LISTE", "Trie"+ clientForConcert.toString());

//			BluetoothDevices bluetoothDevices = new BluetoothDevices(this);
//
//		    for(BluetoothDevice device : bluetoothDevices.getBluetoothDevices()){
//		    	BluetoothClient client = new BluetoothClient(device, this);
//		    	listBluetoothClient.add(client);
//		    }

			/*****************************************/
			for (int i =0; i < clientForConcert.size();i++){
				Log.i("NOMBRE", "Client : "+clientForConcert.get(i).getId()+clientForConcert.get(i).getFirstName()+ " Possede : "+dataBase.getNumberResClientForOneConcert(concert, clientForConcert.get(i))+" Tickets"
						+ " Pour "+ concert.getId()+concert.getTitle());
			}
		}

		/************************** Traitement du bouton validation scan ***********************************/
		this.scanner = new ScanLayout(this, this);
		this.scanner.getButtonTariff().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						scanner.getImageView().setBackgroundResource(R.drawable.qrcode_blue);
						if (idResScan != 0 ){
							/*
//							for(BluetoothClient client : listBluetoothClient){
							Log.i("TAG ENVOIE ID_RES", "ENVOIE " + idResScan);
							server.write(context, idResScan);
//							}
							scanner.getTextView().setText("");
							dataBase.scanTicket(idResScan);
							 */
						    
						}
						textButtonValidate("");
					}
				});


        /**********************************************************/

		this.view_flipper.addView(new ConcertDetailed(this, concert));
		this.view_flipper.addView(new ListLayout(this, new ClientList(this,
				clientForConcert,concert)));
		this.view_flipper.addView(scanner);
		this.view_flipper.addView(new StatsList(this, concert.getId()));


		this.buttonClients.setVisibility(View.VISIBLE);
		this.buttonScan.setVisibility(View.VISIBLE);
		this.buttonStats.setVisibility(View.VISIBLE);



		this.buttonClients.setOnClickListener(this);
		this.buttonDetails.setOnClickListener(this);
		this.buttonScan.setOnClickListener(this);
		this.buttonStats.setOnClickListener(this);
		
		BluetoothChat chat = new BluetoothChat(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		this.decoItem = menu.findItem(R.id.menu_deconect);
		this.bluetoothItem = menu.findItem(R.id.bluetooth);
		connectedItem = menu.findItem(R.id.menu_refresh);
		scanPushItem = menu.findItem(R.id.scanpush);
		updateItem = menu.findItem(R.id.update); 
		scanPushItem.setOnMenuItemClickListener(this);
		updateItem.setOnMenuItemClickListener(this);
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
		buttonClients.setBackgroundResource(R.drawable.button_unselected);
		buttonDetails.setBackgroundResource(R.drawable.button_unselected);
		buttonScan.setBackgroundResource(R.drawable.button_unselected);
		buttonStats.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonDetails) {
			nextIndex = 0;
			buttonDetails.setBackgroundResource(R.drawable.button_selected);
		}  else if (b == buttonClients) {
			nextIndex = 1;
			buttonClients.setBackgroundResource(R.drawable.button_selected);
		} else if (b == buttonScan) {
			nextIndex = 2;
			buttonScan.setBackgroundResource(R.drawable.button_selected);
		} else {
			nextIndex = 3;
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
			if (barcode!=null){
				//typ = scanResult.getFormatName();
				String infoRes[] = barcode.split(";");
				Log.i("SPLIT", infoRes[0]+"/"+infoRes[1]+"/"+
						infoRes[2]+"/"+ infoRes[3]);
				Boolean ok = dataBase.isValidTicket(Integer.parseInt(infoRes[0]), Integer.parseInt(infoRes[1])
						, Integer.parseInt(infoRes[2]), Integer.parseInt(infoRes[3]),concert.getId());

				if (ok){
					Client client = dataBase.getClientWithId(Integer.parseInt(infoRes[2]));
					Concert concert = dataBase.getConcertWithId(Integer.parseInt(infoRes[1]));
					scanner.getTextView().setText(client.getFirstName() + "\n" + client.getLastName() + "\n"+ concert.getTitle());
					scanner.getImageView().setBackgroundResource(R.drawable.qrcode_green);
					idResScan = Integer.parseInt(infoRes[0]);
					String labelTarrif = DatabaseHandler.getLabelById(Integer.parseInt(infoRes[3]));
					textButtonValidate("Tarif : "+labelTarrif);
				}
				else{
					scanner.getTextView().setText(barcode + "   ");
					scanner.getImageView().setBackgroundResource(
							R.drawable.qrcode_red);
					idResScan = 0;
					textButtonValidate("Error Ticket");

				}
				scanner.getTextView().setFreezesText(true);



			}
		}
	}


	/**
	 * Handler the icon showing the connection state
	 */
	private void lightHandler(){
		new Thread(new Runnable() {
			public void run() {
				while(ConnectionActivity.running){
					if (DatabaseHandler.isAvailableServer(context))
						connectedToServer(0);
					else
						connectedToServer(1);
					try {
						Thread.sleep(10 * 60 * 1000);
					} catch (InterruptedException e) {}
				}
			}
		}).start();
	}


	/**
	 * Change the icon
	 * @param lighted : 0 if connected, 1 if not, 2 if refreshing
	 */
	private void connectedToServer(final int lighted){
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (connectedItem != null){
					switch (lighted){
					case 0: connectedItem.setIcon(R.drawable.ic_action_location_found_green);break;
					case 1: connectedItem.setIcon(R.drawable.ic_action_location_found_red);break;
					default: connectedItem.setIcon(R.drawable.ic_action_refresh);break;
					}
				}
			}
		});
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = null;
		if (item == decoItem) {
			intent = new Intent(this, ConnectionActivity.class);
			this.startActivity(intent);

		} else if(item == bluetoothItem) {
			intent = new Intent(this, BluetoothActivity.class);
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
		if (!message.equals("")){
			scanner.getButtonTariff().setVisibility(View.VISIBLE);	
		}else{
			scanner.getButtonTariff().setVisibility(View.INVISIBLE);	
		}
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


	private void updateLists(){
		clientForConcert = dataBase.getClientsForOneConcert(concert.getId());

		if (clientForConcert!=null){
			/************* TRI ALPHABETIQUE ***********/
			Log.i("LISTE", "NonTrié"+ clientForConcert.toString());

			List<Client> oui = new ArrayList<Client>();
			while(clientForConcert.size()>0){
				int num = 0;
				Log.i("Tri", "Taille "+clientForConcert.size());
				for (int i=1 ; i<clientForConcert.size() ; i++){
					if (clientForConcert.get(num).getLastName().compareToIgnoreCase(clientForConcert.get(i).getLastName()) > 0){
						num =i;
					}
				}
				oui.add(clientForConcert.get(num));
				clientForConcert.remove(num);

			}
			clientForConcert=oui;
		}
		int index = view_flipper.getDisplayedChild();
		this.view_flipper.removeAllViews();
		this.view_flipper.addView(new ConcertDetailed(this, concert));
		this.view_flipper.addView(new ListLayout(this, new ClientList(this,
				clientForConcert,concert)));
		this.view_flipper.addView(scanner);
		this.view_flipper.addView(new StatsList(this, concert.getId()));
		view_flipper.setDisplayedChild(index);

	}	



}