//TJ Corley
package com.fire30claritygradebook;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class LoginConnection {
	public static final String SITE = "http://tcorley.me/clarity/login";
	//probably should set up domain. w/e.
	private String username;
	private String password;
	private String errorMessage;
	private boolean isLoggedIn;
	private ClarityLoginJSONObject theJson;
	// set up variables
	
	public LoginConnection(String username,String password)
	{
		this.username = username;
		this.password = password;
		// translates school name to the value passed in the url.
	}
	public void login()
	{
		try 
		{
			URL url = new URL(SITE+"?username="+username+"&password="+password);
			//format for my server's 'api'
			//maybe security issue?
			//would POSTing be better?
			StringBuilder theString = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String aux;
			while ((aux = reader.readLine()) != null) {
			    theString.append(aux);
			}
			//All JSON in one string
			setTheJson(new ClarityLoginJSONObject(theString.toString()));
			System.out.print(theString);
			theJson.setUsername(username);
			theJson.setPassword(password);
			isLoggedIn = theJson.getQuarterIndex() != -1;
			//If there is an error it automatically means we are not logged in
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ClarityLoginJSONObject getTheJson() {
		return theJson;
	}
	public void setTheJson(ClarityLoginJSONObject theJson) {
		this.theJson = theJson;
	}
}
