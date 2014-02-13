package com.example.myparty;

import lists.ClientList;
import lists.StatsList;
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
import android.widget.ScrollView;
import android.widget.ViewFlipper;

public class ConcertDetailsActivity extends Activity implements OnClickListener,OnMenuItemClickListener{

	Button buttonClients ;
	Button buttonDetails ;
	Button buttonScan ;
	Button buttonStats ;
	ViewFlipper view_flipper ;
	MenuItem decoItem;
	ScanLayout scanner;
	ScrollView scrollScan ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		Log.i("PASSE", "PAR ONCREATE MAINACTIVITY");
		setContentView(R.layout.activity_concert_details);
		
		scrollScan = new ScrollView(this); 
		buttonClients = (Button)findViewById(R.id.buttonClient);
		buttonDetails = (Button)findViewById(R.id.buttonDetails);
		buttonScan = (Button)findViewById(R.id.buttonScan);
		buttonStats = (Button)findViewById(R.id.buttonStats);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
	
		scanner = new ScanLayout(this,  this);
		scrollScan.addView(scanner);
		this.view_flipper.addView(new ClientList(this));
		this.view_flipper.addView(new ClientList(this));
		this.view_flipper.addView(scrollScan);
		this.view_flipper.addView(new StatsList(this));
		buttonClients.setOnClickListener(this);
		buttonDetails.setOnClickListener(this);
		buttonScan.setOnClickListener(this);
		buttonStats.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		decoItem.setIcon(R.drawable.ic_action_location_found_green);
		decoItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		int index = view_flipper.getDisplayedChild();
		int nextIndex ;
		buttonClients.setBackgroundResource(R.drawable.button_unselected);
		buttonDetails.setBackgroundResource(R.drawable.button_unselected);
		buttonScan.setBackgroundResource(R.drawable.button_unselected);
		buttonStats.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonDetails){
			nextIndex = 0;
			buttonDetails.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonClients){
			nextIndex = 1;
			buttonClients.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonScan){
			nextIndex = 2;
			buttonScan.setBackgroundResource(R.drawable.button_selected);
		}else{
			nextIndex = 3; 
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
			scanner.getImageView().setBackgroundResource(R.drawable.qrcode_green);
		}
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		return false;
	}	
}