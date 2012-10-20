package com.trainpuzzle.victory_condition;

import com.trainpuzzle.model.map.Station;

public class Event {
	private int time;
	private Station station;
	
	public Event(int time, Station station) {
		this.time = time;
		this.station = station;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
}
