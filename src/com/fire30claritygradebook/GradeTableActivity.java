//TJ Corley
package com.fire30claritygradebook;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;



public class GradeTableActivity extends SherlockActivity {

    private ClarityLoginJSONObject theJson;
    private String clickedURL;
    private int clickedPositon;
    private String username;
    private String password;
    private String school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradetable);
		try 
		{
			Intent i = getIntent();
			theJson = new ClarityLoginJSONObject(i.getStringExtra("theJson"));
			theJson.setQuarterIndex(i.getIntExtra("quarterIndex",-1));
			username = i.getStringExtra("username");
			password = i.getStringExtra("password");
			school = i.getStringExtra("school");
			TextView period = (TextView) findViewById(R.id.tv_2);
			period.setText(theJson.getQuarter());
			
			//this is sort of like having an arg for constructor for activity.
			//I can set my variables using this.
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setOnItemClickListener(new OnItemClickListener()
		{
		    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
		    { 
		    	clickedPositon = position;
		        clickedURL = theJson.getSpecifiedUrl(position);
		        System.out.println(clickedURL);
		        if(!clickedURL.equals(""))
		        	new GradeDataAsyncTask().execute();
		        
		    }
		});
		SimpleAdapter mSchedule = new SimpleAdapter(this, theJson.getGradeMap(), R.layout.twocolumnrow,
	            new String[] {"title", "grade",}, new int[] {R.id.CLASS_CELL, R.id.GRADE_CELL});
		lv.setAdapter(mSchedule);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        SubMenu subMenu = menu.addSubMenu(0, 0, 2, "").setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
        subMenu.add("Refresh Grade Data");
        subMenu.add("Choose Quarter");
        subMenu.add("About");
        
        MenuItem subMenuItem = subMenu.getItem();
        subMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getTitle().equals("Refresh Grade Data"))
    	{
    		new LoginAsyncTask().execute();
    	}
    	else if(item.getTitle().equals("Choose Quarter"))
    	{
    		AlertDialog.Builder adb = new AlertDialog.Builder(this);
    		adb.setSingleChoiceItems(theJson.getQuarterArray(), 0, new OnClickListener() {

    		        @Override
    		        public void onClick(DialogInterface d, int n) {
    		            theJson.setQuarterIndex(n);
    		            System.out.println(theJson.getQuarterIndex());
    		            finish();
    		            Intent i = new Intent(GradeTableActivity.this, GradeTableActivity.class);
    	            	i.putExtra("theJson", theJson.toString());
    	            	i.putExtra("username",username);
    	            	i.putExtra("password", password);
    	            	i.putExtra("school", school);
    	            	i.putExtra("quarterIndex", theJson.getQuarterIndex());
    	                startActivity(i);
    		        }

    		});
    		adb.setNegativeButton("Done", null);
    		adb.setTitle("Choose Grading Period");
    		adb.show();
    	}
    	else if(item.getTitle().equals("About"))
    	{
    		Intent i = new Intent(GradeTableActivity.this, AboutActivity.class);
    		startActivity(i);
    	}
    	return false;
    }
    private class GradeDataAsyncTask extends AsyncTask<Void, Void, Void> {
    	//Could have used URL AsyncTask, I saw most examples using that.
    	//I think it would make it look worse though, and a tad more difficult
    	private ProgressDialog progress;
    	private ClarityGradeJSONObject gradeJson;
    	private boolean error = false;
		@Override
		protected Void doInBackground(Void... params) {
			GradeDataConnection con = new GradeDataConnection(clickedURL,theJson.getASPXAUTH());
			con.connect();
			gradeJson = con.getTheJson();
			if(gradeJson.getError())
			{
				LoginConnection login = new LoginConnection(theJson.getUsername(),theJson.getPassword(),theJson.getPassword());//need to refresh first;
				login.login();
				theJson = login.getTheJson();
				con = new GradeDataConnection(clickedURL,theJson.getASPXAUTH());
				try
				{
					con.connect();
					gradeJson = con.getTheJson();
				}
				catch(Exception e)
				{
					error = true;
				}
				
			}
			return null;
		}
		@Override
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(GradeTableActivity.this, "Fetching Grades...", "");
			// Show spinning dialog
			
		}
		@Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            progress.dismiss();
            if(!error)
            	switchViews();
            else
            {
            	new AlertDialog.Builder(GradeTableActivity.this).setTitle("Error!").
            	setMessage("Could Not Find Class.\nIs this class graded?").setNeutralButton("Ok", null).show();
            }
            super.onPostExecute(result);
        }
		private void switchViews()
		{
			Intent i = new Intent(GradeTableActivity.this, IndividualGradeTableActivity.class);
			try
			{
				i.putExtra("gradeJson", gradeJson.toString());
				i.putExtra("quarterName", theJson.getQuarter());
				i.putExtra("className", theJson.getTitles()[clickedPositon]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
            startActivity(i);
		}
    }
    private class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
    	//Could have used URL AsyncTask, I saw most examples using that.
    	//I think it would make it look worse though, and a tad more difficult
    	private ProgressDialog progress;
    	private LoginConnection login;
		@Override
		protected Void doInBackground(Void... params) {
			login = new LoginConnection(username,password,school);
			login.login();
			return null;
		}
		@Override
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(GradeTableActivity.this, "Refreshing Grades...", "");
			// Show spinning dialog
			
		}
		@Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            progress.dismiss();
            if(login.isLoggedIn())
        	{
            	finish();
            	Intent i = new Intent(GradeTableActivity.this, GradeTableActivity.class);
            	i.putExtra("theJson", login.getTheJson().toString());
            	i.putExtra("username",username);
            	i.putExtra("password", password);
            	i.putExtra("school", school);
            	i.putExtra("quarterIndex", theJson.getQuarterIndex());
                startActivity(i);
                //If we are logged in go to next page.
        	}
            else
            {
            	new AlertDialog.Builder(GradeTableActivity.this).setTitle("Could Not Refresh Grade Data").
            	setMessage(login.getErrorMessage()).setNeutralButton("Ok", null).show();
            	//we didn't log in
            	//tell the error that comes from json and let them try again.
            }
            super.onPostExecute(result);
        }
    }
    
}