package databaseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class ThreadRequestResult extends Thread {
	
	    private String result; 
	    private String url;
	    
	    public ThreadRequestResult(String urlBase, String urlAdd){
	    	super();
	    	url = urlBase+urlAdd;
	    }
	    
	    public String getResult() {
	       return result;
	    }
	    
	    public void setResult(String result){
	    	this.result = result;
	    }
	    
	    public void run(){
	    	StringBuilder response = new StringBuilder();
			URI uri=null;
			try {
				/**************** CREATION URL ET ENVOIE DE REQUETE *******************/
				
				uri = new URI(url);
				HttpGet get = new HttpGet();
                get.setURI(uri);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(get);
                
                /**************** RÉCUPÉRATION DE LA REQUETE EN STRING *******************/
            	HttpEntity messageEntity = httpResponse.getEntity();
                InputStream is = messageEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                
                }
               setResult(response.toString());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
	    	
	    	
	    }
	  
}
