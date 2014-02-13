package charts;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PiePlot;
import org.afree.chart.title.TextTitle;
import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;
import org.afree.graphics.geom.Font;
import android.content.Context;
import android.graphics.Typeface;

public class ChartTicketsSold extends DemoView implements Charts{

    /**
     * constructor
     * @param context
     */
    public ChartTicketsSold(Context context) {
        super(context);
    }

    /**
     * Creates a sample dataset.
     * @return a sample dataset.
     */
    public PieDataset createDataset(int sold, int unsold) {
    	DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Sold", sold);
        dataset.setValue("Unsold", unsold);
        return dataset;
    }

    /**
     * Creates a chart.
     * @param dataset the dataset.
     * @return a chart.
     */
    public void createChart(PieDataset dataset) {

        AFreeChart chart = ChartFactory.createPieChart("Tickets", // chart title
										                dataset, // data
										                true, // include legend
										                true,
										                false);
//        TextTitle title = chart.getTitle();
//        title.setToolTipText("A title tooltip!");

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 12));
//        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        this.setChart(chart);

    }
}