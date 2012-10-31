package com.trainpuzzle.model.board;

import java.util.Set;
import java.util.HashSet;

import com.trainpuzzle.observe.*;


public class Board implements Observable, java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	public int rows = 15;
	public int columns = 20;
	private Tile[][] tiles;
	
	private transient Set<Observer> observerList = new HashSet<Observer>();
	
	public Board() {
		tiles = new Tile[rows][columns];
		initializeTiles();
	}
	
	public Board(int numRows, int numColumns) {
		rows = numRows;
		columns = numColumns;
		tiles = new Tile[rows][columns];
		initializeTiles();
	}
	
	public void register(Observer observer){
	   if(observerList == null){
		   observerList = new HashSet<Observer>();
	   }
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
		
	private void initializeTiles() {
			
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
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