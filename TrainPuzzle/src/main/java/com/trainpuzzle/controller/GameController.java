package com.trainpuzzle.controller;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;

public class GameController{
	
	private Logger logger = Logger.getLogger(Application.class);
	private Set<Observer> observerList = new HashSet<Observer>();
	private CampaignManager campaignManager;
	private TrackPlacer trackPlacer;
	private Simulator simulator;
	private Level level;
	private Level loadedLevelWithTrack;

	public void startGame(int levelNumber) {
		campaignManager = new CampaignManager();
		level = campaignManager.loadLevel(levelNumber);
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	public void startGame(File levelFile) {
		campaignManager = new CampaignManager();
		level = campaignManager.loadLevel(levelFile);
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	
	public Simulator getSimulator() {
		return simulator;
	}
	 	
	/**
	 * 
	 * @deprecated this function been replaced with {@link com.trainpuzzle.controller.run()}
	 */
	public void runSimulation() {
		simulator.run();
	}
	/**
	 * 
	 * @deprecated this function been replaced with {@link com.trainpuzzle.controller.reset()}
	 */
	public void resetSimulation() {
	    simulator.reset();
	}
	
	public void placeTrack(Track track, int row, int column) {
		try {
			trackPlacer.placeTrack(track, row, column);
			loadedLevelWithTrack = trackPlacer.getLevelWithTrack();
			
		} catch (CannotPlaceTrackException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			for(Observer observer : observerList) {
				observer.notifyChange(e);
			}
		}
	}
	
	public void removeTrack(int row, int column) {
		try {
			trackPlacer.removeTrack(row, column);
			loadedLevelWithTrack = trackPlacer.getLevelWithTrack();
		} catch (CannotRemoveTrackException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			for(Observer observer : observerList) {
				observer.notifyChange(e);
			}
		}
	}
	
	public void removeAllTracks() {
		for(int row = 0; row < level.getBoard().rows; row++) {
			for(int column = 0; column < level.getBoard().columns; column++) {
				try {
					if(level.getBoard().getTile(row, column).hasTrack()){
						trackPlacer.removeTrack(row, column);
					}
				} catch (CannotRemoveTrackException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
				}
			}
		}
		
	}
	
	/* Getters and Setters */
	
	public CampaignManager getCampaignManager() {
		return campaignManager;
	}
	
	public Board getBlankMap() {
		return level.getBoard();
	}
	
	public Board getTrackMap() {
		return loadedLevelWithTrack.getBoard();
	}
	
	public Level getLevel() {
		return this.level;
	}

	public void saveCurrentLevel(File file){
		campaignManager.saveLevel(file);
	}
	
}


