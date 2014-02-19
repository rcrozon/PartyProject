package lists;

import android.content.Context;
import android.widget.LinearLayout;

public class ListLayout extends LinearLayout {

	public ListLayout(Context context, List list){
		super(context);
		this.setOrientation(VERTICAL);
//		ConcertList listConcert = new ConcertList(context);
		Research researchBar = new Research(context, list);
		this.addView(researchBar);
		this.addView(list);
	}
}
