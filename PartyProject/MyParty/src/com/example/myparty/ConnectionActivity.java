package com.example.myparty;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MCrypt;
import databaseHandler.ThreadTestServer;


public class ConnectionActivity extends Activity implements OnClickListener {

	private Button buttonConnexion ;
	private MenuItem item;

	private boolean running = true;
	private DatabaseHandler dataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonConnexion = (Button)findViewById(R.id.buttonConnexion);
		buttonConnexion.setOnClickListener(this);
		lightHandler();

		/****************** OUVERTURE BDD ***********************************/

		dataBase = new DatabaseHandler(this);
		dataBase.open();


		/******************  TEST ***********************************/


		String jsonScan;
		jsonScan = dataBase.getJsonScanMAJ();
		Log.i("ScanJson", "Json:"+jsonScan);

		/******************  BDD EXTERNE  ***********************************/

		/*/*ON ENVOI LA REQUET*/
		/*DatabaseServer dbbs = new DatabaseServer(); 
		MyJsonParser parser = new MyJsonParser();

		String tmp =dbbs.getRequest("getAllClients");
		String concertString = dbbs.getRequest("getAllConcerts");

		List<Client> clientlist = parser.getClientFromJson(tmp);
		List<Concert> concertlist = parser.getConcertFromJson(concertString);
		 */
		/*On insere les concerts dans bdd*/
		/*	for (int i=0 ; i< concertlist.size() ; i++){
			Concert c = concertlist.get(i);
			Log.i("Concert",c.testToString());
			//dataBase.insertConcert(c);
		}

		/*On insere les clients dans bdd*/
		/*for (int i=0 ; i< clientlist.size() ; i++){
			Client c = clientlist.get(i);
			Log.i("Client",c.testToString());
			//dataBase.insertClient(c);
		}

		//dataBase.in*/

	}

	@Override
	public void onPause(){
		super.onPause();
		running = false;
	}

	@Override
	public void onResume(){
		super.onResume();
		running = true;
		lightHandler();
	}

	@Override
	public void onStop(){
		super.onStop();
		running = false;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		running = false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		item = menu.findItem(R.id.menu_refresh);
		return true;
	}


	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		if (b == buttonConnexion){

			ThreadTestServer tPing = new ThreadTestServer(this);
			tPing.start();

			try {
				tPing.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/********************* Test du serveur et de la connexion internet ******************************/
			if(isNetworkConnected(this) && tPing.getResult()){

				/* TODO A DECOMMENTER SI ON NE VEUT PAS UTILISER AUTHENTIFICATION*/
				EditText pwd = (EditText)findViewById(R.id.pwdTextEdit);
				EditText login = (EditText)findViewById(R.id.loginTextEdit);
				String myLogin = login.getText().toString();
				String password = pwd.getText().toString();

				Log.i("HSA", "entré "+ password);

				MCrypt mcrypt = new MCrypt();

				/* Encrypt */
				String encrypted=null;
				try {
					encrypted = MCrypt.bytesToHex( mcrypt.encrypt(password) );
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String json = "[{\"username\":\""+myLogin+"\",\"password\":\""+password+"\"}]";


				Log.i("HSA", json);
				Log.i("HSA", encrypted);

				DatabaseServer dbbs = new DatabaseServer();
				String reponse = dbbs.postRequest("login", json);
				Log.i("HSA", "REP: "+reponse);


				/* Decrypt */
				/*String decrypted=null;
			try {
				decrypted = new String( mcrypt.decrypt( encrypted ) );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("HSA", "decrypté "+ decrypted);*/

				Intent intent = new Intent(this, ConcertActivity.class);
				this.startActivity(intent);
			}
			else{

				/****************** AUTHENTIFICATION ***********************************/			

				EditText login = (EditText)findViewById(R.id.loginTextEdit);
				EditText pwd = (EditText)findViewById(R.id.pwdTextEdit);
				Log.i("LOGIN", login.getText().toString() + "  " + pwd.getText().toString());
				if (dataBase.authentification(login.getText().toString(), pwd.getText().toString())){
					Intent intent = new Intent(this, ConcertActivity.class);
					this.startActivity(intent);
				}


				else{
					/*** ERREUR *************/
					Context myContext = getApplicationContext();
					CharSequence text = "ERROR LOGIN OR PASSWORD !";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(myContext, text, duration);
					toast.setGravity(Gravity.TOP|Gravity.LEFT, 150, 600);
					toast.show();
				}

			}
		}
	}

		/**
		 * Handler the icon showing the connection state
		 */
		private void lightHandler(){
			new Thread(new Runnable() {
				public void run() {
					while(running){
						//if (userFunctions.isUserLoggedIn())
						//			connectedToServer(0);
						//	else
						connectedToServer(1);
						try {
							Thread.sleep(500);
							connectedToServer(2);
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
					if (item != null){
						if (lighted == 0){
							item.setIcon(R.drawable.ic_action_location_found_green);
						}else if (lighted == 1){
							item.setIcon(R.drawable.ic_action_location_found_red);
						}else{
							item.setIcon(R.drawable.ic_action_refresh);
						}
					}
				}
			});
		}


		/**
		 * Quit application
		 * 
		 */
		public void onBackPressed(){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);


		}
		public static boolean isNetworkConnected(Context context) {
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());

		}

	}
