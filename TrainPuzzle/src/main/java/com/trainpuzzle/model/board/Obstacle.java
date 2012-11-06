package com.trainpuzzle.model.board;

public class Obstacle implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public enum ObstacleType{
		ROCK,
		TREES,
		GREEN_STATION,
		RED_STATION
	}
	
	private ObstacleType obstacle;
		
	public Obstacle(ObstacleType type) {
		this.obstacle = type;
	}
	
	public ObstacleType getType() {
		return this.obstacle;
	}

	public void setType(ObstacleType obstacle) {
		this.obstacle = obstacle;
	}
}