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
	
	/* Public Interface */
	
	public Track() {
		connections = new HashSet<Connection>();
	}
	
	public void rotateTrack() {
		final int rotateIncrement = 2;
		
		for(Connection connection : connections) {
			Track.Heading rotatedHeading[] = new Track.Heading[2];
			
			for(int i = 0; i < 2; i++) {
				int rotatedValue = (connection.getHeadingPair()[i].getValue() + rotateIncrement) % Track.Heading.numberOfHeadings;
				rotatedHeading[i] = Track.Heading.getHeading(rotatedValue);
			}
			connection.modifyConnection(rotatedHeading[0],rotatedHeading[1]);
		}
		return;
	}
	
	/* Getters and Setters */
	
	public void addConnection(Heading firstHeading, Heading secondHeading) {
		connections.add(new Connection(firstHeading, secondHeading));
		return;
	}
	
	public Set<Connection> getConnections() {
		return connections;
	}
	
}



