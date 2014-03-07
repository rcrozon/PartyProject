package lists;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ListView;

public abstract class List extends ListView {

	protected ArrayList<Items> items;
	
	public List(Context context) {
		super(context);
	}
	
	public ArrayList<Items> getItems(){
		return items;
	}
}
