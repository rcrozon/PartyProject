package databaseHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import databaseHandler.DatabaseHandler;
import databaseHandler.DatabaseServer;
import databaseHandler.MyJsonParser;
import databaseHandler.Tables;
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
import android.widget.ViewFlipper;

public class ThreadTestServer extends Thread {
		
		    private Boolean result; 
		    private Context context;
		    
		    public ThreadTestServer(Context context){
		    	super();
		    	this.context = context;
		    }
		    
		    public Boolean getResult() {
		       return result;
		    }
		    
		    public void setResult(Boolean result){
		    	this.result = result;
		    }
		    
		    public void run(){
		    	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    	NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    if (netInfo != null && netInfo.isConnected()) {
			        try {
			            URL url = new URL("http://anthony.flavigny.emi.u-bordeaux1.fr");   // Change to "http://google.com" for www  test.
			            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
			            urlc.setConnectTimeout(10 * 1000);          // 10 s.
			            urlc.connect();
			            Log.i("PING", ""+urlc.getResponseCode());
			            if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
			               Log.i("PING", "PING OK");
			               setResult(true);
			            } else {
			            	  Log.i("PING", "PING NOT OK");
			            	  setResult(false);
			            }
			        } catch (MalformedURLException e1) {
			        	Log.i("PING", "PING NOT OK");
			        	 setResult(false);
			        } catch (IOException e) {
			        	Log.i("PING", "PING NOT OK");
			        	 setResult(false);
			        }
		    	
		    	
		    }
		  
	}
}



