package com.trainpuzzle.model.board;

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
			this.imgLocation = "src/main/resources/images/grass.png";
			break;
		case WATER:
			this.imgLocation = "src/main/resources/images/water.png";
			break;
		default:
			this.imgLocation = "src/main/resources/images/grass.png";
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