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
	private int mapWidth;
	private int mapHeight;
	
	public Map() {
		mapWidth = 100;
		mapHeight = 100;
		tiles = new Tile[mapWidth][mapHeight];
		initializeTiles();
		addSomeWaterTiles(); // for testing
	}
	
	private void initializeTiles() {
			
		for(int w=0; w < mapWidth; w++) {
			for(int h=0; h < mapHeight; h++) {
				tiles[w][h] = new Tile();
			}	
		}
	}
	
	private void addSomeWaterTiles() {
	 this.tiles[10][10].setLandscapeType("water");
	 this.tiles[5][5].setLandscapeType("water");
	 this.tiles[2][3].setLandscapeType("water");
	}
	
	public Tile getTile(int latitude, int longitude) {
		return tiles[latitude][longitude];
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public void setTile(Tile tile, int latitude, int longitude) {
		tiles[latitude][longitude] = tile;
	}
}