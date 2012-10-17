package com.trainpuzzle.model.map;


public class Connection {
	//TODO: Write comment of why it's not set to null
	private CompassHeading compassHeading1 = CompassHeading.NORTHEAST;
	private CompassHeading compassHeading2 = CompassHeading.NORTHWEST;
	
	/* Public Interface */
	
	public Connection(CompassHeading compassHeading1, CompassHeading compassHeading2) {
		modifyConnection(compassHeading1, compassHeading2);
	}
	
	public int[] getHeadingValues() {
		int connectionValues[] = new int[2];
		connectionValues[0] = compassHeading1.getValue();
		connectionValues[1] = compassHeading2.getValue();
		return connectionValues;
	}
	
	public CompassHeading[] getCompassHeadingPair() {
		CompassHeading connectionHeadings[] = new CompassHeading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	public void modifyConnection(CompassHeading inputHeading1, CompassHeading inputHeading2) {
		if(connectionIsValid(inputHeading1, inputHeading2)) {
			compassHeading1= inputHeading1;
			compassHeading2 = inputHeading2;
		}
	}
	
	//TODO: possibly will need a rotateConnection() method in the future
	
	@Override
	public int hashCode() {
		return compassHeading1.hashCode() * compassHeading2.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}
		Connection other = (Connection) object;
		
		boolean headingsAreDirectlyEqual   = ((compassHeading1 == other.compassHeading1) && (compassHeading2 == other.compassHeading2));
		boolean headingsAreIndirectlyEqual = ((compassHeading1 == other.compassHeading2) && (compassHeading2 == other.compassHeading1));
		
		return headingsAreDirectlyEqual || headingsAreIndirectlyEqual;
	}
		
	private boolean connectionIsValid(CompassHeading inputHeading1, CompassHeading inputHeading2) {
		return inputHeading1 != inputHeading2;
	}

	void rotate90Degrees() {
		compassHeading1.rotate90DegreesClockwise();
		compassHeading2.rotate90DegreesClockwise();
	}	
}