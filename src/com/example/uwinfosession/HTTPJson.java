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

import android.util.Log;

/**
 * Helper class to send HTTP requests and parse JSON string to obtain
 * information about info sessions
 * 
 * @author E Wong
 * 
 */
public class HTTPJson {

	// error tags
	private final String TAG_HTTP_ERROR = "HTTP ERROR";
	private final String TAG_PARSE = "PARSE";
	private final String TAG_NULL_STRING = "NULL JSON STRING";

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

	private static HTTPJson instance;

	/**
	 * private constructor
	 */
	private HTTPJson() {

	}

	/**
	 * singleton get instance
	 * 
	 * @return the instance of the class
	 */
	public static HTTPJson getInstance() {
		if (instance == null) {
			instance = new HTTPJson();
		}

		return instance;
	}

	/**
	 * establishes connection to HTTP link then retrieves the string in JSON
	 * format
	 * 
	 * @param url
	 *            of the API link to send GET request to
	 * @return the string in JSON format from API
	 */
	public String connectHTTP(String url) {
		try {
			// create URl object and establish connection to internet
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// open buffer reader to prepare to read the page
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String inputLine;
			String result = "";

			// reads the page and stores text into a string
			while ((inputLine = reader.readLine()) != null) {
				result = result + inputLine;
			}
			reader.close();

			// returns the string which is in JSON format
			return result;

		} catch (MalformedURLException e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		} catch (Exception e) {
			Log.d(TAG_HTTP_ERROR, e + "");
			e.printStackTrace();
		}
		// returns null if any error occurs
		return null;
	}

	/**
	 * uses java JSON library to extract meaningful data from JSON string
	 * 
	 * @param jsonStr
	 *            string in JSON format
	 * @return list containing the Event objects
	 * @throws Exception
	 */
	public List<Event> readJSONString(String jsonStr) throws Exception {
		// checks if input string is null
		if (jsonStr == null) {
			Log.d(TAG_NULL_STRING, "readJSONString method");
		}

		else {
			try {
				// create JSON object and JSON array
				JSONObject jsonObj = new JSONObject(jsonStr);
				JSONArray data = jsonObj.getJSONArray(TAG_DATA);

				// temporary list instantiated
				List<Event> tmpList = new ArrayList<Event>(data.length());

				// loops through all the elements in JSON array and extracts
				// useful data from it according to the tags
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

					Log.d(TAG_PARSE, employer);

					// temporary event object that is added to eventList
					Event event = new Event(id, employer, date, starttime,
							endtime, location, website, audience, programs,
							description);

					tmpList.add(event);
				}

				// returns the list that is filled up by the event objects
				return tmpList;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
}
