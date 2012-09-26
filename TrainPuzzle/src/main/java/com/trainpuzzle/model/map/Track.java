package com.trainpuzzle.model.map;


public class Track {

public final int NORTH_WEST = 0;  //   0       1       2
public final int NORTH = 1;       //    +------*------+            
public final int NORTH_EAST = 2;  //    |             |
public final int EAST = 3;        //    |             |
public final int SOUTH_EAST = 4;  //  7 *     TILE    * 3
public final int SOUTH = 5;       //    |             |
public final int SOUTH_WEST = 6;  //    |             |
public final int WEST = 7;			//    +------*------+
									//   6       5       4
	public Track() 
	{
		
	}
	
}