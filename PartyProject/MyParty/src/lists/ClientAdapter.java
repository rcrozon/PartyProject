package lists;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ClientAdapter extends BaseAdapter {

	List<ClientItem> clientList;
	LayoutInflater mInflater;
	
	
	public ClientAdapter (Context context, List<ClientItem> clientList){
		this.clientList = clientList;
	}
	
	@Override
	public int getCount() {
		return clientList.size();
	}

	@Override
	public Object getItem(int position) {
		return clientList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  return clientList.get(position);
		
	}

}
