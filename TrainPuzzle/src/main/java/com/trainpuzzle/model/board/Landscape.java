package com.trainpuzzle.model.board;

public class Landscape implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum LandscapeType {
		GRASS,
		WATER,
		ROUGHDIRT,
		DIRT,
		LIGHTDIRT,
		DARKSWAMP,
		MEDSWAMP,
		LIGHTSWAMP
	}
	
	private LandscapeType landscapeType;
	private int multiplier = 1;

	public Landscape(LandscapeType landscapeType) {
		this.landscapeType = landscapeType;	
	}

	public LandscapeType getType() {
		return landscapeType;
	}
	
	public void setType(LandscapeType landscapeType) {
		this.landscapeType = landscapeType;
		switch (landscapeType){
		case ROUGHDIRT:
			this.multiplier=3;
			break;
		case DARKSWAMP:
			this.multiplier=3;
			break;
		case DIRT:
			this.multiplier=2;
			break;			
		case MEDSWAMP:
			this.multiplier=2;
			break;
		default:
			this.multiplier=1;
			break;
		}
	}
	
	public int getMultiplier() {
		return multiplier;
	}
}