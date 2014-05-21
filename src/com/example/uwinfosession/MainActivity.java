package com.example.uwinfosession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//to do: 
//-sort list
//-add new activity for single view
//-xml documents for single view and list view
//-add fragment for tab
//-check for internet connection (make sure the data saves for offline reading)
//-improve UI

public class MainActivity extends Activity {

	// error tags

	private final String TAG_NULL = "NULL EVENTLIST";
	private final String TAG_CLICKED = "CLICKED LIST";
	private final String TAG_JSON_ERROR = "JSON error";

	private final int WINTER_2014 = 1141;
	private final int SPRING_2014 = 1145;
	private final int FALL_2014 = 1149;

	// URL string constants
	private final String API_KEY = "b15ace6df5f1c774c94309b0d91912f3";
	private final String term = SPRING_2014 + "";
	private final String format = "json";

	// private async thread class
	private FindSessions task;

	// lists for each sorted option
	public List<Event> eventList;
	public List<Event> sortedByDateList;
	public List<Event> sortedByEmployerList;
	public List<Event> sortedByLocationList;

	private Context c;
	private Intent intent;

	private ListView l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		l = (ListView) findViewById(R.id.listView1);
		c = getApplicationContext();
		
		task = new FindSessions();
		task.execute();

		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG_CLICKED, position + "");
				if (eventList.isEmpty()) {
					Log.d(TAG_NULL, "OnClick");
					return;
				}
				intent = new Intent(c, SingleActivity.class);
				Bundle b = new Bundle();
				b = putBundle(eventList.get(position));
				intent.putExtras(b);
				startActivity(intent);
			}

		});
	}

	private Bundle putBundle(Event event) {
		Bundle b = new Bundle();
		b.putString("employer", event.getEmployer());
		b.putString("date", event.getDate());
		b.putString("start", event.getStarttime());
		b.putString("end", event.getEndtime());
		b.putString("location", event.getLocation());
		b.putString("audience", event.getAudience());
		b.putString("program", event.getPrograms());
		b.putString("description", event.getDescription());
		b.putString("website", event.getWebsite());
		return b;
	}

	// on button click for sorting by employers
	public void onSortEmployer() {
		if (eventList.isEmpty()) {
			Log.d(TAG_NULL, "sort employer");
			return;
		}

		if (sortedByEmployerList.isEmpty()) {
			//sortedByEmployerList = sortByEmployer();
		}

		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(c,
				android.R.layout.simple_list_item_1, eventList);
		l.setAdapter(adapter);

	}

	// on button click for sorting by date
	public void onSortDate() {
		if (eventList.isEmpty()) {
			Log.d(TAG_NULL, "sort date");
			return;
		}

		if (sortedByEmployerList.isEmpty()) {
			//sortedByDateList = sortByDate();
		}

		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(c,
				android.R.layout.simple_list_item_1, eventList);
		l.setAdapter(adapter);
	}

	// on button click for sorting by location
	public void onSortLocation() {
		if (eventList.isEmpty()) {
			Log.d(TAG_NULL, "sort location");
			return;
		}

		if (sortedByLocationList.isEmpty()) {
			//sortedByLocationList = sortByLocation();
		}

		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(c,
				android.R.layout.simple_list_item_1, eventList);
		l.setAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class FindSessions extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// retrieve object instance
			HTTPJson httpJsonObj = HTTPJson.getInstance();

			// build url from the different components
			String url = "http://api.uwaterloo.ca/v2/terms/" + term
					+ "/infosessions." + format + "?key=" + API_KEY;

			// obtain string in JSON format via HTTP GET
			String jsonString = httpJsonObj.connectHTTP(url);

			try {
				// parse JSON objects to obtain the data required and stores in
				// eventList
				eventList = httpJsonObj.readJSONString(jsonString);
			} catch (Exception e) {
				Log.d(TAG_JSON_ERROR, e + "");
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(c,
					android.R.layout.simple_list_item_1, eventList);
			l.setAdapter(adapter);
		}

	}
}