package lists;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ConcertListLayout extends LinearLayout {

	public ConcertListLayout(Context context){
		super(context);
		this.setOrientation(VERTICAL);
		ConcertList listConcert = new ConcertList(context);
		Research researchBar = new Research(context, listConcert);
		this.addView(researchBar);
		this.addView(listConcert);
	}
}
