package lists;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import charts.ChartPersonsIn;
import charts.ChartTarif;
import charts.ChartTicketsSold;

public class Adapter extends BaseAdapter
{ 
	private ArrayList<Items> listItems;
	
	public Adapter(Context context, ArrayList<Items> arrayList){
		listItems = arrayList;
	}

	@Override
	public int getCount(){
		return listItems.size();
	}

	@Override
	// get the data of an item from a specific position
	// it represents the position of the item in the list
	public Object getItem(int i){
		return listItems.get(i);
	}

	@Override
	// get the position id of the item from the list
	public long getItemId(int i){
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup)
	{
		if (listItems.get(position) instanceof ConcertItem)
			return (ConcertItem)listItems.get(position);
		else if (listItems.get(position) instanceof ClientItem)
			return (ClientItem)listItems.get(position);
		else if (listItems.get(position) instanceof ChartPersonsIn){
			Log.i("INSTANCE", listItems.get(position).getClass().getName());	
			return (ChartPersonsIn)listItems.get(position);
		}
		else if (listItems.get(position) instanceof ChartTarif)
			return (ChartTarif)listItems.get(position);
		else if (listItems.get(position) instanceof ChartTicketsSold)
			return (ChartTicketsSold)listItems.get(position);
		else
			return null;
	}
}
