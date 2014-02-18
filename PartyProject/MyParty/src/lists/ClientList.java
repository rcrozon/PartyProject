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


public class ClientList extends lists.List {
		
    private Adapter adapter;
    	
	public ClientList(Context context,List<Client> cl) {
		super(context);
		items = new ArrayList<Items>();
		/*TODO A Decommenter pour bdd interne*/
		/*for (int i=0;i<cl.size();i++){
			ClientItem testAff = new ClientItem(context, cl.get(i));
			items.add(testAff);
		}*/
		/*FIN TEST*/
		for (int i=0;i<15;i++){
			Client test = new Client(i,"Prenom"+i, "Nom"+i,new Date(), i+"@labri.fr", "login"+i, "passe"+i);
			ClientItem testAff = new ClientItem(context, test);
			items.add(testAff);
		}
		
		adapter = new Adapter(items);
        this.setAdapter(adapter);
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,long id) {
                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                
            }
        }); 
		this.setBackgroundColor(getResources().getColor(R.color.black));
	}
	
	public ArrayList<Items> getItems(){
		return items;
	}

}


