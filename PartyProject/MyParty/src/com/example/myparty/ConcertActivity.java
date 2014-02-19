package com.example.myparty;

import lists.ConcertList;
import lists.ListLayout;
import lists.ReservationsList;
import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private Button buttonReservations ;
	private Button buttonAllConcerts ;
	private Button buttonNextConcerts ;
	private Button buttonNews ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private int index = 0;
	private int nextIndex = 0;
	private boolean client = true;
	
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
		if (client){
			this.view_flipper.addView(listReservations);	
		}
		this.view_flipper.addView(listAll);
		this.view_flipper.addView(listNext);
		this.view_flipper.addView(listNews);
		buttonReservations.setOnClickListener(this);
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		//decoItem.setIcon(R.drawable.ic_action_location_found_green);
		decoItem.setOnMenuItemClickListener(this);
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
		int offset = client ? 0 : -1 ;
		if (b == buttonReservations){
			nextIndex = offset;
			buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonAllConcerts){
			nextIndex = offset + 1;
			buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonNextConcerts){
			nextIndex = offset + 2; 
			buttonNextConcerts.setBackgroundResource(R.drawable.button_selected);
		}else{ 
			nextIndex = offset + 3;
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
		Intent intent = new Intent(this, ConnectionActivity.class);
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
