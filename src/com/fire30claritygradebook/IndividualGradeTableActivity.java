//TJ Corley
package com.fire30claritygradebook;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class IndividualGradeTableActivity extends Activity {
	private ClarityGradeJSONObject gradeJson;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradetable);
		try 
		{
			Bundle extras = getIntent().getExtras();
			gradeJson = new ClarityGradeJSONObject(extras.getString("gradeJson"));
			TextView period = (TextView) findViewById(R.id.tv_2);
			period.setText(extras.getString("quarterName"));
			((TextView) findViewById(R.id.tv_1)).setText(extras.getString("className"));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		ListView lv = (ListView) findViewById(R.id.list);
		//use same listview as the one for showing class grades
		SimpleAdapter mSchedule = new SimpleAdapter(this, gradeJson.getGradeMap(), R.layout.twocolumnrow,
	            new String[] {"title", "grade",}, new int[] {R.id.CLASS_CELL, R.id.GRADE_CELL});
		lv.setAdapter(mSchedule);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
