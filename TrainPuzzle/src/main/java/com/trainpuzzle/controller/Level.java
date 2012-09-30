package com.trainpuzzle.controller;
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
	Level(int levelnumber){
		
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	
}
