package com.example.uwinfosession;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Sorter {
	
	private final String TAG_SORT_ERROR = "SORT ERROR";
	
	public List<Event> sortList(List<Event> list, String sortBy) {
		
		List<Event> sorted = new ArrayList <Event>(list.size());
		int minIndex;

		if(sortBy == "employer"){
			
			while(!list.isEmpty()){
				minIndex = 0;
				for(int i = 1; i < list.size(); i++){
					if(compareEmployer(list.get(i), list.get(minIndex)) > 0){
						minIndex = i;
					}
				}
				sorted.add(list.get(minIndex));
				list.remove(minIndex);
			}
		}
		else if(sortBy == "location"){
			
			while(!list.isEmpty()){
				minIndex = 0;
				for(int i = 1; i < list.size(); i++){
					if(compareLocation(list.get(i), list.get(minIndex)) > 0){
						minIndex = i;
					}
				}
				sorted.add(list.get(minIndex));
				list.remove(minIndex);
			}
		}
		else{
			sorted = list;
			Log.d(TAG_SORT_ERROR, "unsorted; wrong identifier string");
		}
		return sorted;
	}

	// returns 1 if e1 > e2 (e1 lower alphabetically)
	// returns -1 if e1 < e2 (e2 lower alphabetically)
	// returns 0 if e1 == e2 (same alphabetically)
	public int compareEmployer(Event e1, Event e2) {
		return e2.getEmployer().compareTo(e1.getEmployer());
	}

	// returns 1 if e1 > e2 (e1 lower alphabetically)
	// returns -1 if e1 < e2 (e2 lower alphabetically)
	// returns 0 if e1 == e2 (same alphabetically)
	public int compareLocation(Event e1, Event e2) {
		return e2.getLocation().compareTo(e1.getLocation());
	}
}
