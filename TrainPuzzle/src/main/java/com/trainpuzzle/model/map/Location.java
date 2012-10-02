package com.trainpuzzle.model.map;

public class Location {
	
	private int latitude;
	private int longitude;
	
	/* Public Interface */
	
	public Location(int latitude, int longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public boolean equals(Location location){
		if(this.latitude == location.getLatitude()&&this.longitude == location.getLongitude()){
			return true;
		}
		return false;
	}

	/* Getters and Setters */
	
	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

}
