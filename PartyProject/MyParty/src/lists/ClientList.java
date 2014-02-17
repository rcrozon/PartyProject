package lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.myparty.R;
import concert.Client;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ClientList extends ListView{
		
	    private Adapter mAdapter;
	    private ArrayList<Items> items = new ArrayList<Items>();
		
		public ClientList(Context context,List<Client> cl) {
			super(context);
			/*Test*/
			for (int i=0;i<cl.size();i++){
				ClientItem testAff = new ClientItem(context, cl.get(i));
				items.add(testAff);
			}
			/*FIN TEST*/
			
			mAdapter = new Adapter(context, items);
	        this.setAdapter(mAdapter);
	        this.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View view, int pos,long id) {
	                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
	                
	            }
	        }); 
			this.setBackgroundColor(getResources().getColor(R.color.black));
			
		}

	}


