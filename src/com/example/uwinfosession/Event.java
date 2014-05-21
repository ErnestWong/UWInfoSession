package com.example.uwinfosession;

import android.util.Log;

public class Event {

	private String id;
	private String employer;
	private String date;
	private String starttime;
	private String endtime;
	private String location;
	private String website;
	private String audience;
	private String programs;
	private String description;

	public Event(String a, String b, String c, String d, String e, String f,
			String g, String h, String i, String j) {
		id = a;
		employer = b;
		date = c;
		starttime = d;
		endtime = e;
		location = f;
		website = g;
		audience = h;
		programs = i;
		description = j;
		
		if(description.trim().equals("")) description = "N/A";
		
		if(description.contains("\\")){			
			description = description.replace("\\", "");
		}
		
		if(website.trim().equals("")) website = "N/A";
	}

	public String getId() {
		return id;
	}

	public String getEmployer() {
		return employer;
	}

	public String getDate() {
		return date;
	}

	public String getStarttime() {
		return starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public String getLocation() {
		return location;
	}

	public String getWebsite() {
		return website;
	}

	public String getAudience() {
		return audience;
	}

	public String getPrograms() {
		return programs;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString(){
		return employer + ": " + date; 
	}

}
