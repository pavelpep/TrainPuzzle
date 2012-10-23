package com.trainpuzzle.model.board;

import com.trainpuzzle.infrastructure.Images;

public class Landscape {
	
	public enum LandscapeType {
		GRASS,
		WATER
	}
	
	private LandscapeType landscape;
	private String imgLocation;

	public Landscape(LandscapeType type) {
		this.landscape = type;
		
		switch(this.landscape) {
		case GRASS:
			//this.imgLocation = "src/main/resources/images/grass.png";
			this.imgLocation = Images.GRASS;
			break;
		case WATER:
			//this.imgLocation = "src/main/resources/images/water.png";
			this.imgLocation = Images.WATER;
			break;
		default:
			//this.imgLocation = "src/main/resources/images/grass.png";
			this.imgLocation = Images.GRASS;
		}
	}
	
	/* Getters and Setters  */	

	public LandscapeType getType() {
		return this.landscape;
	}
	
	public void setType(LandscapeType landscape) {
		this.landscape = landscape;
	}
	
	public String getImgLocation() {
		return this.imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
	
}