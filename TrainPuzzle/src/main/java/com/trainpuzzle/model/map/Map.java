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

//Initialize the map with 60x40 tiles

	public Map() {
		tiles = new Tile[60][40];
		initializeTiles();
		addSomeWaterTiles(); // for testing
	}
	
	private void initializeTiles() {
		int mapWidth = tiles.length - 1;
		int mapHeight = tiles[1].length - 1;
		
		for(int i=0; i < mapWidth; i++) {
			for(int j=0; j < mapHeight; j++) {
				tiles[mapWidth][mapHeight] = new Tile();
			}	
		}
	}
	public void addSomeWaterTiles() {
	 tiles[10][10].setLandscape("water");
	 tiles[5][5].setLandscape("water");
	 tiles[2][2].setLandscape("water");
	}
	
	public Tile getTile(int latitude, int longitude) {
		return tiles[latitude][longitude];
	}
}