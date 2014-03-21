package com.example.myparty;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MCrypt;
import databaseHandler.MyJsonParser;
import entities.Client;


public class ConnectionActivity extends Activity implements OnClickListener, OnFocusChangeListener {

	private Button buttonConnexion ;
	private EditText editTextLogin ;
	private EditText editTextPassword ;
	private MenuItem item;
	private Context context;
	private boolean running = true;
	private DatabaseHandler dataBase; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonConnexion = (Button)findViewById(R.id.buttonConnexion);
		editTextLogin = (EditText)findViewById(R.id.loginTextEdit);
		editTextPassword = (EditText)findViewById(R.id.pwdTextEdit);
		buttonConnexion.setOnClickListener(this);
		editTextLogin.setOnFocusChangeListener(this);
		editTextPassword.setOnFocusChangeListener(this);
		context = this;
		lightHandler();
		
		/****************** OUVERTURE BDD ***********************************/

		dataBase = new DatabaseHandler(this);
		dataBase.open();

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
	public void onFocusChange(View v, boolean hasFocus) {
		EditText edit = (EditText)v;
		if (edit == editTextLogin){
			editTextLogin.setBackground(getResources().getDrawable(R.drawable.edit_text_design_focus));
			editTextPassword.setBackground(getResources().getDrawable(R.drawable.edit_text_design));
		}else{
			editTextPassword.setBackground(getResources().getDrawable(R.drawable.edit_text_design_focus));
			editTextLogin.setBackground(getResources().getDrawable(R.drawable.edit_text_design));
		}
	}

	private void connect(){
		Log.i("bug", "avant");
		/********************* Test du serveur et de la connexion internet ******************************/
		if(isNetworkConnected(context) && DatabaseHandler.isAvailableServer(context)){
			Log.i("bug", "apres");
			/* TODO A DECOMMENTER SI ON NE VEUT PAS UTILISER AUTHENTIFICATION*/
			EditText pwd = (EditText)findViewById(R.id.pwdTextEdit);
			EditText login = (EditText)findViewById(R.id.loginTextEdit);
			String myLogin = login.getText().toString();
			String password = pwd.getText().toString();

			Log.i("HSA", "entre "+ password);

			MCrypt mcrypt = new MCrypt();

			/* Encrypt */
			String encrypted=null;
			try {
				encrypted = MCrypt.bytesToHex( mcrypt.encrypt(password) );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String json = "[{\"username\":\""+myLogin+"\",\"password\":\""+encrypted+"\"}]";

			Log.i("HSA", " "+json);
			Log.i("HSA", " "+encrypted);

			DatabaseServer dbbs = new DatabaseServer();
			String reponse = dbbs.postRequest("login"," "+ json);
			Log.i("HSA", "REP: "+reponse);
			/*Vérifier la réponse et vérifier si on a un admin puis insérer l'admin*/
			/*
			 * Décommenter quand il ya ura bonne reponse
			 */
			MyJsonParser parser = new MyJsonParser(this);
			if(parser.reponseIsJson(reponse)){
				List<Client> logClient =parser.getClientFromJson(reponse);
				Client tmp =dataBase.getClientWithId(logClient.get(0).getId());
				if (tmp == null){
					DatabaseHandler.insertClient(logClient.get(0));
					Log.i("SERVER", "ON INSERE LE CLIENT");
					Intent intent = new Intent(this, ConcertActivity.class);
					Log.i("IDCLIENT", "id : " + logClient.get(0).getId());
					intent.putExtra("idClient", logClient.get(0).getId());
					this.startActivity(intent);
				}
				else{
					/*Comparer les mots de passe*/
					String paswUse;
					String paswBase;
					paswUse = logClient.get(0).getPassword();
					paswBase = dataBase.getClientWithId(logClient.get(0).getId()).getPassword();
					if (!paswUse.equals(paswBase)){
						Log.i("SERVER", "ON MODIFIE LE MOT DE PASSE ");
						dataBase.updatePassword(logClient.get(0),paswUse);
					}
					Intent intent = new Intent(this, ConcertActivity.class);
					Log.i("IDCLIENT", "id : " + logClient.get(0).getId());
					intent.putExtra("idClient", logClient.get(0).getId());
					this.startActivity(intent);
				}
			}else{
				Context myContext = getApplicationContext();
				CharSequence text = "ERROR LOGIN OR PASSWORD !";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(myContext, text, duration);
				TextView toastText = (TextView) toast.getView().findViewById(android.R.id.message);
				toastText.setTextColor(Color.RED);
				toast.setGravity(Gravity.TOP|Gravity.LEFT, 150, 600);
				toast.show();
			}
		}
		else{
			Log.i("bug", "apres else");
			/****************** AUTHENTIFICATION ***********************************/			

			EditText login = (EditText)findViewById(R.id.loginTextEdit);
			EditText pwd = (EditText)findViewById(R.id.pwdTextEdit);
			Log.i("LOGIN", login.getText().toString() + "  " + pwd.getText().toString());
			int idClient = dataBase.authentification(login.getText().toString(),pwd.getText().toString()); 
			if (idClient != -1){
				Intent intent = new Intent(this, ConcertActivity.class);
				Log.i("IDCLIENT", "id : " + idClient);
				intent.putExtra("idClient", idClient);
				this.startActivity(intent);
			}else{
				/*** ERREUR *************/
				Context myContext = getApplicationContext();
				CharSequence text = "ERROR LOGIN OR PASSWORD !";
				Toast toast = Toast.makeText(myContext, text, Toast.LENGTH_LONG);
				TextView toastText = (TextView) toast.getView().findViewById(android.R.id.message);
				toastText.setTextColor(Color.RED);
				toast.setGravity(Gravity.TOP|Gravity.LEFT, 150, 600);
				toast.show();
			}
		}

	} 
	
	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		if (b == buttonConnexion){
			connect();
		}
	}

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
		
	}
		
	/**
	 * Handler the icon showing the connection state
	 */
	private void lightHandler(){
		new Thread(new Runnable() { 
			public void run() {
				while(running){
					try {
						if (DatabaseHandler.isAvailableServer(context)){
							connectedToServer(0);
							Log.i("buglight", "OK");
						}
						else{
							connectedToServer(1);
							Log.i("bug", "NOTOK");
						}
						Thread.sleep(10 * 60 * 1000);
					} catch (InterruptedException e) {Log.i("bug", "Catch");}
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
					switch (lighted){
						case 0: item.setIcon(R.drawable.ic_action_location_found_green);break;
						case 1: item.setIcon(R.drawable.ic_action_location_found_red);break;
						default: item.setIcon(R.drawable.ic_action_refresh);break;
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
	
}
