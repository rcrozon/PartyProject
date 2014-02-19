package com.example.myparty;

import java.util.Date;

import lists.ClientListLayout;
import lists.ConcertDetailed;
import lists.StatsList;
import scan.IntentIntegrator;
import scan.IntentResult;
import scan.ScanLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import concert.Concert;
import databaseHandler.DatabaseHandler;

public class ConcertDetailsActivity extends Activity implements OnClickListener,OnMenuItemClickListener{

	private Button buttonClients ;
	private Button buttonDetails ;
	private Button buttonScan ;
	private Button buttonStats ;
	private ViewFlipper view_flipper ;
	private MenuItem decoItem;
	private ScanLayout scanner;
	private ScrollView scrollScan ;
	private ImageView imgView ;
	private TextView textTitle;
	private TextView textDate;
	private TextView textFull;
	private TextView textLocation;
	private TextView textNbSeets;
	private TextView textPrice;
	private DatabaseHandler dataBase;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_concert_details);
		
		scrollScan = new ScrollView(this); 
		buttonClients = (Button)findViewById(R.id.buttonClient);
		buttonDetails = (Button)findViewById(R.id.buttonDetails);
		buttonScan = (Button)findViewById(R.id.buttonScan);
		buttonStats = (Button)findViewById(R.id.buttonStats);
		view_flipper = (ViewFlipper)findViewById(R.id.view_flipper);
		
//		dataBase = new DatabaseHandler(this);
//		dataBase.open();
		
		//dataBase.deleteAll();
		//Log.i("Concert", "Concert1 : " + dataBase.getConcertWithId(1).toString());
		
		//R�cup�ration des extras
		Bundle b = getIntent().getExtras();
		//Concert sur lequel on a appuyé
		Concert concert = new Concert(b.getInt("id"),b.getString("imgPath"), 
				b.getString("title"), "10/12/14", "11/12/14", b.getString("location"),
				b.getInt("nbSeets"), 0);
		
		//Récupération de la liste des clients pour ce concert
		/*TODO A decommenter pour BDD interne*/
		/*List<Client> clientForConcert = dataBase.getClientForOneConcert(concert.getId());*/
		//List<Client> clientForConcert = new ArrayList<Client>();
	
		//Concert concert = new Concert("", "Francofolie", new Date(), new Date(), "La Rochelle", 10.5, 14000, false);
		scanner = new ScanLayout(this, this);
		scrollScan.addView(scanner);
		

		this.scanner.getButtonTariff().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_blue);	
			}
		});

		ClientListLayout clientList = new ClientListLayout(this); 
		this.view_flipper.addView(new ConcertDetailed(this, concert));
		this.view_flipper.addView(clientList);
		this.view_flipper.addView(scrollScan);
		this.view_flipper.addView(new StatsList(this));
		this.buttonClients.setOnClickListener(this);
		this.buttonDetails.setOnClickListener(this);
		this.buttonScan.setOnClickListener(this);
		this.buttonStats.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		decoItem = menu.findItem(R.id.menu_deconect);
		//decoItem.setIcon(R.drawable.logout);
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
			if (codeDatabaseHandler())
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_green);
			else
				scanner.getImageView().setBackgroundResource(R.drawable.qrcode_red);
			
		}
	}
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = new Intent(this, ConnectionActivity.class);
		this.startActivity(intent);
		return false;
	}	
	/**
	 * Test in the database if the given QR code is correct and if the client's reservation is recorded
	 * @return
	 */
	public boolean codeDatabaseHandler(){
		// TODO when the database is done
		scanner.getButtonTariff().setText("Carte Etudiante n���cessaire");
		return true;
	}
}