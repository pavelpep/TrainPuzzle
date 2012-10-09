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

	private int numberOfRows; //height
	private int numberOfColumns; //width
	
	/* Public Interface */
	
	public Map() {
		//Initialize the map with 60x40 tiles
		numberOfRows = 100;
		numberOfColumns = 100;
		tiles = new Tile[numberOfRows][numberOfColumns];
		initializeTiles();
		addSomeTrackTiles();
	}
	
	/*Private Functions */
	
	private void initializeTiles() {
			
		for(int r=0; r < numberOfRows; r++) {
			for(int c=0; c < numberOfColumns; c++) {
				tiles[r][c] = new Tile();
			}	
		}
	}
	
	/**
	 * Currently used to create some pre-generated track. This will be removed/modified in the future as we expect the
	 * user to place the track on the map.
	 */
	private void addSomeTrackTiles() {
		
		for(int c=0; c < 20; c++){
		 this.tiles[4][c].setTrack(new Track());
		}
	}
	
	/* Getters and Setters */
	
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	public void setTile(Tile tile, int row, int column) {
		tiles[row][column] = tile;
	}
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
}