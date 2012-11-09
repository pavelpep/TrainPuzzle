package com.trainpuzzle.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.thoughtworks.xstream.*;


import com.trainpuzzle.exception.CannotLoadLockedLevelException;
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
		if(campaign.getCampaignLevels().get(levelNumber - 1).hasUserSave){
			String filename = "Campaigns/" + campaign.getCampaignName() + "/Saves/" + levelNumber + ".xml";
			Level level = loadLevel(filename);
			levelLoaded = level;	
		}else{
			LevelFactory levelFactory = new LevelFactory();
			Level level = levelFactory.createLevel(levelNumber);
			levelLoaded = level;	
		}

	}
	public void levelCompleted(){
		campaign.completeLevel(levelLoaded.getLevelNumber());
	}
	
	public void loadNextLevel() {
		int nextLevel = levelLoaded.getLevelNumber() + 1;
		try {
			loadLevel(nextLevel);
		} catch (CannotLoadLockedLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveCurrentLevel(){
		int levelNumber = levelLoaded.getLevelNumber();
		String filename = "Campaigns/" + campaign.getCampaignName() + "/Saves/" + levelNumber + ".xml";
		saveLevel(levelLoaded, filename);
		campaign.getCampaignLevels().get(levelNumber - 1).hasUserSave = true;
	}
	
	public Level getLevel(){
		return levelLoaded;
	}
	
	public List<CampaignLevel> getLevels(){
		return campaign.getCampaignLevels();
	}

	public void saveLevel(Level level, String filename) {
		File file = new File(filename); 
		try {
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(level, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// Print out exceptions. We should really display them in a dialog...
		catch (IOException e) { 
			System.out.println(e); 
		}
	}
	
  	private Level loadLevel(String filename) {
  		File file = new File(filename);
  		Level levelLoaded = new Level(1);
  		try {
	    	
	    	XStream xstream = new XStream();
	    	levelLoaded = (Level)xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	    }
	    // Print out exceptions. We should really display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
  		return levelLoaded;
	}
}