package com.trainpuzzle.model.board;


public class Connection implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//TODO: Write comment of why it's not set to null
	private CompassHeading compassHeading1 = CompassHeading.NORTHEAST;
	private CompassHeading compassHeading2 = CompassHeading.NORTHWEST;
	
	/* Public Interface */
	
	public Connection(CompassHeading compassHeading1, CompassHeading compassHeading2) {
		this.compassHeading1= compassHeading1;
		this.compassHeading2 = compassHeading2;
	}
	
	public CompassHeading[] getCompassHeadingPair() {
		CompassHeading connectionHeadings[] = new CompassHeading[2];
		connectionHeadings[0] = compassHeading1;
		connectionHeadings[1] = compassHeading2;
		return connectionHeadings;
	}
	
	//TODO: possibly will need a rotateConnection() method in the future
	
	public boolean isInboundHeading(CompassHeading inbound) {
		CompassHeading outbound = inbound.opposite();
		return outbound == compassHeading1 || outbound == compassHeading2; 
	}
	
	public CompassHeading outboundorInbound(CompassHeading inbound) {
		CompassHeading outbound = inbound.opposite();
		if(outbound == compassHeading1) {
			return compassHeading2;
		}
		else {
			return compassHeading1;
		}
	}
	
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
		

	
	public void rotate45Degrees() {
		compassHeading1 = compassHeading1.rotate45DegreesClockwise();
		compassHeading2 = compassHeading2.rotate45DegreesClockwise();
	}

	public void rotate90Degrees() {
		compassHeading1 = compassHeading1.rotate90DegreesClockwise();
		compassHeading2 = compassHeading2.rotate90DegreesClockwise();
	}	
}