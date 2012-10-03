package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import java.lang.Thread;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Map;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Location;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class Application {
	private Logger logger = Logger.getLogger(Application.class);
	private LevelLoader levelLoader;
	private TrackBuilder trackBuilder;
	private Simulator simulator;
	
	private Level loadedLevel;
	private Level loadedLevelWithTrack;
	
	/* Public Interface */
	
	public Application(int levelNumber) {
		//todo change int to enum?
		levelLoader = new LevelLoader();
		loadedLevel = levelLoader.loadLevel(levelNumber);
		trackBuilder = new TrackBuilder(loadedLevel);
		loadedLevelWithTrack = trackBuilder.getLevel();
	}
	
	public void runSimulation() {
		loadedLevelWithTrack = trackBuilder.getLevel();
		simulator = new Simulator(loadedLevelWithTrack);
		move();
	}
	
	public void placeTrack(Track track, int latitude, int longitude) {
		trackBuilder.placeTrack(track, latitude, longitude);
		loadedLevelWithTrack = trackBuilder.getLevel();
	}
	
	/* Private Functions */
	
	private boolean move() {
		Location endPoint = loadedLevel.getEndLocation();
		try {
			boolean canProceed = true;
			while(canProceed) {
				Thread.sleep(1000);
				canProceed = simulator.proceedNextTile();
				//TODO: call UI refresh method
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
	
	/* Getters and Setters */
	
	public Map getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Map getTrackMap() {
		return loadedLevelWithTrack.getMap();
	}
	
	
	
}
