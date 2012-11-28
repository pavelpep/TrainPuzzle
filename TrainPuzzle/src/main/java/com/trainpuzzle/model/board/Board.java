package com.trainpuzzle.model.board;

import com.trainpuzzle.model.board.Landscape.LandscapeType;

public class Board implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int rows = 15;
	private int columns = 20;
	private Tile[][] tiles;
	
	public Board() {
		tiles = new Tile[getRows()][getColumns()];
		initializeTiles(LandscapeType.GRASS);
	}
	
	public Board(int numRows, int numColumns) {
		setRows(numRows);
		setColumns(numColumns);
		tiles = new Tile[getRows()][getColumns()];
		initializeTiles(LandscapeType.GRASS);
	}
	
	public Board(int numRows, int numColumns, LandscapeType landscapeType) {
		setRows(numRows);
		setColumns(numColumns);
		tiles = new Tile[getRows()][getColumns()];
		initializeTiles(landscapeType);
	}
	
	public void resetStationCargo(Board orignalBoard) {
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				if(tiles[row][column].hasStationBuilding()) {
					Station newStation = orignalBoard.getTile(row, column).getStation();
					Station currentStation = tiles[row][column].getStation();
					
					currentStation.setCargo(newStation);
				}
			}
		}
	}

	private void initializeTiles(LandscapeType landscapeType) {
		for(int row = 0; row < getRows(); row++) {
			for(int column = 0; column < getColumns(); column++) {
				tiles[row][column] = new Tile(landscapeType);
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