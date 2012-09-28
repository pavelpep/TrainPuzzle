package com.trainpuzzle.model.map;

import java.util.HashSet;
import java.util.Set;


public class Track {

	
/*
public final int NORTH_WEST = 0;  //   0       1       2
public final int NORTH = 1;       //    +------*------+            
public final int NORTH_EAST = 2;  //    |             |
public final int EAST = 3;        //    |             |
public final int SOUTH_EAST = 4;  //  7 *     TILE    * 3
public final int SOUTH = 5;       //    |             |
public final int SOUTH_WEST = 6;  //    |             |
public final int WEST = 7;		  //    +------*------+
*/						  //   6       5       4
	public enum Heading {
		NORTHWEST(0), 
		NORTH(1), 
		NORTHEAST(2), 
		EAST(3), 
		SOUTHEAST(4), 
		SOUTH(5), 
		SOUTHWEST(6), 
		WEST(7);
		
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



