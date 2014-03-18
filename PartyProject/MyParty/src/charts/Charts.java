package charts;

import java.util.HashMap;

import lists.Items;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.PiePlot;
import org.afree.data.general.PieDataset;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;

import android.content.Context;
import android.graphics.Color;
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
        SolidColor white_color = new SolidColor(Color.WHITE);
        SolidColor black_color = new SolidColor(Color.BLACK);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelPaint(white_color);
        plot.setLabelFont(new Font("SansSerif", Typeface.NORMAL, 36));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        
        plot.setBackgroundPaintType(black_color);
        plot.setLabelBackgroundPaintType(black_color);
        plot.setLabelOutlinePaint(black_color);
        plot.setOutlinePaintType(black_color);
        plot.setBaseSectionPaintType(black_color);
        plot.setLabelLinkPaintType(white_color);
        
        plot.setLabelShadowPaint(black_color);
        plot.setInteriorGap(0);
        plot.setBaseSectionOutlinePaintType(black_color);
        chart.setBorderPaintType(black_color); 
        chart.setBorderVisible(false);
        this.setChart(chart);

    }
    
    public void updateDataset(PieDataset dataset){
    	AFreeChart chart = ChartFactory.createPieChart("", // chart title
                dataset, // data
                false, // include legend
                false,
                false);
    	this.setChart(chart);
    }

	@Override
	public void setVisible(boolean visible) {
		if (visible)
			this.setVisibility(VISIBLE);
		else
			this.setVisibility(INVISIBLE);
	}
    public abstract PieDataset createDataset(int[] values);
    public abstract PieDataset createDataset(HashMap<String, Double> values);

}
