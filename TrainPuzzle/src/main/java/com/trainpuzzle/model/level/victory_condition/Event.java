package com.trainpuzzle.model.level.victory_condition;

import com.trainpuzzle.model.board.Station;

public class Event {
	private int time;
	private Station station;
//	private String eventId;
	
	public Event(int time, Station station) {
		this.time = time;
		this.station = station;
	}
/*	
	public Event(int time, Station station, String eventId) {
		this.eventID = eventId;
		this.time = time;
		this.station = station;
	}
*/	
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

/*
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
*/
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
		
		if(time == event.getTime()) {
			if(station.equals(event.getStation())) {
//				if(eventId.equals(event.getEventId())) {
					return true;
//				}
			}
		}
		return false;
	}
	
}