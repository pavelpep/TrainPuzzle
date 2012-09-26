package com.trainpuzzle.model.map;

/**
 * A tile on the board where a {@link Track} piece cannot be placed.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */
public class Obstacle {

private String type;
private String imgLocation;
	
	public Obstacle() 
	{
		setType("rock"); //initialise type 
	}

	/**
	 * @return the type of Obstacle
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the name of the type of obstact
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the imgLocation where we can find a Obstacle {@link Tile} to display
	 */
	public String getImgLocation() {
		return imgLocation;
	}

	/**
	 * @param imgLocation the imgLocation where we can find a Obstacle {@link Tile} to display
	 */
	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
	
}