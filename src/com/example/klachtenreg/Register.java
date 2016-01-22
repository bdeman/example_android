package com.example.klachtenreg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Register extends Activity {

	
	//for highlighting purposes
	EditText mailET;
	
	public static boolean hasErrors = false;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registreren);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void registerUser(View view) {
		
		//fetch all the inputfields
		EditText firstNameET = (EditText) findViewById(R.id.etVoornaamReg);
		EditText infixnameET = (EditText) findViewById(R.id.etTussenvoegselsReg);
		EditText lastNameET = (EditText) findViewById(R.id.etAchternaamReg);
		
		//seperatly defined so we can highlight the emailfield
		mailET = (EditText) findViewById(R.id.etMail);
		
		EditText streetNameET = (EditText) findViewById(R.id.etStraat);
		EditText postalcodeET = (EditText) findViewById(R.id.etPostcode);
		EditText areaET = (EditText) findViewById(R.id.etWoonplaats);
		EditText passwordET = (EditText) findViewById(R.id.etWW);
		EditText passwordETRE = (EditText) findViewById(R.id.etHWW);
		
		//convert all the inputfields with their contents to strings.
		String firstName = firstNameET.getText().toString();
		String infixName = infixnameET.getText().toString();
		String lastName = lastNameET.getText().toString();
		String mail = mailET.getText().toString();
		String streetName = streetNameET.getText().toString();
		String postalCode = postalcodeET.getText().toString();
		String area = areaET.getText().toString();
		String password = passwordET.getText().toString();
		String passwordRepeat = passwordETRE.getText().toString();
		
		//adding all our string values to the arraylist so we can submit this array to the server.
		
		//note all the first "strings" values are the postkey's the server recieves
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("firstname", firstName));
        nameValuePairs.add(new BasicNameValuePair("infix", infixName));
        nameValuePairs.add(new BasicNameValuePair("lastname", lastName));
        nameValuePairs.add(new BasicNameValuePair("mail", mail));
        nameValuePairs.add(new BasicNameValuePair("streetname", streetName));
        nameValuePairs.add(new BasicNameValuePair("postalcode", postalCode));
        nameValuePairs.add(new BasicNameValuePair("area", area));
        
        nameValuePairs.add(new BasicNameValuePair("password", sha256(password)));
        
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher emailCheck = pattern.matcher(mailET.getText().toString());
		
		
		//email regex check
        if(!emailCheck.matches()) {


			mailET.setBackgroundColor(Color.RED);
            
		} else if(!password.equals(passwordRepeat)) {
			//check if both passwords are equal
			passwordET.setBackgroundColor(Color.RED);
		} else {
			//else everything seems fine, lets sumbit it to the server
            new SendRegisterData().execute(nameValuePairs);
		}

        
	}

	//method for converting our password to a hashed sha256 string
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
	
	//postfetch check, this runs after the background thread finishes
	public void postFetch() {
		
		//if there are errors, highlight the emailfield
		
		//serverside check figured that the email already excists
		if(hasErrors) {
        	//errors
        	mailET.setBackgroundColor(Color.RED);
        	
        
        } else {
        	//else just load the final view
			setContentView(R.layout.registratie_compleet);
			
        }
		
	}
	
	//this is from the onclick button in the final view, so we can close the last layout and close everything
	public void registerComplete(View view) {
		finish();
		
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	
	public class SendRegisterData extends AsyncTask<ArrayList<NameValuePair>, Void, Boolean>{
		
		//potential use in the future
		private Exception exception;

		private String url = "http://localhost/location/register.php";

		@Override
		protected Boolean doInBackground(ArrayList<NameValuePair>... params) {
			HttpClient httpclient = new DefaultHttpClient();
			
			//the url of the serverpage (file) that recieves the posts
		    HttpPost httppost = new HttpPost(url);
		    
		    try {
		    	
		    	//encode our arraylist with postfields and values
		        httppost.setEntity(new UrlEncodedFormEntity(params[0]));

		        //submit the values
		        HttpResponse response = httpclient.execute(httppost);
		        
		        //read the output of the server (in case there are json outputs)
		        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		        String json = reader.readLine();
		        
		        if(json != null) {
		        	//
		        	JSONObject jsonObject = new JSONObject(json);
			        
			        String email = (String) jsonObject.getString("duplicate");
			        
			        //if the email excists we need to display an error
			        if(email.equalsIgnoreCase("email")) {

			        	Register.hasErrors = true;
			        }
			        
		        } else {
		        	//no errors, good to go
		        	Register.hasErrors = false;
		        	
		        }
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    } catch(JSONException e) {
		    	
		    }
		    return null;
		}
		
		//executed after the post has been submitted
		protected void onPostExecute(Boolean result ) {
			postFetch();
		}
	}
}
