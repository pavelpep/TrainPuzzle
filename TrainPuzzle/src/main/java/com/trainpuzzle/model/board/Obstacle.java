package com.trainpuzzle.model.board;

import com.trainpuzzle.infrastructure.Images;

public class Obstacle implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	public enum ObstacleType{
		ROCK,
		TREES,
		GREEN_STATION,
		RED_STATION
	}
	
	private ObstacleType obstacle;
	private String imgLocation;
	
	/* Public Interface */
	
	public Obstacle(ObstacleType type) {
		this.obstacle = type;
		
		switch(this.obstacle) {
		case ROCK:
			this.imgLocation = Images.ROCK;
			break;
		case TREES:
			this.imgLocation = Images.TREES;
			break;
		case GREEN_STATION:
			this.imgLocation = Images.GREEN_STATION;
			break;
		case RED_STATION:
			this.imgLocation = Images.RED_STATION;
			break;			
		default:
			this.imgLocation = Images.ROCK;
		}
	}
	
	/* Getters and Setters  */

	public ObstacleType getType() {
		return this.obstacle;
	}

	public void setType(ObstacleType obstacle) {
		this.obstacle = obstacle;
	}

	public String getImgLocation() {
		return this.imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
}