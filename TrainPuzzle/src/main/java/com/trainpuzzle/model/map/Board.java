package com.trainpuzzle.model.map;

import java.util.LinkedList;
import java.util.List;
import com.trainpuzzle.observe.*;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Board implements Observable{

	private Tile[][] tiles;
	private int numberOfRows; //height
	private int numberOfColumns; //width
	private List<Observer> observerList;
	
	/* Public Interface */
	
	public Board() {
		//Initialize the map with 60x40 tiles
		numberOfRows = 100;
		numberOfColumns = 100;
		tiles = new Tile[numberOfRows][numberOfColumns];
		initializeTiles();
		addSomeTrackTiles();
		observerList = new LinkedList<Observer>();
	}
	
	public void register(Observer observer){
		
	}
	public void notifyAllObservers(){
		
	}
	
	/*Private Functions */
	

	
	private void initializeTiles() {
			
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				tiles[row][column] = new Tile();
			}	
		}
	}
	
	/**
	 * Currently used to create some pre-generated track. This will be removed/modified in the future as we expect the
	 * user to place the track on the map.
	 */
	private void addSomeTrackTiles() {
		
		for(int column = 0; column < 20; column++){
		 this.tiles[4][column].setTrack(new Track());
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