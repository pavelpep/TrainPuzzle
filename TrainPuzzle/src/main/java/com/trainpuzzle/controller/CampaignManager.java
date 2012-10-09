package com.trainpuzzle.controller;

//import com.trainpuzzle.model.map.*;
import com.trainpuzzle.model.level.*;

/**
 * use for selecting the a level.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */

public class CampaignManager {
	private Level levelLoaded;
	
	public Level loadLevel(int levelNumber) {
		levelLoaded = new Level(levelNumber);
		
		return levelLoaded;
	}
}
