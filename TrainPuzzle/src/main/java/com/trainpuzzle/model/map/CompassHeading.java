package com.trainpuzzle.model.map;

public enum CompassHeading {
	NORTHWEST(0), 				//   0       1       2
	NORTH(1), 					//    +------*------+   
	NORTHEAST(2),  				//    |             |
	EAST(3),      				//    |             |
	SOUTHEAST(4), 				//  7 *     TILE    * 3
	SOUTH(5),					//    |             |
	SOUTHWEST(6),				//    |             |
	WEST(7);					//    +------*------+
								//   6       5       4
	private int headingValue;
	static final int numberOfCompassHeadings = 8; //rename to capitals
	private final int OPPOSITE_ROTATION = 4;
	private final int CLOCKWISE_90DEGREES = 2;
	private final int COUNTERCLOCKWISE_90DEGREES = 6;

	private CompassHeading(int headingValue) {
		this.headingValue = headingValue;
	}
	
	public static CompassHeading getCompassHeading(int headingValue) {
		for(CompassHeading heading: values()) {
			int index = heading.ordinal();
			if (index == headingValue) {
				return heading;
			}
		}
		//TODO: Write exception case	
		return NORTHWEST;
	}
		
		/*
		CompassHeading headingResult = null;
		switch (headingValue) {
			case 0:
				headingResult = NORTHWEST;
				break;
			case 1:
				headingResult = NORTH;
				break;
			case 2:
				headingResult = NORTHEAST;
				break;
			case 3:
				headingResult = EAST;
				break;
			case 4:
				headingResult = SOUTHEAST;
				break;
			case 5:
				headingResult = SOUTH;
				break;
			case 6:
				headingResult = SOUTHWEST;
				break;
			case 7:
				headingResult = WEST;
				break;
		}
		return headingResult;
	}
	*/
	
	public CompassHeading opposite() {
		return rotate(OPPOSITE_ROTATION);
	}
	
	public CompassHeading clockwise90Degrees() {
		return rotate(CLOCKWISE_90DEGREES);
	}
	
	public CompassHeading counterclockwise90Degrees() {
		return rotate(COUNTERCLOCKWISE_90DEGREES);
	}
	
	
	private CompassHeading rotate(int amount) {
		int oppositeValue = (headingValue + amount) % numberOfCompassHeadings;
		return getCompassHeading(oppositeValue);
	}
	
	public int getValue() {
		return headingValue;
	}
}