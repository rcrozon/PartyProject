package lists;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myparty.R;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.RelativeLayout.LayoutParams;
import charts.ChartPersonsIn;
import charts.ChartTarif;
import charts.ChartTicketsSold;

public class StatsList extends LinearLayout {

	Adapter adapter ;
	ChartTicketsSold chartTicketSold ; 
	ChartTarif chartTarif ;
	ChartPersonsIn chartPersonsIn ;
	LinearLayout layout ;
	
	public StatsList(final Context context) {
		super(context);
		layout = new LinearLayout(context); 
		layout.setOrientation(LinearLayout.VERTICAL);
		chartTicketSold = new ChartTicketsSold(context);
		chartTarif = new ChartTarif(context); 
		chartPersonsIn = new ChartPersonsIn(context);
		HashMap<String , Integer> tariffs = new HashMap<String, Integer>();
		tariffs.put("Etudiant", 15);
		tariffs.put("Normal", 24);
		tariffs.put("Sénior", 5); 
		chartTicketSold.createChart(chartTicketSold.createDataset(8, 12));
		chartTarif.createChart(chartTarif.createDataset(tariffs));
		chartPersonsIn.createChart(chartPersonsIn.createDataset(52, 12));
		LayoutParams llp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT);
		chartPersonsIn.setLayoutParams(llp);
		chartTicketSold.setLayoutParams(llp);
		chartTarif.setLayoutParams(llp);
		//this.setAdapter(adapter);
	
		layout.addView(chartPersonsIn);
		chartPersonsIn.setBackgroundResource(R.drawable.list_border);
		layout.addView(chartTarif);
//		layout.addView(chartTicketSold);
//		this.setBackgroundResource(R.drawable.list_border);
		this.addView(layout);
	}

}
