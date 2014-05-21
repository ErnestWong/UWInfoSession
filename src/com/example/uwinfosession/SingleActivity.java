package com.example.uwinfosession;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SingleActivity extends Activity {

	// tags
	final String TAG_PARSE_MONTH = "ERROR PARSING MONTH";

	// strings to retrieve data from bundle
	private String employer;
	private String date;
	private String start;
	private String end;
	private String location;
	private String audience;
	private String program;
	private String description;
	private String website;

	// textviews for the different texts
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

		// initializing textviews
		t1 = (TextView) findViewById(R.id.employer_text);
		t2 = (TextView) findViewById(R.id.date_text);
		t3 = (TextView) findViewById(R.id.time_text);
		t4 = (TextView) findViewById(R.id.location_text);
		t5 = (TextView) findViewById(R.id.audience_text);
		t6 = (TextView) findViewById(R.id.programs_text);
		t7 = (TextView) findViewById(R.id.description_text);
		t8 = (TextView) findViewById(R.id.website_text);

		// retrieve String values from MainActivity via intent bundle
		Bundle b = this.getIntent().getExtras();
		employer = b.getString("employer");
		date = b.getString("date");
		start = b.getString("start");
		end = b.getString("end");
		location = b.getString("location");
		audience = b.getString("audience");
		program = b.getString("program");
		description = b.getString("description");
		website = b.getString("website");

		// setting values to textviews
		t1.setText(employer);
		t2.setText(date);
		t3.setText(start + " to " + end);
		t4.setText("At: " + location);
		t5.setText("For: " + audience);
		t6.setText("Programs: " + program);
		t7.setText("Description: " + description);
		t8.setText("Website: " + website);

	}

	/**
	 * on button press, adds event to calendar-- event title, date, start time,
	 * end time, and location are noted
	 * 
	 * @param view
	 */
	public void onButtonPress(View view) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		// splits date string to obtain the day, month, and year of the event
		String[] dateArray = date.split(",.| ");
		int month = getMonthFromString(dateArray[0]);
		int day = Integer.parseInt(dateArray[1]);
		int year = Integer.parseInt(dateArray[2]);

		// splits start time string to obtain start hour and start minute of
		// event
		String[] startArray = start.split(" |:");
		int startHour = Integer.parseInt(startArray[0]);
		int startMinute = Integer.parseInt(startArray[1]);

		//if time is PM, then add 12 hours
		if (startArray[2].equals("PM"))
			startHour += 12;

		//set calendar given the parameters obtained from above
		startCal.set(year, month, day, startHour, startMinute);

		//do the same with end time string
		String[] endArray = end.split(" |:");
		int endHour = Integer.parseInt(endArray[0]);
		int endMinute = Integer.parseInt(endArray[1]);

		if (endArray[2].equals("PM"))
			endHour += 12;

		endCal.set(year, month, day, endHour, endMinute);

		// sends intent with calendar event information to the calendar app
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra(Events.TITLE, "Info Session: " + employer);
		intent.putExtra(Events.EVENT_LOCATION, location);
		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
				startCal.getTimeInMillis());
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
				endCal.getTimeInMillis());

		// starts calendar app activity
		startActivity(intent);
	}

	/**
	 * helper method to convert integer value from String month
	 * 
	 * @param month
	 * @return
	 */
	private int getMonthFromString(String month) {
		try {
			Date date = new SimpleDateFormat("MMM", Locale.ENGLISH)
					.parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int intMonth = cal.get(Calendar.MONTH);
			return intMonth;
		} catch (Exception e) {
			Log.d(TAG_PARSE_MONTH, e + "");
		}
		return 0;
	}
}