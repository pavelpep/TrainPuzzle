package com.trainpuzzle.model.map;

import com.trainpuzzle.model.map.Track.CompassHeading;

public class Connection {
	private Track.CompassHeading compassHeading1;
	private Track.CompassHeading compassHeading2;
	
	/* Public Interface */
	
	public Connection(Track.CompassHeading compassHeading1, Track.CompassHeading compassHeading2) {
		modifyConnection(compassHeading1, compassHeading2);
	}
	
	public int[] getHeadingValues() {
		int connectionValues[] = new int[2];
		connectionValues[0] = compassHeading1.getValue();
		connectionValues[1] = compassHeading2.getValue();
		return connectionValues;
	}
	
	public Track.CompassHeading[] getCompassHeadingPair() {
		Track.CompassHeading connectionHeadings[] = new Track.CompassHeading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	public void modifyConnection(Track.CompassHeading inputHeading1, Track.CompassHeading inputHeading2) {
		if(connectionIsValid(inputHeading1, inputHeading2)) {
			compassHeading1= inputHeading1;
			compassHeading2 = inputHeading2;
		}
	}
	
	//TODO: possibly will need a rotateConnection() method in the future
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compassHeading1 == null) ? 0 : compassHeading1.hashCode() + compassHeading2.hashCode());
		result = prime * result
				+ ((compassHeading2 == null) ? 0 : compassHeading1.hashCode() + compassHeading2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Connection otherConnection = (Connection) object;
		
		boolean headingsAreDirectlyEqual = ((compassHeading1 == otherConnection.compassHeading1) && (compassHeading2 == otherConnection.compassHeading2));
		boolean headingsAreIndirectlyEqual = ((compassHeading1 == otherConnection.compassHeading2) && (compassHeading2 == otherConnection.compassHeading1));
		
		if ( !(headingsAreDirectlyEqual || headingsAreIndirectlyEqual) ) {
			return false;
		}
		return true;
	}
	
	/* Private Functions */
	
	private boolean connectionIsValid(Track.CompassHeading inputHeading1, Track.CompassHeading inputHeading2) {
		if (inputHeading1 == inputHeading2) {
			return false;
		}
		return true;
	}	
}
