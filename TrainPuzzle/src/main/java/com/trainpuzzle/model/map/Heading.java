package com.trainpuzzle.model.map;

public enum Heading {
	NORTHWEST, 				//   0       1       2
	NORTH, 					//    +------*------+   
	NORTHEAST,  				//    |             |
	EAST,      				//    |             |
	SOUTHEAST, 				//  7 *     TILE    * 3
	SOUTH,					//    |             |
	SOUTHWEST,				//    |             |
	WEST;					//    +------*------+
								//   6       5       4
	private int headingValue = ordinal();
	static final int NUMBEROFHEADINGS = values().length; //rename to capitals
	private final int OPPOSITE_ROTATION = 4;
	private final int CLOCKWISE_90DEGREES = 2;
	private final int COUNTERCLOCKWISE_90DEGREES = 6;

	private Heading() {
	}
	
	public static Heading getHeading(int headingValue) {
		for(Heading heading: values()) {
			int index = heading.ordinal();
			if (index == headingValue) {
				return heading;
			}
		}
		//TODO: Write exception case for when the parameter value is > 7 or < 0
		return NORTHWEST;
	}
	
	public Heading opposite() {
		return rotate(OPPOSITE_ROTATION);
	}
	
	public Heading clockwise90Degrees() {
		return rotate(CLOCKWISE_90DEGREES);
	}
	
	public Heading counterclockwise90Degrees() {
		return rotate(COUNTERCLOCKWISE_90DEGREES);
	}
	
	
	private Heading rotate(int amount) {
		int oppositeValue = (headingValue + amount) % NUMBEROFHEADINGS;
		return getHeading(oppositeValue);
	}
	
	public int getValue() {
		return headingValue;
	}
}