package com.trainpuzzle.controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.thoughtworks.xstream.*;


import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.Level;


public class CampaignManager {

	private Campaign campaign;
	private Level levelLoaded;
	
	public CampaignManager(){
        loadCampaign();
	}
	
	public Level loadLevel(int levelNumber) {
		this.levelLoaded = campaign.loadLevel(levelNumber);
		return this.levelLoaded;
	}
	
	public Level openNextLevel(){
		
		return loadLevel(campaign.getCurrentLevel());
		
	}
	
	public void saveLevel(File file) {
	      try {
	        // Create the necessary output streams to save the level.
	        PrintStream out = new PrintStream(file);
	        XStream xstream = new XStream();
	        xstream.toXML(levelLoaded, out);
	        
	        System.out.println("wrote to file: " + file.getAbsoluteFile());
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (IOException e) { System.out.println(e); }
	    
	  }
 
	public Level loadLevel(File file) {
		  
		Level loadedLevel = new Level(3);
		
	      try {
	        XStream xstream = new XStream();
	        loadedLevel = (Level)xstream.fromXML(file);
	        this.levelLoaded = loadedLevel;
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	        
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	     
	      return this.levelLoaded;
	  }
	  
	public void saveCampaign() {
		  File file = new File("campaign.xml"); 
	      try {
	        // Create the necessary output streams to save the level.
	        PrintStream out = new PrintStream(file);
	        XStream xstream = new XStream();
	        xstream.toXML(campaign, out);
	        
	        System.out.println("wrote to file: " + file.getAbsoluteFile());
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (IOException e) { System.out.println(e); }
	    
	  }
	  
	public void loadCampaign() {
		File file = new File("campaign.xml"); 
		  
		Campaign loadedCampaign = new Campaign();
		
	      try {
	        XStream xstream = new XStream();
	        loadedCampaign = (Campaign)xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	        
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	      
	      this.campaign = loadedCampaign;
	  }
}


