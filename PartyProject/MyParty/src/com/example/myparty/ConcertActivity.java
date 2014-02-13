package com.example.myparty;

import java.util.Date;

import concert.Concert;
import lists.ClientList;
import lists.ConcertItem;
import lists.ConcertList;
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
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class ConcertActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	Button buttonAllConcerts ;
	Button buttonNextConcerts ;
	Button buttonNews ;
	ViewFlipper view_flipper ;
	private MenuItem decoItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concerts);
		buttonAllConcerts = (Button)findViewById(R.id.buttonAllConcerts);
		buttonNews = (Button)findViewById(R.id.buttonNews);
		buttonNextConcerts = (Button)findViewById(R.id.buttonNextConcerts);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
		this.view_flipper.addView(new ConcertList(this));
		this.view_flipper.addView(new ConcertList(this));
		this.view_flipper.addView(new ConcertList(this));
		buttonAllConcerts.setOnClickListener(this);
		buttonNews.setOnClickListener(this);
		buttonNextConcerts.setOnClickListener(this);
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
		buttonAllConcerts.setBackgroundResource(R.drawable.button_unselected);
		buttonNews.setBackgroundResource(R.drawable.button_unselected);
		buttonNextConcerts.setBackgroundResource(R.drawable.button_unselected);
		if (b == buttonAllConcerts){
			nextIndex = 0;
			buttonAllConcerts.setBackgroundResource(R.drawable.button_selected);
		}else if (b == buttonNextConcerts){
			nextIndex = 1; 
			buttonNextConcerts.setBackgroundResource(R.drawable.button_selected);
		}else{ 
			nextIndex = 2;
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
	
		Intent intent = new Intent(this, MainActivity.class);
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
