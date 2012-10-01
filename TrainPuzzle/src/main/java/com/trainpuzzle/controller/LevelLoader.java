package com.trainpuzzle.controller;

import com.trainpuzzle.model.map.*;
import com.trainpuzzle.model.level.*;

/**
 * use for selecting the a level.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class LevelLoader {
	
	private Level level;
	
	LevelLoader(int levelNumber) {
		
		level = new Level(levelNumber);
		
		if (!hasMap()) {
			generateMap();
		}
	}
	
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Generate a random map
	 */
	public void generateMap() {
		
	}
	
	/**
	 * search for a possible way
	 */
	private boolean isValidMap() {
		return true;
	}
	
	private boolean hasMap() {
		return true;
	}
}
