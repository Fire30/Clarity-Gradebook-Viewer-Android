//TJ Corley
//https://github.com/Fire30/
//App licensed under MIT

package com.fire30claritygradebook;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockActivity{

    private String username;
    private String password;
    private boolean autoLogin;


	//would make as Array, but I need to add 'select school'.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultValues();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        return super.onCreateOptionsMenu(menu);
    }
    public void doLogin(View v)
    {
    	password = ((EditText) (findViewById(R.id.password))).getText().toString();
    	username = ((EditText) (findViewById(R.id.username))).getText().toString();
    	autoLogin = ((CheckBox) findViewById(R.id.autoLogin)).isChecked();
    	
    	// when we press the login button, get text from the buttons.
    	new LoginAsyncTask().execute();
    	// Android need asynctask for http, I guess it doesn't make gui unresponsive
    	
    }
    public void setDefaultValues()
    {
    	SharedPreferences preferences = this.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        if(preferences.getString("username", null) != null)
        {
        	((EditText)(findViewById(R.id.username))).setText(preferences.getString("username", null),TextView.BufferType.EDITABLE);
        }
        if(preferences.getString("password", null) != null)
        {
        	((EditText)(findViewById(R.id.password))).setText(preferences.getString("password", null),TextView.BufferType.EDITABLE);
        }
    }
    private class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
    	//Could have used URL AsyncTask, I saw most examples using that.
    	//I think it would make it look worse though, and a tad more difficult
    	private ProgressDialog progress;
    	private LoginConnection login;
		@Override
		protected Void doInBackground(Void... params) {
			login = new LoginConnection(username,password);
			login.login();
			return null;
		}
		@Override
		protected void onPreExecute()
		{
			progress = ProgressDialog.show(MainActivity.this, "Logging In...", "");
			// Show spinning dialog
			
		}
		@Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            progress.dismiss();
            if(login.isLoggedIn())
        	{
            	SharedPreferences preferences = MainActivity.this.getSharedPreferences("MyPreferences", MODE_PRIVATE);  
        		SharedPreferences.Editor editor = preferences.edit();
            	if(autoLogin)
            	{
            		editor.putString("username", username);
            		editor.putString("password", password);
            		//Lets us use these values across sessions
            	}
            	else
            	{
            		editor.clear();
            		//If no autologin, clear all values
            	}
            	editor.commit();
            	Intent i = new Intent(MainActivity.this, GradeTableActivity.class);
            	i.putExtra("theJson", login.getTheJson().toString());
            	i.putExtra("username",username);
            	i.putExtra("password", password);
            	i.putExtra("quarterIndex", login.getTheJson().getQuarterIndex());
                startActivity(i);
                //If we are logged in go to next page.
        	}
            else
            {
            	new AlertDialog.Builder(MainActivity.this).setTitle("Could Not Login!").
            	setMessage(login.getErrorMessage()).setNeutralButton("Ok", null).show();
            	//we didn't log in
            	//tell the error that comes from json and let them try again.
            }
            super.onPostExecute(result);
        }
    }
    
}
