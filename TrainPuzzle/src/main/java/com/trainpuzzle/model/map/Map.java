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
	
	/* Public Interface */
	
	public Map() {
		mapWidth = 100;
		mapHeight = 100;
		tiles = new Tile[mapWidth][mapHeight];
		initializeTiles();
		addSomeTrackTiles(); // for testing
	}
	
	/*Private Functions */
	
	private void initializeTiles() {
			
		for(int w=0; w < mapWidth; w++) {
			for(int h=0; h < mapHeight; h++) {
				tiles[w][h] = new Tile();
			}	
		}
	}
	
	/**
	 * Currently used to create some pre-generated track. This will be removed/modified in the future as we expect the
	 * user to place the track on the map.
	 */
	private void addSomeTrackTiles() {
		for(int i=0; i < 20; i++){
		 this.tiles[4][i].setTrack(new Track());
		}
	}
	
	/* Getters and Setters */
	
	public Tile getTile(int latitude, int longitude) {
		return tiles[latitude][longitude];
	}
	
	public void setTile(Tile tile, int latitude, int longitude) {
		tiles[latitude][longitude] = tile;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
}