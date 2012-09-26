package com.trainpuzzle.model.map;

/**
 * The Tile class is essentially a super class which Tile type class's inherihit from.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */
public abstract class Tile {

private int elevation;
private String tileType;

	public Tile() 
	{

	}

	/**
	 * @return the elevation
	 */
	public int getElevation() {
		return elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	/**
	 * @return the tileType
	 */
	public String getTileType() {
		return tileType;
	}

	/**
	 * @param tileType the tileType to set
	 */
	public void setTileType(String tileType) {
		this.tileType = tileType;
	}
	
}