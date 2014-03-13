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
			Log.i("ADDRESS", reqThread2.getResult());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return reqThread2.getResult();

	}

	public String postRequest(final String urlAdd,final String json){

		ThreadPostRequest postThread = new ThreadPostRequest(urlBase, urlAdd, json);
		try {
			postThread.start();
			postThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return postThread.getResult();
	}
}


