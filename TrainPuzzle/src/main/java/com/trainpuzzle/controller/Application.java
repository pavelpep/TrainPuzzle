package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import java.lang.Thread;

import com.trainpuzzle.ui.windows.LoadedLevel;

import com.trainpuzzle.exception.TrainCrashException;
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
	
	private LoadedLevel uiLoadedLevel;
	
	/* Public Interface */
	
	public Application(int levelNumber, LoadedLevel uiLoadedLevel) {
		//todo change int to enum?
		levelLoader = new LevelLoader();
		loadedLevel = levelLoader.loadLevel(levelNumber);
		trackBuilder = new TrackBuilder(loadedLevel);
		this.uiLoadedLevel = uiLoadedLevel;
	}
	
	public void runSimulation() {
		loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
		simulator = new Simulator(loadedLevelWithTrack);
		move();
	}
	
	public void placeTrack(Track track, int latitude, int longitude) {
		trackBuilder.placeTrack(track, latitude, longitude);
		loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
	}
	
	/* Private Functions */
	
	private void move() {
		Location endPoint = loadedLevel.getEndLocation();
		
		while(!simulator.isVictoryConditionsSatisfied()) {
			
			uiLoadedLevel.redrawTrain(simulator.getTrain());
			try {
				Thread.sleep(100);
				simulator.proceedNextTile();
			} catch (TrainCrashException tce) {
				//TODO: call UI drawRefresh with a fallen over train
				logger.error(tce.getMessage(), tce.fillInStackTrace());
				
			} catch (InterruptedException ie) {
				logger.error(ie.getMessage(), ie.fillInStackTrace());
			}
			if(endPoint.equals(simulator.getTrain().getLocation())) {
				uiLoadedLevel.redrawTrain(simulator.getTrain());
				logger.info("Level has been cleared!");
			} 
		}
		
		
	}
	
	/* Getters and Setters */
	
	public Map getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Map getTrackMap() {
		return loadedLevelWithTrack.getMap();
	}
	
	
	
}
