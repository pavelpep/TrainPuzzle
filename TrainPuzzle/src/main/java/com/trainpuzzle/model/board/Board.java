package com.trainpuzzle.model.board;

import java.util.Set;
import java.util.HashSet;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;

public class Board implements Observable, java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int rows = 15;
	private int columns = 20;
	private Tile[][] tiles;
	
	private transient Set<Observer> observerList = new HashSet<Observer>();
	
	public Board() {
		tiles = new Tile[getRows()][getColumns()];
		initializeTiles();
	}
	
	public Board(int numRows, int numColumns) {
		setRows(numRows);
		setColumns(numColumns);
		tiles = new Tile[getRows()][getColumns()];
		initializeTiles();
	}
	
	public void register(Observer observer) {
		if(observerList == null) {
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
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				tiles[row][column] = new Tile();
			}	
		}
	}
		
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	public Tile getTile(Location location) {
		return tiles[location.getRow()][location.getColumn()];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
}