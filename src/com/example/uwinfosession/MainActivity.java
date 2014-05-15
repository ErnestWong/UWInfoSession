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
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity{

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
	
	private final String API_KEY = "b15ace6df5f1c774c94309b0d91912f3";
	private final String term = SPRING_2014;
	private final String format = "json";
	FindSessions task;
	public List<Event> eventList;
	
	private Context c;
	private Intent intent;

	private final ListView l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		l = (ListView)findViewById();	
	
		c = getApplicationContext();
		task = new FindSessions();
		task.execute();
	
		l.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
				intent = new Intent(c, /*SingleActivity.class*/);
				Bundle b = new Bundle();
				b = putBundle(eventList.get(position));
				intent.putExtra("event bundle", b);
				startActivity(intent);
  			}
		}); 
	}
	
	private Bundle putBundle(Event event){
		Bundle b = new Bundle();
		b.putString("date", event.getDate());
		b.putString("start", event.getStarttime());
		b.putString("end", event.getEndtime());
		b.putString("location", event.getLocation());
		b.putString("audience", event.getAudience());
		b.putString("program", event.getPrograms));
		b.putString("description", event.getDescription());
		b.putString("website", event.getWebsite());
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
			ListAdapter adapter = new ArrayAdapter<Event>(c, /*layout ID for view*/, eventList);
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
