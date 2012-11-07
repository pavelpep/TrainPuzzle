package com.trainpuzzle.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.thoughtworks.xstream.*;


import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.Level;

public class LevelManager {

	private Campaign campaign;
	private Level levelLoaded;
	
	public LevelManager(){
        this.campaign = new Campaign();
	}
	
	public LevelManager(Campaign campaign){
        this.campaign = campaign;
	}
		
	public Level loadLevel(int levelNumber) {
		campaign.loadLevel(levelNumber);
		this.levelLoaded = campaign.getCurrentLevel();
		return this.levelLoaded;
	}
	
	public Level loadNextLevel() {
		campaign.loadNextLevel();
		this.levelLoaded = campaign.getCurrentLevel();
		return this.levelLoaded;
	}
	
	public Campaign getCampaign(){
		return this.campaign;
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