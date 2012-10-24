package com.trainpuzzle.model.level.victory_condition;

import com.trainpuzzle.model.board.Station;

public class Event implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int time;
	private Station station;
	private String eventName;
	
	

	public Event(int time, Station station) {
		this.time = time;
		this.station = station;
		this.createSationName();
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
	private void createSationName(){
		int row = station.getStationLocation().getRow();
		int column = station.getStationLocation().getColumn();
		eventName = "pass station at (" + row +"," + column +")";
	}
	
	private boolean compareEvent(Event event) {
		//TODO: ensure that equals method handles time correctly for VictoryConditionEvaluator
		return eventName.equals(event.getEventName());
			
		
	}
	
}
