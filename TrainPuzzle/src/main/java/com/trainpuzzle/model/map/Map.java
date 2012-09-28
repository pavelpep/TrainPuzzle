package com.trainpuzzle.model.map;

/**
 * The Map class is essentially a wrapper for our gameboard.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */
public class Map {

private Tile[][] tiles;
	
	/**
	 * Initialises the map size to 60 x 40 {@link Tile}s in size
	 */
	public Map() 
	{
		tiles = new Tile[60][40];
		initializeTiles();
	}
	
	private void initializeTiles(){
		
		int mapWidth = tiles.length;
		int mapHeight = tiles[1].length;
		for(int i=0; i < mapWidth; i++){
			for(int j=0; j < mapWidth; j++){
				tiles[mapWidth][mapHeight] = new Tile();
				
			}
			
		}
		
	}
	
}