package com.example.klachtenreg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstuse);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		

		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	//define which view we will need to display
	public void choice(View view) {
		
		//decide wich button has been pressed to display the corrosponding view
		switch(view.getId()) {
		
		
        	case R.id.ibRegistreren:
        		finish();
        		Intent registerIntent = new Intent(this, Register.class);
        		startActivity(registerIntent);
            break;
            
        	case R.id.ibInloggen:
        		finish();
        		Intent loginIntent = new Intent(this, Login.class);
        		startActivity(loginIntent);
            break;
		}
	}
	
	public void noReg(View view) {
		finish();
		
		Intent complaint = new Intent(this, Complaint.class);
		startActivity(complaint);
	}

}
