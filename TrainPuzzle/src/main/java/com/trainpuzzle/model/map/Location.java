package com.trainpuzzle.model.map;

public class Location {
	
	private int row;
	private int column;
	
	/* Public Interface */
	
	public Location(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null){
			return false;
		}
		Location location = (Location) object;
		return this.row == location.getRow() && this.column == location.getColumn();
	}

	/* Getters and Setters */
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}