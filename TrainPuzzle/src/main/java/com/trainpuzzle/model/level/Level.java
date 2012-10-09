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
	
	/* Public Interface */
	
	public Level(int levelNumber) {
		
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
		addSomeWaterTiles();
		startLocation = new Location(4,0);
		endLocation = new Location(4,19);
	}
	
	private void addSomeWaterTiles() {
		Tile tileToAddWater;
		
		tileToAddWater = map.getTile(10, 10);
		tileToAddWater.setLandscapeType("water");
		map.setTile(tileToAddWater, 10, 10);
		
		tileToAddWater = map.getTile(5, 5);
		tileToAddWater.setLandscapeType("water");
		map.setTile(tileToAddWater, 5, 5);
		
		tileToAddWater = map.getTile(2, 3);
		tileToAddWater.setLandscapeType("water");
		map.setTile(tileToAddWater, 2, 3);
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