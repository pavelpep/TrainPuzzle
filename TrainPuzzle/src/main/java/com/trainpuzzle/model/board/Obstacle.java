package com.trainpuzzle.model.board;


public class Obstacle {

	public enum ObstacleType{
		ROCK,
		TREES,
		GREEN_STATION_BUILDING,
		RED_STATION_BUILDING
	}
	
	private ObstacleType obstacle;
	private String imgLocation;
	
	/* Public Interface */
	
	public Obstacle(ObstacleType type) {
		this.obstacle = type;
		
		switch(this.obstacle) {
		case ROCK:
			this.imgLocation = "src/main/resources/images/rock.png";
			break;
		case TREES:
			this.imgLocation = "src/main/resources/images/trees.png";	
			break;
		case GREEN_STATION_BUILDING:
			this.imgLocation = "src/main/resources/images/greenStation_Front.png";
			break;
		case RED_STATION_BUILDING:
			this.imgLocation = "src/main/resources/images/redStation_Front.png";
			break;			
		default:
			this.imgLocation = "src/main/resources/images/rock.png";
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