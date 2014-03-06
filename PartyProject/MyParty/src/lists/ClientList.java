package lists;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.myparty.R;

import entities.Client;
import entities.ClientItem;
import entities.Concert;


public class ClientList extends List {
		
    private Adapter adapter;
    	
	public ClientList(Context context, java.util.List<Client> list,Concert concert) {
		super(context);
		items = new ArrayList<Items>();
		
/****************** UTILISATION DE LA BONNE LISTE DE CLIENTS ***********************************/
		 
		if (list!=null){
			for (int i=0;i<list.size();i++){
				ClientItem testAff = new ClientItem(context, list.get(i),concert);
				items.add(testAff);
			}
		}
		else{
			Client test = new Client(0,"NO CLIENT", "NO CLIENT","NO CLIENT", "NO CLIENT", "NO CLIENT",0,"NO CLIENT");
			ClientItem testAff = new ClientItem(context, test,concert);
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


