package com.trainpuzzle.model.board;

public class Landscape implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum LandscapeType {
		GRASS,
		WATER
	}
	
	private LandscapeType landscapeType;

	public Landscape(LandscapeType landscapeType) {
		this.landscapeType = landscapeType;		
	}
	
	/* Getters and Setters  */	

	public LandscapeType getType() {
		return landscapeType;
	}
	
	public void setType(LandscapeType landscapeType) {
		this.landscapeType = landscapeType;
	}
}