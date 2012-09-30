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
		
		public Heading opposite() {
			final int oppositeIncrement = 4;
			int oppositeValue = (headingValue + oppositeIncrement) % numberOfHeadings;
			Heading oppositeHeading = null;
			switch (oppositeValue) {
				case 0:
					oppositeHeading = NORTHWEST;
					break;
				case 1:
					oppositeHeading = NORTH;
					break;
				case 2:
					oppositeHeading = NORTHEAST;
					break;
				case 3:
					oppositeHeading = EAST;
					break;
				case 4:
					oppositeHeading = SOUTHEAST;
					break;
				case 5:
					oppositeHeading = SOUTH;
					break;
				case 6:
					oppositeHeading = SOUTHWEST;
					break;
				case 7:
					oppositeHeading = WEST;
					break;
			}
			return oppositeHeading;
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
		for(Connection connection : connections){
			connection.modifyConnection(connection.getHeadings()[0].opposite(), connection.getHeadings()[1].opposite());
		}
		return;
	}
	
	
}



