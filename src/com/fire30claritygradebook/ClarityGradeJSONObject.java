//TJ Corley
package com.fire30claritygradebook;

import java.util.ArrayList;
import java.util.Arrays;
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
			return getJSONObject(1).getBoolean("error");
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
		for(String title : getTitles())
		{
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("title",title);
			map.put("grade", getGrade(Arrays.asList(getTitles()).indexOf(title)));
			myList.add(map);
		}
		return myList;
	}
	public String[] getTitles()
	{
		try {
			JSONArray array = getJSONObject(0).getJSONArray("grades");
			String[] grades = new String[array.length()];
			for(int i = 0; i < array.length();i++)
			{
				grades[i] = array.getJSONArray(i).getString(0);
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
			JSONArray array = getJSONObject(0).getJSONArray("grades");
			return array.getJSONArray(position).getString(1);
		}
		catch(Exception e)
		{
			return "Error";
		}
	}
	
	

}
