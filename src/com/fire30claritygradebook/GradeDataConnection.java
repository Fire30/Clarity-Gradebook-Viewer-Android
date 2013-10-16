//TJ Corley
package com.fire30claritygradebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;

public class GradeDataConnection {
	private String url;
	private ClarityGradeJSONObject theJson;
	public GradeDataConnection(String url)
	{
		this.url = url;
	}
	public void connect() throws JSONException, IOException
	{
			URL theURL = new URL(this.url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(theURL.openStream(), "UTF-8"));
			String aux;
			StringBuilder theString = new StringBuilder();
			while ((aux = reader.readLine()) != null) {
			    theString.append(aux);
			}
			//All JSON in one string
			theJson = new ClarityGradeJSONObject(theString.toString());
			System.out.println(theJson);
	}
		
	public ClarityGradeJSONObject getTheJson()
	{
		return this.theJson;
	}
	

}
