package com.trainpuzzle.model.map;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class Track {
	
	public enum Heading {
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
		private static final int numberOfHeadings = 8;
		
		private Heading(int headingValue) {
			this.headingValue = headingValue;
		}
		
		public static Heading getHeading(int headingValue) {
			Heading headingResult = null;
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
		
		public Heading opposite() {
			final int oppositeIncrement = 4;
			int oppositeValue = (headingValue + oppositeIncrement) % numberOfHeadings;
			return getHeading(oppositeValue);
		}
		
		public int getValue() {
			return headingValue;
		}
	}

	private Set<Connection> connections;
	
	public Track() 
	{
		connections = new HashSet<Connection>();
		//System.out.println();
		
	}
	public void addConnection(Heading firstHeading, Heading secondHeading){
		connections.add(new Connection(firstHeading, secondHeading));
		return;
	}
	
	public Set<Connection> getConnections(){
		return connections;
	}
	
	public void rotateTrack() {
		final int rotateIncrement = 2;
		for(Connection connection : connections){
			int rotatedValue1 = (connection.getHeadingPair()[0].getValue() + rotateIncrement) % Track.Heading.numberOfHeadings;
			int rotatedValue2 = (connection.getHeadingPair()[1].getValue() + rotateIncrement) % Track.Heading.numberOfHeadings;
			Track.Heading rotatedHeading1 = Track.Heading.getHeading(rotatedValue1);
			Track.Heading rotatedHeading2 = Track.Heading.getHeading(rotatedValue2);
			connection.modifyConnection(rotatedHeading1,rotatedHeading2);
		}
		return;
	}
	
	
}



