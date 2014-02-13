package charts;

import java.util.HashMap;

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
        dataset.setValue("Sold", Double.valueOf(values[0]));
        dataset.setValue("Unsold", Double.valueOf(values[1]));
        return dataset;
    }

	@Override
	public PieDataset createDataset(HashMap<String, Integer> values) {
		// TODO Auto-generated method stub
		return null;
	}

    
}