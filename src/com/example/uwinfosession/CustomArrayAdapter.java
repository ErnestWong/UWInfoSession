package com.example.uwinfosession;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<Event> {

	private String TAG = "gotView";
	private int count = 0;
	
	private Context c;
	private int resourceId;
	private List<Event> list;
	private TextView txtView;

	public CustomArrayAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		c = context;
		resourceId = resource;
		list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		// if view is null then inflate view
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resourceId, parent, false);
			
			Log.d(TAG, count + "");
			count++;
		}

		//txtView = (TextView) convertView.findViewById(android.R.id.text1);
		//txtView.setText(list.get(position).getEmployer());
		
		Log.d("CHECK EMPLOYER", list.get(position).getEmployer());

		return convertView;
	}
}
