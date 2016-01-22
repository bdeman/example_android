package com.example.klachtenreg;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class SendData extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean>{

	private Exception exception;
	private String url = "http://localhost/location/data.php";
	
	@Override
	protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
		
		
		//new post
		HttpClient httpclient = new DefaultHttpClient();
		//page it will submit to
	    HttpPost httppost = new HttpPost(url);
	    

	    try {
	    	
	    	//execute our post to the server
	        httppost.setEntity(new UrlEncodedFormEntity(params[0]));

	        HttpResponse response = httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

		return null;
	}
}
