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
	
	private boolean connectionIsValid(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		return true;
	}
}
