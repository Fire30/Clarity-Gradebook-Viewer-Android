//TJ Corley
package com.fire30claritygradebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClarityLoginJSONObject extends JSONObject{
	private String school;
	private String username;
	private String password;
	private int quarterIndex;
	
	public ClarityLoginJSONObject(String string) throws JSONException {
		super(string);
		//need to get declare quarter from beggining
		try 
		{
			this.quarterIndex = getInt("Period");
		} 
		catch (JSONException e) 
		{
			this.quarterIndex = -1;
		}
	}
	public String[] getTitles()
	{
		try 
		{
			JSONArray classes = getJSONArray("GradeData");
			String[] titles = new String[classes.length()];
			for (int i = 0; i < classes.length(); i++)
			{
				JSONObject theClass = (JSONObject)classes.get(i);
				titles[i] = theClass.keys().next().toString();
			}
			return titles;
		}
		catch (JSONException e) 
		{
			return new String[0];
		}
	}
	public String getQuarter() throws JSONException
	{
		return (String) getJSONArray("QuarterNames").get(getQuarterIndex());
	}
	public int getQuarterIndex()
	{
		return this.quarterIndex;
	}
	public void setQuarterIndex(int quarterIndex)
	{
		this.quarterIndex = quarterIndex;
			
	}
		//not liking try-catch, considering it is impossible to not have the period
	public String[] getQuarterArray()
	{
		JSONArray quarters;
		try 
		{
			quarters = getJSONArray("QuarterNames");
			String[] names = new String[quarters.length()];
			for(int i = 0; i < quarters.length();i++)
			{
				names[i] = (String)quarters.get(i);
			}
			return names;
		}
		catch(Exception e)
		{
			return new String[0];
		}
	}
	public String getGrade(String className)
	{
		JSONArray quarters = null;
		try 
		{
			quarters = getJSONArray("GradeData");
			JSONObject classes = (JSONObject)(quarters.get(Arrays.asList(getTitles()).indexOf(className)));
			JSONArray classData = (JSONArray)classes.get(classes.names().get(0).toString());
			JSONArray gradeData =  (JSONArray)classData.get(getQuarterIndex());
			return gradeData.get(0).toString();
			//Extremely Nasty, should have thought more about how to display JSON
		} 
		catch (JSONException e1) 
		{
			return "";
		}
	}
	public String getSpecifiedUrl(int position)
	{
		try 
		{
			JSONArray quarters = getJSONArray("GradeData");
			JSONObject classes = (JSONObject)(quarters.get(position));
			JSONArray classData = (JSONArray)classes.get(classes.names().get(0).toString());
			JSONArray gradeData =  (JSONArray)classData.get(getQuarterIndex());
			
			return gradeData.get(1).toString();
			
		} catch (Exception e) 
		{
			return "";
		}
		
	}
	public String getASPXAUTH()
	{
		try 
		{
			return getString("ASPXAUTH");
		} 
		catch (JSONException e) 
		{
			return "error";
		}
	}
	public List<? extends Map<String, ?>> getGradeMap() {
		
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		for(String title : getTitles())
		{
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("title",title);
			map.put("grade", getGrade(title));
			myList.add(map);
		}
		return myList;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}

}
