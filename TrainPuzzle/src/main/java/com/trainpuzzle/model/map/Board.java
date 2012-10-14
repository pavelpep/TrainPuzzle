package com.trainpuzzle.model.map;

import java.util.Set;
import java.util.HashSet;

import com.trainpuzzle.observe.*;


public class Board implements Observable {

	private Tile[][] tiles;
	private int numberOfRows = 100;; //height
	private int numberOfColumns = 100;; //width
	//private List<Observer> observerList = new LinkedList<Observer>();;
	private Set<Observer> observerList = new HashSet<Observer>();
	
	public Board() {
		tiles = new Tile[numberOfRows][numberOfColumns];
		initializeTiles();
		addSomeTrackTiles();
	}
	
	public void register(Observer observer){
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange();
		}
	}
		
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