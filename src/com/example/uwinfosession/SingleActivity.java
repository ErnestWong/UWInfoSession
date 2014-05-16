package com.example.uwinfosession;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleActivity extends Activity {

	//strings to retrieve data from bundle
	private String employer;
	private String date;
	private String start;
	private String end;
	private String location;
	private String audience;
	private String program;
	private String description;
	private String website;
	
	//textviews for the different texts
	private TextView t1;
	private TextView t2;
	private TextView t3;
	private TextView t4;
	private TextView t5;
	private TextView t6;
	private TextView t7;
	private TextView t8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_layout);
		
		//initializing textviews
		t1 = (TextView) findViewById(R.id.employer_text);
		t2 = (TextView) findViewById(R.id.date_text);
		t3 = (TextView) findViewById(R.id.time_text);
		t4 = (TextView) findViewById(R.id.location_text);
		t5 = (TextView) findViewById(R.id.audience_text);
		t6 = (TextView) findViewById(R.id.programs_text);
		t7 = (TextView) findViewById(R.id.description_text);
		t8 = (TextView) findViewById(R.id.website_text);
		
		//retrieve String values from MainActivity via intent bundle
		Bundle b = this.getIntent().getExtras();
		employer = b.getString(employer);
		date = b.getString(date);
		start = b.getString(start);
		end = b.getString(end);
		location = b.getString(location);
		audience = b.getString(audience);
		program = b.getString(program);
		description = b.getString(description);
		website = b.getString(website);
		
		//setting values to textviews
		t1.setText(employer);
		t2.setText(date);
		t3.setText(start + " to " + end);
		t4.setText("At " + location);
		t5.setText("For: " + audience);
		t6.setText("Programs: " + program);
		t7.setText("Description: " + description);
		t8.setText("Website: " + website);
		
	}
}