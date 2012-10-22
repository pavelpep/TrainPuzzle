package com.trainpuzzle.model.board;

import java.util.Set;
import java.util.HashSet;

import com.trainpuzzle.observe.*;


public class Board implements Observable {

	public final int NUMBER_OF_ROWS = 15;
	public final int NUMBER_OF_COLUMNS = 20;
	private Tile[][] tiles;
	//private List<Observer> observerList = new LinkedList<Observer>();;
	private Set<Observer> observerList = new HashSet<Observer>();
	
	public Board() {
		tiles = new Tile[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		initializeTiles();
	}
	
	public void register(Observer observer){
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
		
	private void initializeTiles() {
			
		for(int row = 0; row < NUMBER_OF_ROWS; row++) {
			for(int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				tiles[row][column] = new Tile();
			}	
		}
	}
	
	/**
	 * Currently used to create some pre-generated track. This will be removed/modified in the future as we expect the
	 * user to place the track on the map.
	 */

	
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	public Tile getTile(Location location) {
		return tiles[location.getRow()][location.getColumn()];
	}
	
	/*//this replaces tiles instead of modifying. Not needed.
	public void setTile(Tile tile, int row, int column) {
		tiles[row][column] = tile;
	}
	*/
}