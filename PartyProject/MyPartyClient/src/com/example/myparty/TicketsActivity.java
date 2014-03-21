package com.example.myparty;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import databaseHandler.DatabaseHandler;
import databaseHandler.Tables;
import databaseHandler.ThreadBitMap;
import entities.Concert;
import entities.TicketItem;
import entities.TicketUnitaire;

public class TicketsActivity extends Activity implements OnMenuItemClickListener, OnClickListener {

	private MenuItem decoItem;
	private DatabaseHandler dataBase;
	private LinearLayout layoutButtons;
	private RelativeLayout layoutMain;
	private ViewFlipper viewFlipper;
	private RadioGroup radioGroup ;
	private TextView textNbTickets;
	private Button buttonPrev;
	private Button buttonNext;
	private MenuItem connectedItem;
	private int nbTickets ;
	private int numCurrentTickets = 1 ;
    private float lastX;
    private int idClient ;
    private Context context;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_tickets);
		layoutButtons = (LinearLayout)findViewById(R.id.layoutButtons);
		layoutMain = (RelativeLayout)findViewById(R.id.layoutMain);
		viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		textNbTickets = (TextView)findViewById(R.id.textNbTickets);
		buttonNext = (Button)findViewById(R.id.buttonNext);
		buttonPrev = (Button)findViewById(R.id.buttonPrev);
		
		/****************** OUVERTURE BDD ***********************************/
		
		this.dataBase = new DatabaseHandler(this);
		this.dataBase.open();
		
		/****************** RECUPERATION DE L'ID DU CONCERT *****************/
		buttonNext.setOnClickListener(this);
		buttonPrev.setOnClickListener(this);
		radioGroup.setEnabled(false);
		Bundle b = getIntent().getExtras();
		Concert concert = null;

        concert = DatabaseHandler.getConcertWithId(b.getInt("idConcert"));
        idClient = b.getInt("idClient");
        ///////////////////////////////////////////////////
        ArrayList<TicketUnitaire> tickets = dataBase.getTicketClient(idClient, b.getInt("idConcert"));
        ArrayList<TicketItem> ticketsListItem = new ArrayList<TicketItem>();
        for(TicketUnitaire ticket : tickets){
        	ticketsListItem.add(new TicketItem(this, ticket, idClient));
        }
        
        
        nbTickets = ticketsListItem.size();
        Log.i("NBTICKET", "nb " + nbTickets);
        if (nbTickets > 0 && nbTickets <= 10){
        	radioGroup.setVisibility(View.VISIBLE);
        	layoutButtons.setVisibility(View.GONE);
        	for(int i = 0; i < nbTickets; ++i){
        		RadioButton radio = new RadioButton(this);
        		//layoutInfos.addView(tickets.get(i), 0);
        		this.radioGroup.addView(radio);
        		this.viewFlipper.addView(ticketsListItem.get(i));
        		//createQRCode(concert, i);
        	}
        	changeRadioSelection(0);
        }else if (nbTickets > 10){
        	radioGroup.setVisibility(View.GONE);
        	layoutButtons.setVisibility(View.VISIBLE);
        	textNbTickets.setText("1 / " + nbTickets);
        	for(int i = 0; i < nbTickets; ++i){
        		this.viewFlipper.addView(ticketsListItem.get(i));
        	}
        }else{
        	layoutButtons.setVisibility(View.VISIBLE);
        	radioGroup.setVisibility(View.GONE);
        	textNbTickets.setText("No tickets availables");
        }
        this.viewFlipper.setDisplayedChild(0);

        ThreadBitMap t = new ThreadBitMap(Tables.IMG_PATH_SERVER + concert.getImagePath());
		t.start();
		try { 
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BitmapDrawable bitmap = new BitmapDrawable(t.getResult());
        this.layoutMain.setBackground(bitmap);
		
	}

	private void changeRadioSelection(int index){
		((RadioButton)this.radioGroup.getChildAt(index)).setChecked(true);
	}
	/*
	private void createQRCode(Concert concert, int idTicket){
		String stringQRCode = idTicket + ";" + concert.getId() + ";3;5";
		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(stringQRCode, 
														null, 
														Contents.Type.TEXT,
														BarcodeFormat.QR_CODE.toString(), 
														100);
		try {
			Bitmap bitmapQRCode = qrCodeEncoder.encodeAsBitmap();
			Drawable drawable = new BitmapDrawable(getResources(), bitmapQRCode);
			imgFlashCode.setBackground(drawable);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	*/
	public boolean onTouchEvent(MotionEvent touchevent){
    	switch (touchevent.getAction()) {
        // when user first touches the screen to swap
             case MotionEvent.ACTION_DOWN:  {
                 lastX = touchevent.getX();
                 break;
             }
             case MotionEvent.ACTION_UP:  {
                 float currentX = touchevent.getX();
                 // if left to right swipe on screen
                 if (lastX < currentX)  {
                	 // If no more View/Child to flip
                	 if (viewFlipper.getDisplayedChild() == 0)
                         break;
                     // set the required Animation type to ViewFlipper
                     // The Next screen will come in form Left and current Screen will go OUT from Right 
            
        	 		 viewFlipper.setInAnimation(this, R.anim.in_from_left);
                     viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                     // Show the next Screen
                     changeRadioSelection(viewFlipper.getDisplayedChild()-1);
         			 viewFlipper.showPrevious();
                 }
                 // if right to left swipe on screen
                 if (lastX > currentX) {
                     if (viewFlipper.getDisplayedChild() == nbTickets-1)
                         break;
                     // set the required Animation type to ViewFlipper
                     // The Next screen will come in form Right and current Screen will go OUT from Left 
                     viewFlipper.setInAnimation(this, R.anim.in_from_right);
                     viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                     // Show The Previous Screen
                     changeRadioSelection(viewFlipper.getDisplayedChild()+1);
                     viewFlipper.showNext();
                 }
                 break;
             }
    	}
    	return false;
    }

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.connected, menu);
		this.decoItem = menu.findItem(R.id.menu_deconnect);
		// decoItem.setIcon(R.drawable.logout);
		this.connectedItem = menu.findItem(R.id.menu_refresh); 
		if (DatabaseHandler.isNetworkConnected(this)){
			connectedToServer(0);
		}
		this.decoItem.setOnMenuItemClickListener(this);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent;
		intent = new Intent(this, ConnectionActivity.class);
		this.startActivity(intent);
		return false;
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Button b = (Button)v;
		if (b == buttonPrev){
			Toast.makeText(this, "OnCLick", Toast.LENGTH_SHORT).show();
			if (this.viewFlipper.getDisplayedChild() != 0){
				numCurrentTickets--;
		 		viewFlipper.setInAnimation(this, R.anim.in_from_left);
	            viewFlipper.setOutAnimation(this, R.anim.out_to_right);
				viewFlipper.showPrevious();
			}
		}else{
			if (this.viewFlipper.getDisplayedChild() != nbTickets-1){
				numCurrentTickets++;
				viewFlipper.setInAnimation(this, R.anim.in_from_right);
	            viewFlipper.setOutAnimation(this, R.anim.out_to_left);
				viewFlipper.showNext();
			}
		}
    	textNbTickets.setText(numCurrentTickets + " / " + nbTickets);
		
	}
	

	private void loadDatabase(){
		new Thread(new Runnable() { 
			@Override
			public void run() {
				if (DatabaseHandler.updateAllTables(context, idClient)){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							connectedToServer(0); 
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
						}
					});
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
}