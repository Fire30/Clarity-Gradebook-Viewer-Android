//TJ Corley
package com.fire30claritygradebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public class ClarityGradeJSONObject extends JSONArray {
	private String errorMessage;
	public ClarityGradeJSONObject(String string) throws JSONException
	{
		super(string);
	}
	public boolean getError()
	{
		try
		{
			return !getJSONArray(0).getJSONObject(0).has("assignment_name");
		}
		catch(Exception e)
		{
			return true;
		}
	}
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
	public List<? extends Map<String, ?>> getGradeMap() {
		
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		int i = 0;
		for(String title : getTitles())
		{
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("title",title);
			map.put("grade", getGrade(i));
			myList.add(map);
			i++;
		}
		return myList;
	}
	public String[] getTitles()
	{
		try {
			String[] grades = new String[this.length()];
			for(int i = 0; i < this.length();i++)
			{
				grades[i] = getJSONArray(i).getJSONObject(0).getString("assignment_name");
			}
			return grades;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String getGrade(int position)
	{
		try
		{
			return  getJSONArray(position).getJSONObject(1).getString("score");
		}
		catch(Exception e)
		{
			return "Error";
		}
	}
	
	

}
