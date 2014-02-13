package com.example.myparty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lists.ClientAdapter;
import lists.ClientItem;

import concert.Client;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ClientListActivity extends Activity {
	
	private List<ClientItem> clientList = new ArrayList<ClientItem>();
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_list);
		Date d = new Date(100000);
		for (int i=0;i<15;i++){
			Client test = new Client("Prenom"+i, "Nom"+i,d, i+"@labri.fr", "login"+i, "passe"+i);
			ClientItem testAff = new ClientItem(this, test);
			clientList.add(testAff);
		}
		/*Client test = new Client("Jean", "Dupont",d, "test@labri.fr", "jeannot", "passe");
		ClientItem testAff = new ClientItem(this, test);
		clientList.add(testAff);
		Client test2 = new Client("Pierre", "Durand",d, "ok@labri.fr", "pierro", "oui");
		ClientItem testAff2 = new ClientItem(this, test2);
		clientList.add(testAff2);*/
		ClientAdapter adapter = new ClientAdapter(this, clientList);
		listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_list, menu);
		return true;
	}

}
