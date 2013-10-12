//TJ Corley
package com.fire30claritygradebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClarityLoginJSONObject extends JSONArray{
	private String school;
	private String username;
	private String password;
	private int quarterIndex;
	public static final String baseURL = "http://tcorley.me/clarity/grade?";
	
	public ClarityLoginJSONObject(String string) throws JSONException {
		super(string);
		//need to get declare quarter from beggining
		try 
		{
			this.quarterIndex = this.getJSONObject(2).getInt("quarter_index");
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
			JSONArray classes = this.getJSONObject(0).getJSONArray("classes");
			String [] classTitleArray = new String[classes.length()];
			for (int i = 0; i < classes.length(); i++)
			{
				classTitleArray[i] = classes.getJSONObject(i).getString("class_name");
			}
			return classTitleArray;
		}
		catch (JSONException e) 
		{
			return new String[0];
		}
	}
	public String getQuarter() throws JSONException
	{
		return this.getJSONObject(1).getJSONArray("periods").getString(quarterIndex);
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
			quarters = this.getJSONObject(1).getJSONArray("periods");
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
	public String getGrade(int index)
	{
		try 
		{
			JSONArray gradeList = this.getJSONObject(0).getJSONArray("classes").getJSONObject(index).getJSONArray("grade_values");
			return gradeList.getString(quarterIndex);
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
			JSONObject classes = this.getJSONObject(0).getJSONArray("classes").getJSONObject(position);
			String enrollID = classes.getJSONArray("enroll_id").getString(0);
			String termID = classes.getJSONArray("term_ids").getJSONArray(quarterIndex).getString(0);
			
			return baseURL + "enroll_id=" + enrollID + "&term_id=" + termID + 
					"&student_id=" + getStudentID() + "&aspx=" + getASPXAUTH();
			
		} catch (Exception e) 
		{
			return "";
		}
		
	}
	public String getASPXAUTH()
	{
		try 
		{
			return this.getJSONObject(3).getJSONArray("credentials").getString(0);
		} 
		catch (JSONException e) 
		{
			return "error";
		}
	}
	public String getStudentID()
	{
		try 
		{
			return this.getJSONObject(3).getJSONArray("credentials").getString(1);
		} 
		catch (JSONException e) 
		{
			return "error";
		}
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
