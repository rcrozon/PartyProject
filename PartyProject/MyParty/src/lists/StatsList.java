package lists;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.Gravity;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import charts.ChartPersonsIn;
import charts.ChartTariff;
import charts.ChartTicketsSold;
import charts.Charts;

import com.example.myparty.R;

import databaseHandler.DatabaseHandler;
import entities.Client;

public class StatsList extends LinearLayout {

	private Charts chartTicketSold ; 
	private Charts chartTariff ;
	private Charts chartPersonsIn ; 
	private LinearLayout layout ;
	private Adapter adapter ;
	private ScrollView scrollStats;
	private DatabaseHandler database;
	
	public StatsList(Context context, int idConcert) {
		super(context);
		ScrollView scrollStats = new ScrollView(context);
		
		layout = new LinearLayout(context); 
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,500, Gravity.CENTER_HORIZONTAL));
		
		database = new DatabaseHandler(context);
		database.open();
		
		chartTicketSold = new ChartTicketsSold(context);
		chartTariff = new ChartTariff(context); 
		chartPersonsIn = new ChartPersonsIn(context);
		HashMap<String, Double> tariffs = database.getTariffsFromConcert(idConcert);
		int cptIn = 0, cptOut = 0;
		int tickets[] = {8, 12};
		int persons[] = {cptIn, cptOut};
		chartTicketSold.createChart(chartTicketSold.createDataset(tickets));
		chartTariff.createChart(chartTariff.createDataset(tariffs));
		chartPersonsIn.createChart(chartPersonsIn.createDataset(persons));
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,600);
		chartPersonsIn.setLayoutParams(lp);
		chartTicketSold.setLayoutParams(lp);
		chartTariff.setLayoutParams(lp);
		 
		lp.setMargins(20, 20, 20, 20);
		
		layout.addView(chartPersonsIn);
		layout.addView(chartTicketSold);
		layout.addView(chartTariff);
		
		scrollStats.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		scrollStats.addView(layout);
		this.addView(scrollStats);
	}

}
