package charts;

import java.util.HashMap;

import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;

import android.content.Context;
import android.util.Log;

public class ChartTariff extends Charts {


	public ChartTariff(Context context){
	    super(context);
	}
    /**
     * Creates a sample dataset.
     * @return a sample dataset.
     */
	
    public PieDataset createDataset(int[] values) {return null;}
    
	@Override
	public PieDataset createDataset(HashMap<String, Integer> values) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String tariff : values.keySet()){
	        dataset.setValue(tariff, Double.valueOf(values.get(tariff)));
		}
		return dataset;
	}

}
