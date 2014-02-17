package com.example.myparty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import databaseHandler.DatabaseHandler;
import databaseHandler.Tables;
import databaseHandler.UserFunctions;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonConnexion ;
	private Button buttonRegister ;
	private MenuItem item;
	private UserFunctions userFunctions = new UserFunctions();
	private boolean running = true;
	private DatabaseHandler dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonConnexion = (Button)findViewById(R.id.buttonConnexion);
		buttonRegister = (Button)findViewById(R.id.buttonRegister);
		buttonConnexion.setOnClickListener(this);
		buttonRegister.setOnClickListener(this);
		lightHandler();
		/*TODO A décommenter pour authentification*/
		//dataBase = new DatabaseHandler(this);
		//dataBase.open();
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
		if (v == buttonConnexion){
			
			Intent intent = new Intent(this, ConcertActivity.class);
	    	this.startActivity(intent);
			
	    	/* TODO A DECOMMENTER POUR POUVOIR UTILISER AUTHENTIFICATION avec BDD interne*/
			/*EditText login = (EditText)findViewById(R.id.loginTextEdit);
			EditText pwd = (EditText)findViewById(R.id.pwdTextEdit);
			
			Log.i("TEST", login.getText().toString());
			Log.i("TEST", pwd.getText().toString());
			
			if (dataBase.authentification(login.getText().toString(), pwd.getText().toString())){
				Intent intent = new Intent(this, ConcertActivity.class);
		    	this.startActivity(intent);
			}
			else{
				//MESSAGE ERREUR
				Context myContext = getApplicationContext();
				CharSequence text = "ERROR LOGIN OR PASSWORD !";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(myContext, text, duration);
				toast.setGravity(Gravity.TOP|Gravity.LEFT, 150, 600);
				toast.show();
			}*/
		
		}else {
			Intent intent = new Intent(this, RegisterActivity.class);
	    	this.startActivity(intent);
		}
	}
	
	/**
	 * Handler the icon showing the connection state
	 */
	private void lightHandler(){
		new Thread(new Runnable() {
	        public void run() {
            	while(running){
            		if (userFunctions.isUserLoggedIn())
            			connectedToServer(0);
            		else
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
	
}
