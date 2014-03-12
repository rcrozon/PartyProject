package com.example.myparty;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import QRCode.Contents;
import QRCode.QRCodeEncoder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import entities.Reservation;
import entities.Ticket;
import entities.TicketItem;

public class TicketsActivity extends Activity implements OnMenuItemClickListener, OnClickListener {

	private MenuItem decoItem;
	private DatabaseHandler dataBase;
	private LinearLayout layoutButtons;
	private LinearLayout layoutInfos;
	private RelativeLayout layoutMain;
	private ViewFlipper viewFlipper;
	private RadioGroup radioGroup ;
	private TextView textNbTickets;
	private TextView textStyle;
	private TextView textArtists;
	private TextView textEndDate;
	private TextView textBeginDate;
	private TextView textTitle;
	private TextView textTariff; 
	private Button buttonPrev;
	private Button buttonNext;
	private int nbTickets ;
	private int numCurrentTickets = 1 ;
    private float lastX;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tickets);
		layoutButtons = (LinearLayout)findViewById(R.id.layoutButtons);
		layoutMain = (RelativeLayout)findViewById(R.id.layoutMain);
		layoutInfos = (LinearLayout)findViewById(R.id.layoutInfos);
		viewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
		textNbTickets = (TextView)findViewById(R.id.textNbTickets);
		textStyle = (TextView)findViewById(R.id.textStyle);
		textArtists = (TextView)findViewById(R.id.textArtists);
		textBeginDate = (TextView)findViewById(R.id.textBeginDate);
		textEndDate = (TextView)findViewById(R.id.textEndDate);
		textTitle = (TextView)findViewById(R.id.textTitle);
		textTariff = (TextView)findViewById(R.id.textTariff);
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

        concert = dataBase.getConcertWithId(b.getInt("idConcert"));
        int idClient = b.getInt("idClient");
        ///////////////////////////////////////////////////
        ArrayList<Ticket> tickets = dataBase.getTicketClient(idClient);
        ArrayList<TicketItem> ticketsListItem = new ArrayList<TicketItem>();
        for(Ticket ticket : tickets){
        	ticketsListItem.add(new TicketItem(this, ticket, idClient));
        }
//        HashMap<String, Double> hmap = DatabaseHandler.getTariffsFromConcert(concert.getId());
        ArrayList<String> artistsList = DatabaseHandler.getArtistsFromConcert(concert.getId());
        ArrayList<String> stylesList = DatabaseHandler.getStylesFromConcert(concert.getId());
        ArrayList<String> tariffsList = new ArrayList<String>();
        for(Ticket ticket : tickets){
        	tariffsList.add(DatabaseHandler.getLabelById(ticket.getIdTariff()));
        }
        textArtists.setText("Artists : ");
        for(int i = 0; i < artistsList.size(); ++i){
        	textArtists.setText(textArtists.getText() + artistsList.get(i) + " ");
        }
        textStyle.setText("Styles : ");
        for(int i = 0; i < artistsList.size(); ++i){
        	textStyle.setText(textStyle.getText() + stylesList.get(i) + " ");
        }
        textBeginDate.setText("Begin date : " + concert.getBeginDate());
        textEndDate.setText("End date : "+ concert.getEndDate());
        textTitle.setText("Title : " + concert.getTitle());
        
        nbTickets = ticketsListItem.size();
        if (nbTickets > 0 && nbTickets <= 10){
        	radioGroup.setVisibility(View.VISIBLE);
        	layoutButtons.setVisibility(View.GONE);
        	for(int i = 0; i < nbTickets; ++i){
        		RadioButton radio = new RadioButton(this);
        		//layoutInfos.addView(tickets.get(i), 0);
        		this.radioGroup.addView(radio);
                textTariff.setText("Tariff : " + tariffsList.get(i));
        		this.viewFlipper.addView(ticketsListItem.get(i), 0);
        		//createQRCode(concert, i);
        	}
        	changeRadioSelection(0);
        }else if (nbTickets > 10){
        	radioGroup.setVisibility(View.GONE);
        	layoutButtons.setVisibility(View.VISIBLE);
        	textNbTickets.setText("1 / " + nbTickets);
        	for(int i = 0; i < nbTickets; ++i){
                textTariff.setText("Tariff : " + tariffsList.get(i));
        		this.viewFlipper.addView(ticketsListItem.get(i), 0);
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
		this.decoItem = menu.findItem(R.id.menu_deconect);
		// decoItem.setIcon(R.drawable.logout);
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
}