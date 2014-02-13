package charts;

import org.afree.data.general.DefaultPieDataset;
import org.afree.data.general.PieDataset;

import android.content.Context;

public class ChartTarif extends Charts {


	public ChartTarif(Context context){
	    super(context);
	}
    /**
     * Creates a sample dataset.
     * @return a sample dataset.
     */
	
    public PieDataset createDataset(int[] values) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int tariff : values) {
        	dataset.setValue("Tarrif", Double.valueOf(tariff));
        }
        return dataset;
    }

}
