package com.trainpuzzle.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.trainpuzzle.exception.CannotLoadLockedLevelException;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;

public class GameController {
	
	private static final int DEFAULT_CAMPAIGN = 1;
	private Logger logger = Logger.getLogger(Application.class);
	private Set<Observer> observerList = new HashSet<Observer>();
	
	private CampaignManager campaignManager;
	private LevelManager levelManager;
	
	private TrackPlacer trackPlacer;
	private Simulator simulator;
	private Level level;

	public GameController(){
		campaignManager = new CampaignManager();
		campaignManager.selectCampaign(DEFAULT_CAMPAIGN);
		levelManager = new LevelManager(campaignManager.getCampaign());
	}
	
	public void startGame() {
		level = levelManager.getLevel();
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	
	public void startGame(int levelNumber) {
		try {
			levelManager.loadLevel(levelNumber);
		} catch (CannotLoadLockedLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startGame();
	}

	public void startGame(File levelFile) {
		level = loadLevel(levelFile);
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	
	public void changeCampaign(int campaignNumber){
		campaignManager.selectCampaign(campaignNumber);
		levelManager = new LevelManager(campaignManager.getCampaign());
	}

	 	
	
	public void placeTrack(Track track, int row, int column) {
		try {
			trackPlacer.placeTrack(track, row, column);
			
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
		}
		catch (CannotRemoveTrackException e) {
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
	public LevelManager getLevelManager() {
		return levelManager;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public Simulator getSimulator() {
		return simulator;
	}
	
	public void saveCurrentLevel(File file){
		saveLevel(this.level, file);
	}
	
	private void saveLevel(Level level, File file) {
	      try {
	        // Create the necessary output streams to save the level.
	        PrintStream out = new PrintStream(file);
	        XStream xstream = new XStream();
	        xstream.toXML(level, out);
	        
	        System.out.println("wrote to file: " + file.getAbsoluteFile());
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (IOException e) { System.out.println(e); }
	    
	  }
	private Level loadLevel(File file) {
		  
		Level loadedLevel = new Level(3);
		
	      try {
	        XStream xstream = new XStream();
	        loadedLevel = (Level)xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	        
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	     
	      return loadedLevel;
	  }
	
}


