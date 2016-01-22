package com.example.klachtenreg;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ComplaintStepTwo extends Activity implements OnClickListener {
	
	ImageButton createPicture;
	Intent pictureIntent;
	final static int cameraData = 0;
	ImageView displayPicture;
	Bitmap picture;
	
	String filepath;
	
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	
	EditText firstNameET;
	EditText infixET;
	EditText lastNameET;
	
	EditText mailET;
	EditText phoneET;
	
	EditText streetNameET;
	EditText postalcodeET;
	EditText areaET;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step2);
		
		displayPicture = (ImageView) findViewById(R.id.ivReturnedPic);
		createPicture = (ImageButton) findViewById(R.id.ibTakePic);
		
		createPicture.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ibTakePic:
				
				pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(pictureIntent, cameraData);

				break;
				
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complaint_step_two, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			
			Bundle extras = data.getExtras();
			picture = (Bitmap) extras.get("data");
			displayPicture.setImageBitmap(picture); 
			
		}
	}
	
	
	//the method below has not been used in the final product, but did prove to be functional, so we left it just in case.
	/* 
	protected String saveBitmap(Bitmap bm, String name) throws Exception {
	    String tempFilePath = Environment.getExternalStorageDirectory() + "/"
	            + getPackageName() + "/" + name + ".jpg";
	    File tempFile = new File(tempFilePath);
	    if (!tempFile.exists()) {
	        if (!tempFile.getParentFile().exists()) {
	            tempFile.getParentFile().mkdirs();
	        }
	    }

	    tempFile.delete();
	    tempFile.createNewFile();

	    int quality = 100;
	    FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

	    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
	    bm.compress(CompressFormat.JPEG, quality, bos);

	    bos.flush();
	    bos.close();

	    bm.recycle();

	    return tempFilePath;
	}*/
	
	public void stepThree(View view) {

		SessionContainer session = (SessionContainer) getApplicationContext();
		
		Intent intent = getIntent();
		String description = intent.getStringExtra(Complaint.EXPLANATION);
		String houseType = intent.getStringExtra(Complaint.HOUSETYPE);
		String location = intent.getStringExtra(Complaint.LOCATION);
		String typePlace = intent.getStringExtra(Complaint.TYPERELEFENCE);
		
		//bytearray for the picture content
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		
		if(picture != null) {

			//compress our bitmap picture to a jpg
			picture.compress(CompressFormat.JPEG, 100, bos);
			
			byte[] data = bos.toByteArray();
			
			//here is were we use the base64 class to encode our image to a string
			String byteArray = Base64.encodeBytes(data);

			//add to the sumbitable array
			nameValuePairs.add(new BasicNameValuePair("image", byteArray));
			
		}
		
		//values for our submitable array
        nameValuePairs.add(new BasicNameValuePair("description", description));
        nameValuePairs.add(new BasicNameValuePair("housetype", houseType));
        nameValuePairs.add(new BasicNameValuePair("location", location));
        nameValuePairs.add(new BasicNameValuePair("typeplace", typePlace));

        //if the user is logged in we submit the userid along the postvalues
        if(session.getUserID() > 0) {

            nameValuePairs.add(new BasicNameValuePair("userID", Integer.toString(session.getUserID())));

            new SendData().execute(nameValuePairs);
            
            //no need to display any other pages besides the final page
    		setContentView(R.layout.step3_internet);
    		
		} else { //we dont know this person
			
			setContentView(R.layout.step3_1_noreg);
			
		}
		
	}
	
	
	public void stepThreeReg(View view) {
		
		//get the fields
		firstNameET = (EditText) findViewById(R.id.etVoornaamFix);
		infixET = (EditText) findViewById(R.id.etTussenvoegsels);
		lastNameET = (EditText) findViewById(R.id.etAchternaam);
		
		mailET = (EditText) findViewById(R.id.etMail);
		phoneET = (EditText) findViewById(R.id.etTelefoonnummer);
		
		streetNameET = (EditText) findViewById(R.id.etStraat);
		postalcodeET = (EditText) findViewById(R.id.etPostcode);
		areaET = (EditText) findViewById(R.id.etWoonplaats);
		
		//get the field values
		String firstName = firstNameET.getText().toString();
		String infixName = infixET.getText().toString();
		String lastName = lastNameET.getText().toString();
		String mail = mailET.getText().toString();
		String phoneNumber = phoneET.getText().toString();
		
		//we decided after a customer meeting, that these were not needed anymore, but left them here just in case
		//String streetName = streetNameET.getText().toString();
		//String postalCode = postalcodeET.getText().toString();
		//String area = areaET.getText().toString();
		
		
		//add our values to the submitable array
		nameValuePairs.add(new BasicNameValuePair("firstname", firstName));
		nameValuePairs.add(new BasicNameValuePair("infix", infixName));
        nameValuePairs.add(new BasicNameValuePair("lastname", lastName));
        nameValuePairs.add(new BasicNameValuePair("mail", mail));
        
        nameValuePairs.add(new BasicNameValuePair("phonenumber", phoneNumber));
        
        //nameValuePairs.add(new BasicNameValuePair("streetname", streetName));
        //nameValuePairs.add(new BasicNameValuePair("postalcode", postalCode));
        //nameValuePairs.add(new BasicNameValuePair("area", area));
        
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher emailCheck = pattern.matcher(mailET.getText().toString());
		
		//check if the user has a valid email
		if(emailCheck.matches()) {

			//submit in background
	        new NoregComplaint().execute(nameValuePairs);
	        
	        //display final view
			setContentView(R.layout.step3_internet);

		} else {
			//error message
			mailET.setBackgroundColor(Color.RED);
		}


	}
	
	//for closing our final view
	public void shutdown(View view) {
		
		finish();
		
	}


}
