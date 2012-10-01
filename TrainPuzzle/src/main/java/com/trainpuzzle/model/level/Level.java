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
	private Condition startCondition;
	private Condition victoryCondiction;
	private int numberOfObstacles;
	
	Level(int levelnumber){
		
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
}
