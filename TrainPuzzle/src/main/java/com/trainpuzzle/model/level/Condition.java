package com.trainpuzzle.model.level;

public class Condition {
	private int latitude;
	private int longitude;
	public void setLocation(int[] location){
		this.latitude = location[0];
		this.longitude = location[1];
	}
	public int[] getLocation(){
		int[] location = new int[2];
		location[0] = this.latitude;
		location[1] = this.longitude;
		return location;
	}
}
