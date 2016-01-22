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

public class NoregComplaint extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean>{

	private Exception exception;
	private String url = "http://localhost/location/noregcomplaint.php";

	@Override
	protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
		//the post values are in params[0]
		HttpClient httpclient = new DefaultHttpClient();
		//page on the server the data will be submitted to
	    HttpPost httppost = new HttpPost(url);

	    try {
	    	//send our post to the server
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
