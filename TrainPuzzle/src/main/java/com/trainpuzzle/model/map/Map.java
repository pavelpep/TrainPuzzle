package com.trainpuzzle.model.map;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Map {

	private Tile[][] tiles;
	private int[] startPoint;	

//Initialize the map with 60x40 tiles

	public Map() {
		tiles = new Tile[60][40];
		startPoint = new int[2];
		initializeTiles();
	}
	
	private void initializeTiles() {
		int mapWidth = tiles.length;
		int mapHeight = tiles[1].length;
		
		for(int i=0; i < mapWidth; i++) {
			for(int j=0; j < mapWidth; j++) {
				tiles[mapWidth][mapHeight] = new Tile();
			}	
		}
	}
	
	public int[] getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int[] startPoint) {
		this.startPoint = startPoint;
	}
	
	public Tile getTile(int latitude, int longitude) {
		return tiles[latitude][longitude];
	}
}