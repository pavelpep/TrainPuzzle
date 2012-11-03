package com.trainpuzzle.model.board;


public class Landscape implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	public enum LandscapeType {
		GRASS,
		WATER
	}
	
	private LandscapeType landscape;


	public Landscape(LandscapeType type) {
		this.landscape = type;
		
	}
	
	/* Getters and Setters  */	

	public LandscapeType getType() {
		return this.landscape;
	}
	
	public void setType(LandscapeType landscape) {
		this.landscape = landscape;
	}
	
}