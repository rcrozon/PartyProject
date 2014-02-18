package lists;

import android.content.Context;
import android.widget.LinearLayout;

public class ClientListLayout extends LinearLayout {

	public ClientListLayout(Context context){
		super(context);
		this.setOrientation(VERTICAL);
		ClientList clientList = new ClientList(context); 
		Research researchBar = new Research(context, clientList);
		this.addView(researchBar);
		this.addView(clientList);
	}
}
