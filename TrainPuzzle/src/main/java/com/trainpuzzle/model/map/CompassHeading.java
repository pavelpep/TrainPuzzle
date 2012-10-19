package com.trainpuzzle.model.map;

public enum CompassHeading {
	NORTHWEST, 				//   0       1       2
	NORTH, 					//    +------*------+   
	NORTHEAST,  		    //    |             |
	EAST,      				//    |             |
	SOUTHEAST, 				//  7 *     TILE    * 3
	SOUTH,					//    |             |
	SOUTHWEST,				//    |             |
	WEST;					//    +------*------+
						    //   6       5       4
	
	private static final int OPPOSITE_ROTATION = 4;
	private static final int CLOCKWISE_90DEGREES = 2;
	private static final int COUNTERCLOCKWISE_90DEGREES = 6;
	
	//TODO Remove redundant variables.
	//Removed empty constructor
	
	public static CompassHeading getHeading(int headingValue) {
		for(CompassHeading heading: values()) {
			int index = heading.ordinal();
			if (index == headingValue) {
				return heading;
			}
		}
		//TODO: Write exception case for when the parameter value is > 7 or < 0
		return NORTHWEST;
	}
	
	public CompassHeading opposite() {
		return rotate(OPPOSITE_ROTATION);
	}
	
	public CompassHeading rotate90DegreesClockwise() {
		return rotate(CLOCKWISE_90DEGREES);
	}
	
	public CompassHeading rotate90DegreesCounterClockwise() {
		return rotate(COUNTERCLOCKWISE_90DEGREES);
	}
	
	private CompassHeading rotate(int amount) {
		int oppositeValue = (ordinal() + amount) % values().length;
		return getHeading(oppositeValue);
	}
	
	/* depreciated with ordinal()
	public int getValue() {
		return ordinal();
	}
	*/
}