package com.trainpuzzle.model.map;

/**
 * The Tile class is essentially a super class which Tile type class's inherit from.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */
public  class Tile {

private int elevation;
private String tileType;
private Track track;
private Obstacle obstacle;

	public Tile() 
	{
		elevation = 0;
		tileType = "grass";
		
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
	
	/**
	 * @return hasTrack returns whether 
	 * there is a Track at this tile or not. 
	 */
	public boolean hasTrack() {
		
		if(track == null){
			return false;
		}else{
			return true;
		}
	}
	
	public void putTrack() {
		track = new Track();
	}
	
	public void removeTrack() {
		track = null ;
	}
}