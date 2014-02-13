package charts;

import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;

import android.content.Context;

public class ChartPersonsIn extends Charts{


	public ChartPersonsIn(Context context){
	    super(context);
    }
    /**
     * Creates a sample dataset.
     * @return a sample dataset.
     */
	public PieDataset createDataset(int[] values) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("In", values[0]);
        dataset.setValue("Out", values[1]);
        return dataset;
    }
}
