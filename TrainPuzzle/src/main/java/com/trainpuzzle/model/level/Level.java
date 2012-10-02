package com.trainpuzzle.model.level;

import com.trainpuzzle.model.map.*;

/**
 * Class generates {@link Map} based on victory condition, {@link Landscape} and
 * {@link Obstacle}s of the level.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class Level {
	private Map map;
	private Location startLocation;
	private Location endLocation;
	//private Condition startCondition;
	//private Condition victoryCondition;
	//private int numberOfObstacles;
	
	/* Public Interface */
	
	public Level(int levelNumber){
		
		switch(levelNumber) {
			case 1:
				createLevelOne();
				break;
			default:
				createLevelOne(); //since we only have one level
				break;
		} 
	}
	
	/* Private Functions */
	
	private void createLevelOne() {
		map = new Map();
		startLocation = new Location(10,10);
		endLocation = new Location(10,30);
		
	}
	
	/* Setters and Getters */
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Location getStartLocation() {
		return this.startLocation;
	}
	
	public Location getEndLocation() {
		return this.endLocation;
	}

	
}
