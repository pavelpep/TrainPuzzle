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
		private static final int numberOfCompassHeadings = 8;
		
		private CompassHeading(int headingValue) {
			this.headingValue = headingValue;
		}
		
		public static CompassHeading getCompassHeading(int headingValue) {
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
		
		public CompassHeading opposite() {
			final int oppositeIncrement = 4;
			int oppositeValue = (headingValue + oppositeIncrement) % numberOfCompassHeadings;
			return getCompassHeading(oppositeValue);
		}
		
		public int getValue() {
			return headingValue;
		}
	}

	private Set<Connection> connections;
	
	/* Public Interface */
	
	public Track() {
		connections = new HashSet<Connection>();
		Connection connection = new Connection(Track.CompassHeading.EAST, Track.CompassHeading.WEST);
		connections.add(connection);
	}
	
	public void rotateTrack() {
		final int rotateIncrement = 2;
		
		for(Connection connection : connections) {
			Track.CompassHeading rotatedCompassHeading[] = new Track.CompassHeading[2];
			
			for(int i = 0; i < 2; i++) {
				int rotatedValue = (connection.getCompassHeadingPair()[i].getValue() + rotateIncrement) % Track.CompassHeading.numberOfCompassHeadings;
				rotatedCompassHeading[i] = Track.CompassHeading.getCompassHeading(rotatedValue);
			}
			connection.modifyConnection(rotatedCompassHeading[0],rotatedCompassHeading[1]);
		}
		return;
	}
	
	/* Getters and Setters */
	
	public void addConnection(CompassHeading firstCompassHeading, CompassHeading secondCompassHeading) {
		connections.add(new Connection(firstCompassHeading, secondCompassHeading));
		return;
	}
	
	public Set<Connection> getConnections() {
		return connections;
	}
	
}



