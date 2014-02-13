package lists;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import charts.ChartPersonsIn;
import charts.ChartTarif;
import charts.ChartTicketsSold;
import charts.Charts;

import com.example.myparty.R;

import concert.Client;

public class StatsList extends LinearLayout {

	Charts chartTicketSold ; 
	Charts chartTarif ;
	Charts chartPersonsIn ;
	LinearLayout layout ;
	LinearLayout personslegend;
	Adapter adapter ;
	ScrollView scrollStats;
	
	public StatsList(Context context) {
		super(context);
		ScrollView scrollStats = new ScrollView(context);
		
		layout = new LinearLayout(context); 
		layout.setOrientation(LinearLayout.VERTICAL);
		
		personslegend = new LinearLayout(context); 
		personslegend.setOrientation(LinearLayout.HORIZONTAL);
		
		chartTicketSold = new ChartTicketsSold(context);
		chartTarif = new ChartTarif(context); 
		chartPersonsIn = new ChartPersonsIn(context);
		int tariffs[] = {15, 24, 5};  
		int tickets[] = {8, 12};  
		int persons[] = {52, 12}; 
		chartTicketSold.createChart(chartTicketSold.createDataset(tickets));
		chartTarif.createChart(chartTarif.createDataset(tariffs));
		chartPersonsIn.createChart(chartPersonsIn.createDataset(persons));

		chartPersonsIn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,500));
		chartTicketSold.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,500));
		chartTarif.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,500));
		
		layout.addView(chartPersonsIn);
		layout.addView(chartTicketSold);
		layout.addView(chartTarif);
		
		scrollStats.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		scrollStats.setFillViewport(true);
		scrollStats.setBackgroundResource(R.drawable.list_border);
		scrollStats.addView(layout);
		this.addView(scrollStats);
	}

}
