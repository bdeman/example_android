package com.example.klachtenreg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
	
	
	TextView errorMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		

		//session container
		SessionContainer session = (SessionContainer) getApplicationContext();
		
		//if the user is still logged in but wishes to go to the login, we set the userid
		if(session.getUserID() > 0) {
			session.setUserID(0);
		} 
		

		setContentView(R.layout.login);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void goRegister(View view) {
		finish();
		
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);
	}
	
	public void doLogin(View view) {
		
		//get the inputfields
		EditText emailET = (EditText) findViewById(R.id.etGebruikersnaam);
		EditText passwordET = (EditText) findViewById(R.id.etWachtwoord);
		
		//get the contents of the inputfields
		String email = emailET.getText().toString();
		String password = passwordET.getText().toString();
		
		//add the contents to the array which will be submitted to the server page
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mail", email));
        nameValuePairs.add(new BasicNameValuePair("password", sha256(password)));
        
        //run a backgroundtask to submit the data
        //defined below
		new AuthUser().execute(nameValuePairs);
	}
	
	//encrypt method for our password
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	//method for forwarding to either a new view, or display a error message
	public void forward() {
		
		SessionContainer session = (SessionContainer) getApplicationContext();
		//if the user has recieved a userid it will be larger than 0, so we can forward them to the next view
		if(session.getUserID() > 0) {
			finish();
			
			Intent intent = new Intent(this, Complaint.class);
			startActivity(intent);
		} else {
			//password, email combo does not match anything in our database, so display an error message
			errorMessage = (TextView) findViewById(R.id.tvStatus);
			errorMessage.setText("Combinatie niet bekend");
		}
	}
	
	public class AuthUser extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean>{
		
		private Exception exception;
		private String url = "http://localhost/location/login.php";

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url);
		    
		    try {
		    	
		    	
		        httppost.setEntity(new UrlEncodedFormEntity(params[0]));

		        HttpResponse response = httpclient.execute(httppost);
		        
		        
		        //reading the server json data
		        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		        StringBuilder sb = new StringBuilder();
		        
		        String json = null;
		        
		        while((json = reader.readLine()) != null){
		        	sb.append(json);
		        }
		        
		        json = sb.toString();
		        
		        if(json != null) {
		        	
		        	JSONObject jsonObject = new JSONObject(json);
		        	
		        	//fetch the user id from the output in json
			        int userID = (int) jsonObject.getInt("user_id");

			        Log.i("userID ", "user id " +  userID);
			        //set the userid to the sessioncontainer so we can use it in the app
			        SessionContainer session = (SessionContainer) getApplicationContext();
			        session.setUserID(userID);
			        
		        }
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    } catch(JSONException e) {
		    	//Log.i("its","BRAP");
		    }
		    return null;
		}
		
		//automaticly called after execution of the thread
		protected void onPostExecute(Boolean result ) {
			forward();
		}
	}
}
