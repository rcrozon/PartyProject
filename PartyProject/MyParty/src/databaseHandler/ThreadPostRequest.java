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

public class ThreadPostRequest extends Thread {

	private String result; 
	private String url;
	private String json;

	public ThreadPostRequest(String urlBase, String urlAdd,String json){
		super();
		url = urlBase+urlAdd;
		this.json = json;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result){
		this.result = result;
	}

	public void run(){

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("json", json));
		Log.i("factor", "passe0");
		try {
			Log.i("factor", "passe1");
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
			setResult(response.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


