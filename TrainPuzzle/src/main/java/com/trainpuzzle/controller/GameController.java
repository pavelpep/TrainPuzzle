package com.trainpuzzle.controller;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.infrastructure.FileManager;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observer;

public class GameController {
	
	private Config config;
	private Logger logger = Logger.getLogger(GameController.class);
	private Set<Observer> observerList = new HashSet<Observer>();
	
	private CampaignManager campaignManager;
	private LevelManager levelManager;
	
	private TrackPlacer trackPlacer;
	private Simulator simulator;
	private Level level;

	public GameController() {
		loadConfig();
		campaignManager = new CampaignManager(config.campaigns);
		campaignManager.selectCampaign(config.currentCampaign);
		levelManager = new LevelManager(campaignManager.getCampaign());
	}
	
	private void loadConfig(){
			config = (Config)FileManager.loadObject("config.xml");
	}
	
	private void saveConfig(){
		FileManager.saveObject(config, "config.xml");
	}
	public void startGame() {
		level = levelManager.getLevel();
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	
	public void startGame(int levelNumber) {
		levelManager.selectLevel(levelNumber);
		startGame();
	}

	public void startGame(File levelFile) {
		level = FileManager.loadLevel(levelFile.getAbsolutePath());
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
	}
	
	public void changeCampaign(int campaignNumber) {
		campaignManager.saveCampaign();
		campaignManager.selectCampaign(campaignNumber);
		config.currentCampaign = campaignNumber;
		saveConfig();
		levelManager = new LevelManager(campaignManager.getCampaign());
	}

	public void levelCompleted() {
		levelManager.levelCompleted();
		campaignManager.saveCampaign();
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
		for(int row = 0; row < level.getBoard().getRows(); row++) {
			for(int column = 0; column < level.getBoard().getColumns(); column++) {
				try {
					if(level.getBoard().getTile(row, column).hasTrack()) {
						trackPlacer.removeTrack(row, column);
					}
				} catch (CannotRemoveTrackException e) {
					logger.error(e.getMessage(), e.fillInStackTrace());
				}
			}
		}
	}
	
	public CampaignManager getCampaignManager() {
		return campaignManager;
	}
	
	public void resetCampaign(int campaignNumber) {
		campaignManager.resetCampaign(campaignNumber);
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
	
	public void saveCurrentLevel(File file) {
		FileManager.saveLevel(this.level, file.getAbsolutePath());
	}	
}