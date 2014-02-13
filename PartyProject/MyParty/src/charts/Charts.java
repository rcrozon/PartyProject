package charts;

import lists.Items;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PiePlot;
import org.afree.data.general.PieDataset;
import org.afree.graphics.geom.Font;

import android.content.Context;
import android.graphics.Typeface;

public abstract class Charts extends DemoView implements Items {
	
	public Charts(Context context) {
		super(context);
	}

	/**
     * Creates a chart.
     * @param dataset the dataset.
     * @return a chart.
     */
    public void createChart(PieDataset dataset) {

        AFreeChart chart = ChartFactory.createPieChart("", // chart title
										                dataset, // data
										                false, // include legend
										                false,
										                false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        this.setChart(chart);

    }
    
    public abstract PieDataset createDataset(int[] values);

}
