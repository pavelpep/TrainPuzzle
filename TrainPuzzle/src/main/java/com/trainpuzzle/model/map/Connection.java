package com.trainpuzzle.model.map;

public class Connection {

	private Track.Heading compassHeading1;
	private Track.Heading compassHeading2;
	
	public Connection(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		modifyConnection(inputHeading1, inputHeading2);
	}
	
	public void modifyConnection(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		if(connectionIsValid(inputHeading1, inputHeading2)) {
			compassHeading1= inputHeading1;
			compassHeading2 = inputHeading2;
		}
		return;
	}
	
	public int[] getHeadingValues() {
		int connectionValues[] = new int[2];
		connectionValues[0] = compassHeading1.getValue();
		connectionValues[1] = compassHeading2.getValue();
		return connectionValues;
	}
	
	public Track.Heading[] getHeadings() {
		Track.Heading connectionHeadings[] = new Track.Heading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	private boolean connectionIsValid(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		return true;
	}
	
	//need to override equals and hashset
	
}
