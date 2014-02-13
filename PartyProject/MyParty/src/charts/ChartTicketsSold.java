package charts;

import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;

import android.content.Context;

public class ChartTicketsSold extends Charts{

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
    public PieDataset createDataset(int[] values) {
    	DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Sold", values[0]);
        dataset.setValue("Unsold", values[1]);
        return dataset;
    }

    
}