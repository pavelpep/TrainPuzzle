package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import java.lang.Thread;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Map;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Location;

/**
 * 
 * @author $Author: jsc15 $
 * @version $Revision: 85 $
 * @since $Date: 2012-09-30 00:53:30 -0700 (Sun, 30 Sep 2012) $
 */
public class Application {
	private Logger logger = Logger.getLogger(Application.class);
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
		move();
	}
	private boolean move() {
		Location endPoint = loadedLevel.getEndLocation();
		try {
			Thread.sleep(1000);
		while(simulator.go()) {
			Thread.sleep(1000);
			if(endPoint.equals(simulator.getTrain().getLocation())) {
				logger.info("YOU WIN!!");
				return true;
			}
			
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace());
		}
		logger.info("The train crash");
		return false;
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
