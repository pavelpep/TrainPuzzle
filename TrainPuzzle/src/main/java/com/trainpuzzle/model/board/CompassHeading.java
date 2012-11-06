package com.trainpuzzle.model.board;

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
	
	private static final int CLOCKWISE_45DEGREES = 1;
	private static final int CLOCKWISE_90DEGREES = 2;
	private static final int OPPOSITE_ROTATION = 4;
	private static final int COUNTERCLOCKWISE_90DEGREES = 6;
	private static final int COUNTERCLOCKWISE_45DEGREES = 7;
		
	public static CompassHeading getHeading(int headingValue) {
		return values()[headingValue];
	}
		
	public CompassHeading rotate45DegreesClockwise() {
		return rotate(CLOCKWISE_45DEGREES);
	}
	
	public CompassHeading rotate45DegreesCounterClockwise() {
		return rotate(COUNTERCLOCKWISE_45DEGREES);
	}
	
	public CompassHeading rotate90DegreesClockwise() {
		return rotate(CLOCKWISE_90DEGREES);
	}
	
	public CompassHeading rotate90DegreesCounterClockwise() {
		return rotate(COUNTERCLOCKWISE_90DEGREES);
	}
	
	public CompassHeading opposite() {
		return rotate(OPPOSITE_ROTATION);
	}
	
	private CompassHeading rotate(int amount) {
		int rotatedHeading = (ordinal() + amount) % values().length;
		return getHeading(rotatedHeading);
	}
	
	public int getValue() {
		return ordinal();
	}	
}