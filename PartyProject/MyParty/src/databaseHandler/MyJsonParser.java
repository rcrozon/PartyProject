package databaseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
 



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entities.Client;
 



import android.util.Log;
 
public class MyJsonParser {


    public MyJsonParser() {
    }
 
    public List<Client> getClientFromJson(String json) {
    	List<Client> cl = new ArrayList<Client>();
    	
    	JSONArray rep;
		try {
			rep = new JSONArray(json);
			for (int i = 0 ; i<rep.length() ; i++){
	    		
				JSONObject client = rep.getJSONObject(i);
				String infoStr = client.getString("Client");
				JSONObject infoClient = new JSONObject(infoStr);
				int id = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ID));
				id+=11;
				String username = infoClient.getString(Tables.CLIENT_NAME_USERNAME);
				String mail = infoClient.getString(Tables.CLIENT_NAME_MAIL);
				/*TODO Crypter MDP*/
				//String password = infoClient.getString(Tables.CLIENT_NAME_PASSWORD);
				String firstName = infoClient.getString(Tables.CLIENT_NAME_FIRSTNAME);
				String lastName = infoClient.getString(Tables.CLIENT_NAME_LASTNAME);
				int admin = Integer.parseInt(infoClient.getString(Tables.CLIENT_NAME_ADMIN));
				String dateCreated = infoClient.getString(Tables.CLIENT_NAME_DATE_CREATE);
				Client clientObj = new Client(id, firstName, lastName, mail, username, "test", admin, dateCreated);
				cl.add(clientObj);			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return cl;
    }    	
}