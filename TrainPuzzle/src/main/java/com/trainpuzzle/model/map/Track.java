package com.trainpuzzle.model.map;

import java.util.HashSet;
import java.util.Set;


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
		
		private Heading(int headingValue) {
			this.headingValue = headingValue;
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
	public void addConnection(){
		connections.add(new Connection(Heading.NORTH, Heading.SOUTH));
		return;
	}
	
	public void rotateTrack() {
		for(Connection connection : connections){
			
		}
		return;
	}
	
	
}



