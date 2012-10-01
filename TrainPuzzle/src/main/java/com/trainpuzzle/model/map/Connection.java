package com.trainpuzzle.model.map;

public class Connection {

	private Track.Heading compassHeading1;
	private Track.Heading compassHeading2;
	
	public Connection(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		modifyConnection(inputHeading1, inputHeading2);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compassHeading1 == null) ? 0 : compassHeading1.hashCode() + compassHeading2.hashCode());
		result = prime * result
				+ ((compassHeading2 == null) ? 0 : compassHeading1.hashCode()+ compassHeading2.hashCode());
		return result;
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
		Connection other = (Connection) obj;
		
		boolean headingsAreDirectlyEqual = ((compassHeading1 == other.compassHeading1) && (compassHeading2 == other.compassHeading2));
		boolean headingsAreIndirectlyEqual = ((compassHeading1 == other.compassHeading2) && (compassHeading2 == other.compassHeading1));
		
		if ( !(headingsAreDirectlyEqual || headingsAreIndirectlyEqual) ) {
			return false;
		}
		return true;
	}

	public void modifyConnection(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		if(connectionIsValid(inputHeading1, inputHeading2)) {
			compassHeading1= inputHeading1;
			compassHeading2 = inputHeading2;
		}
	}
	
	public void rotateConnection() {
		
	}
	
	public int[] getHeadingValues() {
		int connectionValues[] = new int[2];
		connectionValues[0] = compassHeading1.getValue();
		connectionValues[1] = compassHeading2.getValue();
		return connectionValues;
	}
	
	public Track.Heading[] getHeadingPair() {
		Track.Heading connectionHeadings[] = new Track.Heading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	private boolean connectionIsValid(Track.Heading inputHeading1, Track.Heading inputHeading2) {
		if (inputHeading1 == inputHeading2) {
			return false;
		}
		return true;
	}	
}
