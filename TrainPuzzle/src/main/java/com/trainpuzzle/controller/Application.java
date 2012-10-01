package com.trainpuzzle.controller;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Map;
import com.trainpuzzle.model.map.Track;

/**
 * 
 * @author $Author: jsc15 $
 * @version $Revision: 85 $
 * @since $Date: 2012-09-30 00:53:30 -0700 (Sun, 30 Sep 2012) $
 */
public class Application {
	private LevelLoader levelLoader;
	private TrackBuilder trackBuilder;
	private Simulator simulator;
	
	private Level loadedLevel;
	private Level modifiedLevel;
	
	public Application(int levelNumber) {
		//todo change int to enum?
		levelLoader = new LevelLoader();
		loadedLevel = levelLoader.loadLevel(levelNumber);
		trackBuilder = new TrackBuilder(loadedLevel);
		modifiedLevel = trackBuilder.getLevel();
	}
	
	public void runSimulation() {
		modifiedLevel = trackBuilder.getLevel();
		simulator = new Simulator(modifiedLevel);
	}
	
	public void placeTrack(Track track, int latitude, int longitude) {
		trackBuilder.placeTrack(track, latitude, longitude);
		modifiedLevel = trackBuilder.getLevel();
	}
	
	public Map getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Map getTrackMap() {
		return modifiedLevel.getMap();
	}
}
