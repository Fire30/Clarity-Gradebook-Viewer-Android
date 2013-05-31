//TJ Corley
package com.fire30claritygradebook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class GradeDataConnection {
	public static final String SITE = "http://ec2-174-129-141-39.compute-1.amazonaws.com/clarity/grades/";
	private String url;
	private String ASPXAUTH;
	private ClarityGradeJSONObject theJson;
	public GradeDataConnection(String url, String ASPXAUTH)
	{
		try 
		{
			this.url = URLEncoder.encode(url,"UTF-8");
			this.ASPXAUTH = ASPXAUTH;
		} 
		catch (UnsupportedEncodingException e) 
		{
			this.url = url;
			//If password can't be encoded to utf-8?
		}
	}
	public void connect()
	{
		try {
			URL theURL = new URL(SITE + "?aspxauth=" + ASPXAUTH + "&url=" + url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(theURL.openStream(), "UTF-8"));
			String aux;
			StringBuilder theString = new StringBuilder();
			while ((aux = reader.readLine()) != null) {
			    theString.append(aux);
			}
			//All JSON in one string
			theJson = new ClarityGradeJSONObject(theString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public ClarityGradeJSONObject getTheJson()
	{
		return this.theJson;
	}
	

}
