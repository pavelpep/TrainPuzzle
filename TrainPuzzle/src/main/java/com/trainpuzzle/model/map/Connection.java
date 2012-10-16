package com.trainpuzzle.model.map;


public class Connection {
	//TODO: Write comment of why it's not set to null
	private Heading compassHeading1 = Heading.NORTHEAST;
	private Heading compassHeading2 = Heading.NORTHWEST;
	
	/* Public Interface */
	
	public Connection(Heading compassHeading1, Heading compassHeading2) {
		modifyConnection(compassHeading1, compassHeading2);
	}
	
	public int[] getHeadingValues() {
		int connectionValues[] = new int[2];
		connectionValues[0] = compassHeading1.getValue();
		connectionValues[1] = compassHeading2.getValue();
		return connectionValues;
	}
	
	public Heading[] getCompassHeadingPair() {
		Heading connectionHeadings[] = new Heading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	public void modifyConnection(Heading inputHeading1, Heading inputHeading2) {
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
		
	private boolean connectionIsValid(Heading inputHeading1, Heading inputHeading2) {
		return inputHeading1 != inputHeading2;
	}

	void rotate90Degrees() {
		compassHeading1.clockwise90Degrees();
		compassHeading2.clockwise90Degrees();
	}	
}