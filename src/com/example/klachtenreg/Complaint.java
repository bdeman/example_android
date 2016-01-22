package com.example.klachtenreg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class Complaint extends Activity {

	//area
	public static String HOUSETYPE = "housetype";
	public static String LOCATION = "where";
	public static String TYPERELEFENCE = "boop";
	
	public static String EXPLANATION = "etc";
	
	//personal info
	public final static String FIRSTNAME = "com.example.klachtenreg.FIRSTNAME";;
	public final static String LASTNAME = "com.example.klachtenreg.LASTNAME";
	public final static String STREETNAME = "com.example.klachtenreg.STREETNAME";
	public final static String POSTALCODE = "com.example.klachtenreg.POSTALCODE";
	public final static String PHONENUMBER = "com.example.klachtenreg.PHONENUMBER";
	public final static String EMAIL = "com.example.klachtenreg.EMAIL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	
		//button1 = (Button) findViewById(R.id.button1);
		
		setContentView(R.layout.step1);
		
	}
	
	public void stepTwo(View view) {
		//finish this view and create the next
		finish();
		
		//forward all our data to the next class
    	Intent intent = new Intent(this, ComplaintStepTwo.class);
    	EditText description = (EditText) findViewById(R.id.etKlachtomschrijving);
    	String message = description.getText().toString();
    	intent.putExtra(HOUSETYPE, HOUSETYPE);
    	intent.putExtra(LOCATION, LOCATION);
    	intent.putExtra(TYPERELEFENCE, TYPERELEFENCE);
    	intent.putExtra(EXPLANATION, message);
    	startActivity(intent);

	}
	
	
	//todo alles netjes opdelen
	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
		//set the value and forward to the next view
	    switch(view.getId()) {
	        case R.id.rbAppartement:
	            if (checked)
	            	HOUSETYPE = "Appartement";
	    	    	setContentView(R.layout.step1_1appartement);
	            break;
	        case R.id.rbWoonhuis:
	            if (checked)
	            	HOUSETYPE = "Woonhuis";
	            	setContentView(R.layout.step1_1woonhuis);
	            break;
	        
	        case R.id.rbThuis:
	        	if(checked)
	        		LOCATION = "Thuis";
	        		setContentView(R.layout.step1_2thuis);
	        	break;
	        	
	        case R.id.rbBuren:
	        	if(checked)
	        		LOCATION = "Buren";
	        		setContentView(R.layout.step1_2buren);
	        	break;

	        case R.id.rbServiceruimte:
	        	if(checked)
	        		LOCATION = "Serviceruimte";
	        		setContentView(R.layout.step1_2serviceruimte);
	        	break;
	        case R.id.rbOmgeving:
	        	if(checked)
	        		LOCATION = "Omgeving";
	        		setContentView(R.layout.step1_2omgeving);
	        	break;
	        
	        	
	        //step1_2buren
	        case R.id.rbGeluid:
	        	if(checked)
	        		TYPERELEFENCE = "Geluid";
	        	
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbStank:
	        	if(checked)
	        		TYPERELEFENCE = "Stank";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbVuil:
	        	if(checked)
	        		TYPERELEFENCE = "Vuil";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbGeweld:
	        	if(checked)
	        		TYPERELEFENCE = "Geweld";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbOverig:
	        	if(checked)
	        		TYPERELEFENCE = "Overig";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        	
	        //step1_2serviceruimte
	        case R.id.rbLift:
	        	if(checked)
	        		TYPERELEFENCE = "Lift";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        	
	        case R.id.rbVerlichting:
	        	if(checked)
	        		TYPERELEFENCE = "Verlichting";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        	
	        case R.id.rbRuitschade:
	        	if(checked)
	        		TYPERELEFENCE = "Ruitschade";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        	
	        //step1_2thuis
	        case R.id.rbWoonkamer:
	        	if(checked)
	        		TYPERELEFENCE = "Woonkamer";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbKeuken:
	        	if(checked)
	        		TYPERELEFENCE = "Keuken";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbGang:
	        	if(checked)
	        		TYPERELEFENCE = "Gang";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbTrap:
	        	if(checked)
	        		TYPERELEFENCE = "Trap";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbSlaapkamer:
	        	if(checked)
	        		TYPERELEFENCE = "Slaapkamer";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbBadkamer:
	        	if(checked)
	        		TYPERELEFENCE = "Badkamer";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbToilet:
	        	if(checked)
	        		TYPERELEFENCE = "Toilet";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbBerging:
	        	if(checked)
	        		TYPERELEFENCE = "Berging";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbTechnischeRuimte:
	        	if(checked)
	        		TYPERELEFENCE = "Technische ruimte";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbBalkon:
	        	if(checked)
	        		TYPERELEFENCE = "Balkon";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        case R.id.rbLantaarn:
	        	if(checked)
	        		TYPERELEFENCE = "Lantaarn";
	        		setContentView(R.layout.step1_3omschrijving);
	        	break;
	        	
	        		
	    }
	    

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complaint, menu);
		return true;
	}

}
