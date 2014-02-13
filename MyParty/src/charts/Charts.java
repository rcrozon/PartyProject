package charts;

import lists.Items;

import org.afree.chart.AFreeChart;
import org.afree.data.general.PieDataset;

public interface Charts extends Items {
	
    public void createChart(PieDataset dataset);

}
