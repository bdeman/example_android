package com.example.klachtenreg;

import android.app.Application;

/*
 * used for our session created when succesful logging in
 */
public class SessionContainer extends Application{
	
	Session session = new Session();
	
	public int getUserID() {
		return session.userID;
	}
	
	public void setUserID(int userID) {
		session.userID = userID;
	}
}
