package charts;

import java.util.HashMap;

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
        dataset.setValue("In", Double.valueOf(values[0]));
        dataset.setValue("Out", Double.valueOf(values[1]));
        return dataset;
    }
	@Override
	public PieDataset createDataset(HashMap<String, Integer> values) {return null;}
	
}
