package com.trainpuzzle.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.thoughtworks.xstream.*;


import com.trainpuzzle.exception.CannotLoadLockedLevelException;
import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.factory.LevelFactory;
import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.CampaignLevel;
import com.trainpuzzle.model.level.Level;

public class LevelManager {

	private Campaign campaign;
	private Level levelLoaded;
	
	public LevelManager(){
        campaign = new Campaign();
	}
	
	public LevelManager(Campaign campaign){
        this.campaign = campaign;
	}
		
	public void loadLevel(int levelNumber) throws CannotLoadLockedLevelException {
		if(campaign.getCampaignLevels().get(levelNumber - 1).isLocked){
			throw new CannotLoadLockedLevelException("Level " + levelNumber + " is locked.");
		}
		LevelFactory levelFactory = new LevelFactory();
		Level level = levelFactory.createLevel(levelNumber);
		levelLoaded = level;	

	}
	public void levelCompleted(){
		campaign.completeLevel(levelLoaded.getLevelNumber());
	}
	
	private void loadNextLevel() {
		int nextLevel = levelLoaded.getLevelNumber() + 1;
		try {
			loadLevel(nextLevel);
		} catch (CannotLoadLockedLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Level getLevel(){
		return levelLoaded;
	}
	
	public List<CampaignLevel> getLevels(){
		return campaign.getCampaignLevels();
	}

	public void saveLevel() {
		File file = new File("level.xml"); 
		try {
		// Create the necessary output streams to save the level.
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(levelLoaded, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// Print out exceptions. We should really display them in a dialog...
		catch (IOException e) { 
			System.out.println(e); 
		}
	}
  	public void loadLevel(File file) {

	    try {
	    	Level levelLoaded;
	    	XStream xstream = new XStream();
	    	levelLoaded = (Level)xstream.fromXML(file);
	        this.levelLoaded = levelLoaded;
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	    }
	    // Print out exceptions. We should really display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
	}
}