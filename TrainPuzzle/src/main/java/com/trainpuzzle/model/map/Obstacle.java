package com.trainpuzzle.model.map;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *
 */

public class Obstacle {

	private String name;
	private String imgLocation;
	
	/* Public Interface */
	
	public Obstacle() {
		setName("rock");
	}
	
	/* Getters and Setters  */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
}