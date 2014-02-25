package databaseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class DatabaseServer {
	
	public static final String urlBase = "http://anthony.flavigny.emi.u-bordeaux1.fr/PartySite/Mobiles/";
	
	public DatabaseServer(){
	}
	
	public String getRequest(final String urlAdd){
		
		ThreadRequestResult reqThread2 = new ThreadRequestResult(urlBase,urlAdd);
		try {
			reqThread2.start();
			reqThread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reqThread2.getResult();
		
	}
	
	public void postRequest(final String urlAdd,String json){
	
		new Thread(new Runnable() {
		
		@Override
		public void run() {
		            
		            	String j = new String();
		      
		            	j= "{\"Client\":{\"id\":\"1\",\"username\":\"antho\",\"mail\":\"anthony.gueguenou@laposte.net\",\"password\""+
		            	":\"f636ba068d303281a78662a4059229817c06a125\",\"first_name\":\"anthony\",\"last_name\":\"gueguenou\""+
		            			",\"admin\":\"0\",\"created\":\"2014-02-19 17:43:17\"}}";
		            	
		            	DefaultHttpClient httpClient = new DefaultHttpClient();
		                HttpPost httpPost = new HttpPost(urlBase+urlAdd);
		                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		                nameValuePairs.add(new BasicNameValuePair("json", j));
		                try {
							httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                try {
		                	StringBuilder response = new StringBuilder();
							HttpResponse httpResponse =httpClient.execute(httpPost);
							HttpEntity messageEntity = httpResponse.getEntity();
			                InputStream is = messageEntity.getContent();
			                BufferedReader br = new BufferedReader(new InputStreamReader(is));
			                String line;
			                while ((line = br.readLine()) != null) {
			                    response.append(line);
			                
			                }
			                Log.i("factor", "Retour envoi : "+ response.toString());
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}}).start();
	}
}
		
		
		/*try {
		        	   Log.i("get", "avant0");
						//ON CREER UN JSON GRACE A LA REPONSE
		            	//JSONObject js = new JSONObject(response.toString());
		            	//ON EXTRAIT LA STRING SANS POST
						//String tmp = js.getString("Client");
						//ON CRÉE UN TABLEEAU
		            	//Log.i("get", "passe");
		            	//JSONObject client =js.getJSONObject("Client");
		        	   JSONArray rep = new JSONArray(response.toString());
		            	Log.i("get", "avant");
						//JSONArray ja = js.getJSONArray("Client");
		            	
						Log.i("get", "apres");
						//Log.i("get", "apres1 : "+ rep.getJSONObject(0).toString());
		            	//String user = client.getString("username");
						
						//ON CRÉE UN JSON AVEC CHAQUE LIGNE DU TABLEAU
						for (int i = 0; i<rep.length();i++){
							JSONObject t = rep.getJSONObject(i);
							String p = t.getString("Client");
							JSONObject info = new JSONObject(p);
							String p2 = info.getString("id");
							Log.i("get", "ID: "+p2);
						}
						//JSONObject t = ja.getJSONObject(0);
						
						//ON A LES INFO	
						//int p = t.getInt("id");
						//String p2 = t.getString("username");
						//Log.i("get", "Username : "+user);
						//Log.i("get", "ID : "+p+" / Username : "+p2);
						
						
						//String s = ja.getString(0);
						//String tab[] = s.split("\"");
						//Log.i("twit",tab[5] );
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.i("get", "excep");
						e.printStackTrace();
					}
			*/
		 
	

