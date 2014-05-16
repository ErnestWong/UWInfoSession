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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//to do: 
//-sort list
//-check if adapter works
//-add new activity for single view
//-xml documents for single view and list view
//-add fragment for tab
//-check for internet connection (make sure the data saves for offline reading)
//-improve UI

public class MainActivity extends Activity{

	//error tags
	private final String TAG_NULL = "NULL EVENTLIST";
	
	// tags
	private final String TAG_DATA = "data";
	private final String TAG_ID = "id";
	private final String TAG_EMPLOYER = "employer";
	private final String TAG_DATE = "date";
	private final String TAG_START_TIME = "start_time";
	private final String TAG_END_TIME = "end_time";
	private final String TAG_LOCATION = "location";
	private final String TAG_WEBSITE = "website";
	private final String TAG_AUDIENCE = "audience";
	private final String TAG_PROGRAMS = "programs";
	private final String TAG_DESCRIPTION = "description";

	private final int WINTER_2014 = 1141;
	private final int SPRING_2014 = 1145;
	private final int FALL_2014 = 1149;

	//URL string constants
	private final String API_KEY = "b15ace6df5f1c774c94309b0d91912f3";
	private final String term = SPRING_2014 + "";
	private final String format = "json";
	
	//private async thread class
	private FindSessions task;

	//lists for each sorted option
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
		
		l = (ListView)findViewById(R.id.listView1);	

		c = getApplicationContext();
		task = new FindSessions();
		task.execute();

		l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				if(eventList.isEmpty()){
					Log.d(TAG_NULL, "OnClick");
					return;
				}
					intent = new Intent(c, SingleActivity.class);
					Bundle b = new Bundle();
					b = putBundle(eventList.get(position));
					intent.putExtra("event bundle", b);
					startActivity(intent);
  			}

		}); 
	}
	/*
	//on button click for sorting by employers
	public void onSortEmployer(){
		if(eventList.isEmpty()){
			Log.d(TAG_NULL, "sort employer");
			return;
		}

		if(sortedByEmployerList.isEmpty()){
			sortedByEmployerList = sortByEmployer;
		}	

		ListAdapter adapter = new ArrayAdapter<Event>(c, layout, sortedByEmployerList);
		l.setAdapter(adapter);

	}

	//on button click for sorting by date
	public void onSortDate(){
		if(eventList.isEmpty()){
			Log.d(TAG_NULL, "sort date");
			return;
		}

		if(sortedByEmployerList.isEmpty()){
			sortedByDateList = sortByDate;
		}

		ListAdapter adapter = new ArrayAdapter<Event>(c,layout , sortedByDateList);
		l.setAdapter(adapter);
	}

	//on button click for sorting by location
	public void onSortLocation(){
		if(eventList.isEmpty()){
			Log.d(TAG_NULL, "sort location");
			return;
		}

		if(sortedByLocationList.isEmpty()){
			sortedByLocationList = sortByLocation;
		}

		ListAdapter adapter = new ArrayAdapter<Event>(c, layout, sortedByLocationList);
		l.setAdapter(adapter);
	}

	*/
	
	//returns 1 if e1 > e2 (e1 lower alphabetically)
	//returns -1 if e1 < e2 (e2 lower alphabetically)
	//returns 0 if e1 == e2 (same alphabetically)
	public int compareEmployer(Event e1, Event e2){
		return e2.getEmployer().compareTo(e1.getEmployer());
	}

	//returns 1 if e1 > e2 (e1 lower alphabetically)
	//returns -1 if e1 < e2 (e2 lower alphabetically)
	//returns 0 if e1 == e2 (same alphabetically)
	public int compareLocation(Event e1, Event e2){
		return e2.getLocation().compareTo(e1.getLocation());
	}

	private Bundle putBundle(Event event){
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class FindSessions extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				String url = "http://api.uwaterloo.ca/v2/terms/" + term
						+ "/infosessions." + format + "?key=" + API_KEY;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				String inputLine;
				String result = "";

				while ((inputLine = reader.readLine()) != null) {
					// System.out.println(inputLine);
					result = result + inputLine;
				}
				reader.close();
				// System.out.println(result);
				readJSONString(result);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			CustomArrayAdapter adapter = new CustomArrayAdapter(c, R.id.listViewLabel, eventList);
			l.setAdapter(adapter);
		}

		private void readJSONString(String jsonStr) throws Exception {
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					JSONArray data = jsonObj.getJSONArray(TAG_DATA);

					eventList = new ArrayList<Event>(data.length());

					for (int i = 0; i < data.length(); i++) {
						JSONObject a = data.getJSONObject(i);

						String id = a.getString(TAG_ID);
						String employer = a.getString(TAG_EMPLOYER);
						String date = a.getString(TAG_DATE);
						String starttime = a.getString(TAG_START_TIME);
						String endtime = a.getString(TAG_END_TIME);
						String location = a.getString(TAG_LOCATION);
						String website = a.getString(TAG_WEBSITE);
						String audience = a.getString(TAG_AUDIENCE);
						String programs = a.getString(TAG_PROGRAMS);
						String description = a.getString(TAG_DESCRIPTION);

						// temporary event object that is added to eventList
						Event event = new Event(id, employer, date, starttime,
								endtime, location, website, audience, programs,
								description);
						eventList.add(event);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}

	}
}