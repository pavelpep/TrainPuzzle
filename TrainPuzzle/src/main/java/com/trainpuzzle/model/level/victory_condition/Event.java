package com.trainpuzzle.model.level.victory_condition;

import com.trainpuzzle.model.board.Station;

public class Event {
	private int time;
	private Station station;
	public static final int NOTIMELIMIT = -1;
	private String eventName;
	
	

	public Event(int time, Station station, String eventName) {
		this.eventName = eventName;
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


	public String getEventName() {
		return eventName;
	}

	public void setEventId(String eventName) {
		this.eventName = eventName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Event other = (Event) obj;
		return compareEvent(other);
	}
	
	private boolean compareEvent(Event event) {
		boolean timeIssue = time == NOTIMELIMIT || event.getTime() < time;
		//TODO: ensure that equals method handles time correctly for VictoryConditionEvaluator
		return timeIssue && eventName.equals(event.getEventName());
			
		
	}
	
}
